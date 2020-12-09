package ru.beerbis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class BasicScene implements Screen, InputProcessor {
    protected SpriteBatch batch;

    @Override
    public void show() {
        batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render(float delta) {}

    @Override
    public void resize(int width, int height) {}

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
        return touchDown(new Vector2(screenX, Gdx.graphics.getHeight() - screenY), pointer, button);
    }

    public boolean touchDown(Vector2 screenPos, int pointer, int button) {
        return false;
    }

    @Override
    public final boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return touchUp(new Vector2(screenX, Gdx.graphics.getHeight() - screenY), pointer, button);
    }

    public boolean touchUp(Vector2 screenPos, int pointer, int button) {
        return false;
    }

    @Override
    public final boolean touchDragged(int screenX, int screenY, int pointer) {
        return touchDragged(new Vector2(screenX, Gdx.graphics.getHeight() - screenY), pointer);
    }

    public boolean touchDragged(Vector2 screenPos, int pointer) {
        return false;
    }

    @Override
    public final boolean mouseMoved(int screenX, int screenY) {
        return mouseMoved(new Vector2(screenX, Gdx.graphics.getHeight() - screenY));
    }

    public boolean mouseMoved(Vector2 screenPos) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
