package ru.beerbis.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.BasicScene;
import ru.beerbis.math.Rect;
import ru.beerbis.pool.BulletPool;
import ru.beerbis.pool.EnemyPool;
import ru.beerbis.sprite.Background;
import ru.beerbis.sprite.MainShip;
import ru.beerbis.sprite.Star;
import ru.beerbis.utils.EnemyEmitter;

public class GameScene extends BasicScene {

    private static final int STAR_COUNT = 128;

    private Texture bg = new Texture("textures/bg.png");;
    private Background background = new Background(bg);

    private TextureAtlas atlas = new TextureAtlas("textures/mainAtlas.tpack");;
    private Star[] stars;
    BulletPool bulletPool = new BulletPool();

    private Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds\\music.mp3"));
    private Sound bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds\\music.mp3"));

    private MainShip mainShip = new MainShip(atlas, bulletPool);
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }

        enemyPool = new EnemyPool(bulletPool, worldBounds);
        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, bulletSound, enemyPool);

        bgMusic.setLooping(true);
        bgMusic.setVolume(0.2f);
        bgMusic.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (Star star : stars) {
            star.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();

        bulletSound.dispose();
        bgMusic.dispose();

        mainShip.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        mainShip.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        mainShip.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 screenPos, int pointer) {
        mainShip.touchDragged(screenPos, pointer);
        return super.touchDragged(screenPos, pointer);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        bulletPool.updateActiveObjects(delta);
        mainShip.update(delta);
        enemyPool.updateActiveObjects(delta);
        enemyEmitter.generate(delta);
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.55f, 0.23f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) star.draw(batch);
        bulletPool.drawActiveObjects(batch);
        enemyPool.drawActiveObjects(batch);
        mainShip.draw(batch);
        batch.end();
    }
}
