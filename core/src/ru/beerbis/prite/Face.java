package ru.beerbis.prite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.BasicMoving;
import ru.beerbis.base.Sprite;
import ru.beerbis.math.Rect;
import ru.beerbis.movements.SimpleDirectMovement;

public class Face extends Sprite {
    BasicMoving moving;

    public Face(Texture img, float height) {
        super(img, height);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(worldBounds.pos); //а и пусть летит в центр на любой ресайз, почему бы и нет
    }

    @Override
    public void touchDown(Vector2 screenPos, int pointer, int button) {
        moving = new SimpleDirectMovement(screenPos, pos, 3f, Gdx.graphics.getFramesPerSecond());
    }

    @Override
    public void update(float deltaTime) {
        if (moving == null) return;
        if (!moving.move(pos)) moving = null;
    }
}
