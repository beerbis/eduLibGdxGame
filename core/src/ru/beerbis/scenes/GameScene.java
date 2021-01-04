package ru.beerbis.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.beerbis.base.BasicScene;
import ru.beerbis.math.Rect;
import ru.beerbis.pool.BulletPool;
import ru.beerbis.pool.EnemyPool;
import ru.beerbis.pool.ExplosionPool;
import ru.beerbis.sprite.Background;
import ru.beerbis.sprite.Bullet;
import ru.beerbis.sprite.Enemy;
import ru.beerbis.sprite.GameOver;
import ru.beerbis.sprite.MainShip;
import ru.beerbis.sprite.NewGameButton;
import ru.beerbis.sprite.Star;
import ru.beerbis.sprite.ParallaxStar;
import ru.beerbis.utils.EnemyEmitter;
import ru.beerbis.utils.Font;

public class GameScene extends BasicScene {

    private static final int STAR_COUNT = 128;
    private static final float MARGIN = 0.01f;
    private static final String FRAGS = "Frags: ";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Level: ";

    private enum State {PLAYING, GAME_OVER}

    private final Game game;
    private Texture bg = new Texture("textures/bg.png");;
    private Texture hb = new Texture("textures/healthBar.png");
    private Background background = new Background(bg);

    private TextureAtlas atlas = new TextureAtlas("textures/mainAtlas.tpack");;
    private Star[] stars;
    BulletPool bulletPool = new BulletPool();

    private Music bgMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds\\music.mp3"));
    private Sound explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    private Sound bulletSound =  Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    ExplosionPool explosionPool = new ExplosionPool(atlas, explosionSound);

    private MainShip mainShip = new MainShip(atlas, bulletPool, explosionPool);
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;

    private State state = State.PLAYING;
    private GameOver gameOver = new GameOver(atlas);
    private NewGameButton newGameButton;
    private int frags;

    private Font font = new Font("font/font.fnt", "font/font.png", 0.02f);
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHp = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();

    public GameScene(final Game game) {
        this.game = game;
        newGameButton = new NewGameButton(atlas, new Runnable() {
            @Override
            public void run() { game.setScreen(new GameScene(game)); }
        });
    }

    @Override
    public void show() {
        super.show();

        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new ParallaxStar(atlas, mainShip.getRefSpeed());
        }

        enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, hb);
        enemyEmitter = new EnemyEmitter(atlas, worldBounds, bulletSound, enemyPool);

        bgMusic.setLooping(true);
        bgMusic.setVolume(0.2f);
        bgMusic.play();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollision();
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
        hb.dispose();
        atlas.dispose();
        bulletPool.dispose();
        enemyPool.dispose();

        bulletSound.dispose();
        bgMusic.dispose();

        mainShip.dispose();
        explosionPool.dispose();
        explosionSound.dispose();

        font.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) mainShip.touchDown(touch, pointer, button);
        if (state == State.GAME_OVER) newGameButton.touchDown(touch, pointer, button);
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) mainShip.touchUp(touch, pointer, button);
        if (state == State.GAME_OVER) newGameButton.touchUp(touch, pointer, button);
        return super.touchUp(touch, pointer, button);
    }

    @Override
    public boolean touchDragged(Vector2 screenPos, int pointer) {
        if (state == State.PLAYING) mainShip.touchDragged(screenPos, pointer);
        return super.touchDragged(screenPos, pointer);
    }

    private void update(float delta) {
        for (Star star : stars) {
            star.update(delta);
        }
        explosionPool.updateActiveObjects(delta);
        if (state == State.PLAYING) {
            bulletPool.updateActiveObjects(delta);
            mainShip.update(delta);
            enemyPool.updateActiveObjects(delta);
            enemyEmitter.generate(delta, frags);
        } else
            newGameButton.update(delta);
    }

    private void checkCollision() {
        if (state == State.GAME_OVER) {
            return;
        }
        List<Enemy> enemyList = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : enemyList) {
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (mainShip.pos.dst(enemy.pos) < minDist) {
                enemy.blastMe();
                mainShip.damage(enemy.getDamage());
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip && enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (enemy.isDestroyed()) frags++;
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() != mainShip && mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
        if (mainShip.isDestroyed()) {
            state = State.GAME_OVER;
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
    }

    private void draw() {
        Gdx.gl.glClearColor(0.55f, 0.23f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : stars) star.draw(batch);
        if (state == State.PLAYING) {
            bulletPool.drawActiveObjects(batch);
            enemyPool.drawActiveObjects(batch);
            mainShip.draw(batch);
        } else if (state == State.GAME_OVER) {
            gameOver.draw(batch);
            newGameButton.draw(batch);
        }
        explosionPool.drawActiveObjects(batch);
        printInfo();
        batch.end();
    }


    private void printInfo() {
        sbFrags.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + MARGIN, worldBounds.getTop() - MARGIN);
        sbHp.setLength(0);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop() - MARGIN, Align.center);
        sbLevel.setLength(0);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight() - MARGIN, worldBounds.getTop() - MARGIN, Align.right);
    }
}
