package ru.beerbis.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.sprite.Background;
import ru.beerbis.base.BasicScene;
import ru.beerbis.base.Sprite;
import ru.beerbis.math.Rect;
import ru.beerbis.sprite.ButtonExit;
import ru.beerbis.sprite.ButtonPlay;
import ru.beerbis.sprite.Star;

public class MainMenuScene extends BasicScene {
    private static final int STAR_COUNT = 256;
    private final Game game;

    private Texture bgImage = new Texture("textures\\bg.png");
    private TextureAtlas atlas = new TextureAtlas("textures/menuAtlas.tpack");

    private Sprite background = new Background(bgImage);
    private Sprite[] stars;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MainMenuScene(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();

        stars = new Sprite[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void render(float delta) {
        background.update(delta);
        for (Sprite star: stars) star.update(delta);

        batch.begin();
        background.draw(batch);
        for (Sprite star: stars) star.draw(batch);
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public boolean touchDown(Vector2 screenPos, int pointer, int button) {
        buttonExit.touchDown(screenPos, pointer, button);
        buttonPlay.touchDown(screenPos, pointer, button);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 screenPos, int pointer, int button) {
        buttonExit.touchUp(screenPos, pointer, button);
        buttonPlay.touchUp(screenPos, pointer, button);
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Sprite star: stars) star.resize(worldBounds);
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public void dispose() {
        atlas.dispose();
        bgImage.dispose();
        super.dispose();
    }
}
