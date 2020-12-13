package ru.beerbis.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.math.MatrixUtils;
import ru.beerbis.math.Rect;

public abstract class BasicScene implements Screen, InputProcessor {
    protected SpriteBatch batch = new SpriteBatch();
    protected Rect worldBounds = new Rect();
    private Rect screenBounds = new Rect();
    private Rect glBounds =  new Rect(0, 0, 1f, 1f);;
    private Matrix4 worldToGl = new Matrix4();
    private Matrix3 screenToWorld = new Matrix3();

    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {}

    @Override
    public final void resize(int width, int height) {
        screenBounds.setSize(width, height);
        screenBounds.setBottom(0);
        screenBounds.setLeft(0);

        worldBounds.setSize(1f * width / (float) height, 1f);

        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
//        Gdx.input.setInputProcessor(null);
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public final boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return touchDown(new Vector2(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld), pointer, button);
    }

    public boolean touchDown(Vector2 screenPos, int pointer, int button) {
        return false;
    }

    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return touchUp(new Vector2(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld), pointer, button);
    }

    public boolean touchUp(Vector2 screenPos, int pointer, int button) {
        return false;
    }

    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        return touchDragged(new Vector2(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld), pointer);
    }

    public boolean touchDragged(Vector2 screenPos, int pointer) {
        return false;
    }

    @Override
    public final boolean mouseMoved(int screenX, int screenY) {
        return mouseMoved(new Vector2(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld));
    }

    public boolean mouseMoved(Vector2 screenPos) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
