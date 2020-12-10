package ru.beerbis.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.BasicScene;
import ru.beerbis.Sprite;
import ru.beerbis.math.Rect;
import ru.beerbis.movements.SimpleDirectMovement;

public class TheOneScene extends BasicScene {
    private Texture bgImage = new Texture("background.jpg");
    private Texture faceImage = new Texture("badlogic.jpg");
    private Sprite theFace = new Sprite(faceImage, 0.3f);

    @Override
    public boolean touchDown(Vector2 screenPos, int pointer, int button) {
        theFace.setMovement(new SimpleDirectMovement(screenPos, theFace.pos, 3f, Gdx.graphics.getFramesPerSecond()));
        return true;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        theFace.move();

        batch.begin();
        batch.draw(bgImage, worldBounds.getLeft(), worldBounds.getBottom(), worldBounds.getWidth(), worldBounds.getHeight());
        theFace.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        theFace.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        faceImage.dispose();
        bgImage.dispose();
    }
}
