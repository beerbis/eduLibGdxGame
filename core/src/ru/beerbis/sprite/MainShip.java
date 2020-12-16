package ru.beerbis.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.Sprite;
import ru.beerbis.math.Rect;
import ru.beerbis.pool.BulletPool;

public class MainShip extends Sprite {
    private static final float HEIGHT = 0.15f;
    private static final float BOTTOM_MARGIN = - 0.5f + 0.05f;

    private static final int NO_POINTER = -1;
    private static final Vector2 SPEED_KEY_RIGHT =  new Vector2(0.1f, 0);
    private static final Vector2 SPEED_KEY_LEFT = new Vector2(-0.1f, 0);
    private static final Vector2 SPEED_ZERO = new Vector2(0, 0);
    private static final Vector2 BULLET_SPEED = new Vector2(0, 0.5f);
    private static final float BULLET_HEIGHT = 0.01f;

    private static final float[] STRIKE_BLINK_DELAYS = {0.100f, 0.100f};
    private static final int STRIKE_BLINK_COUNT = 6;
    private Rect worldBounds;

    private int blinkCounter;
    private float blinkDelay;
    private Vector2 speed = new Vector2();
    private boolean pressedRight;
    private boolean pressedLeft;
    private int leftPointer = NO_POINTER;
    private int rightPointer = NO_POINTER;
    private BulletPool bulletPool;
    private final TextureRegion bulletRegion;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2, HEIGHT);
        setBottom(BOTTOM_MARGIN);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
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

        pos.mulAdd(speed, deltaTime);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void touchDown(Vector2 touch, int pointer, int button) {
        strike();
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != NO_POINTER) return;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != NO_POINTER) return;
            rightPointer = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer, int button) {
        if (pointer == leftPointer) {
            leftPointer = NO_POINTER;
            if (rightPointer != NO_POINTER) {
                moveRight();
            } else {
                stop();
            }
        } else if (pointer == rightPointer) {
            rightPointer = NO_POINTER;
            if (leftPointer != NO_POINTER) {
                moveLeft();
            } else {
                stop();
            }
        }
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
            case Input.Keys.SPACE:
                shoot();
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

    private void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, BULLET_SPEED, BULLET_HEIGHT, worldBounds, 1);
    }
}
