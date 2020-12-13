package ru.beerbis.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class BaseButton extends Sprite {
    public BaseButton(TextureRegion region, float height) {
        super(region, height);
    }

    public BaseButton(Texture img, float height) {
        super(img, height);
    }

    private static final float SCALE_PRESSED = 0.9f;

    private int pointer;
    private boolean pressed;

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        if (pressed || !isMe(touch)) return;
        this.pointer = pointer;
        pressed = true;
        scale = SCALE_PRESSED;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || !pressed) return;
        pressed = false;
        scale = 1f;
        if (isMe(touch)) action();
    }

    public abstract void action();
}
