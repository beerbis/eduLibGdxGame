package ru.beerbis.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.BasicScene;
import ru.beerbis.Sprite;
import ru.beerbis.movements.SimpleDirectMovement;

public class TheOneScene extends BasicScene {
    private Texture bgImage;
    private Sprite theFace;

    @Override
    public boolean touchDown(Vector2 screenPos, int pointer, int button) {
        theFace.setMovement(new SimpleDirectMovement(screenPos, theFace.position, 3f, Gdx.graphics.getFramesPerSecond()));
        return true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bgImage, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        theFace.move();
        theFace.draw(batch);
        batch.end();
    }

    @Override
    public void show() {
        super.show();
        theFace = new Sprite(new Texture("badlogic.jpg"));
        bgImage = new Texture("background.jpg");
    }

    @Override
    public void dispose() {
        super.dispose();
        theFace.dispose();
        bgImage.dispose();
    }
}
