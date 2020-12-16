package ru.beerbis.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.Sprite;
import ru.beerbis.math.Rect;

public class MainShip extends Sprite {
    private static final float HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = - 0.5f + 0.05f;

    private static final Vector2 SPEED_KEY_RIGHT =  new Vector2(0.1f, 0);
    private static final Vector2 SPEED_KEY_LEFT = new Vector2(-0.1f, 0);
    private static final Vector2 SPEED_ZERO = new Vector2(0, 0);
    private static final float[] STRIKE_BLINK_DELAYS = {0.100f, 0.100f};
    private static final int STRIKE_BLINK_COUNT = 6;
    private Rect worldBounds;

    private int blinkCounter;
    private float blinkDelay;
    private Vector2 speed = new Vector2();
    private boolean pressedRight;
    private boolean pressedLeft;
    private Vector2 tmp = new Vector2();

    public MainShip(TextureAtlas atlas) {
        super(atlas.findRegion("main_ship"), 1, 2, 2, HEIGHT);
        setBottom(BOTTOM_MARGIN);
    }

    public void strike() {
        blinkDelay = 0;
        blinkCounter = STRIKE_BLINK_COUNT;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);

        if (blinkCounter > 0) {
            blinkDelay -= deltaTime;
            if (blinkDelay <= 0) {
                blinkCounter--;
                frame = blinkCounter % 2;
                blinkDelay = STRIKE_BLINK_DELAYS[frame];
            }
        }

        tmp.set(pos);
        tmp.mulAdd(speed, deltaTime);
        if (worldBounds.isMe(tmp))
            pos.set(tmp);
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        strike();
    }

    @Override
    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                pressedLeft = true;
                moveLeft();
                break;
        }
    }

    @Override
    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.RIGHT:
            case Input.Keys.D:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.LEFT:
            case Input.Keys.A:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
        }
    }

    private void moveLeft()     { speed.set(SPEED_KEY_LEFT);    }
    private void moveRight()    { speed.set(SPEED_KEY_RIGHT);   }
    private void stop()         { speed.set(SPEED_ZERO);        }
}
