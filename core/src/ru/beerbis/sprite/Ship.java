package ru.beerbis.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.Sprite;
import ru.beerbis.math.Rect;

public class Ship extends Sprite {
    private static final int KEY_RIGHT = 22;
    private static final int KEY_LEFT = 21;
    private static final Vector2 SPEED_KEY_RIGHT =  new Vector2(0.1f, 0);
    private static final Vector2 SPEED_KEY_LEFT = new Vector2(-0.1f, 0);
    private static final Vector2 SPEED_ZERO = new Vector2(0, 0);
    private static final float[] STRIKE_BLINK_DELAYS = {0.100f, 0.100f};
    private static final int STRIKE_BLINK_COUNT = 6;
    private Rect worldBounds;

    private int blinkCounter;
    private float blinkDelay;
    private Vector2 speed = new Vector2();
    private int currentKey;
    private Vector2 tmp = new Vector2();

    public static Ship newShip(TextureAtlas atlas) {
        TextureRegion r = atlas.findRegion("main_ship");
        TextureRegion[][] rs = r.split(r.getRegionWidth() / 2, r.getRegionHeight());

        //прямо беда какая-то, `super()` можно только первой строкой вызывать, а надо предрассчитать
        //араметры для него... как делать в таких случаях я даже не знаю.
        Ship ship = new Ship(rs[0], 0.1f);
        ship.pos.set(0, -0.4f);
        return ship;
    }

    private Ship(TextureRegion[] regions, float height) {
        super(regions, height);
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
        if (keycode == KEY_LEFT || keycode == KEY_RIGHT) {
            currentKey = keycode;
            speed.set(keycode == KEY_LEFT ? SPEED_KEY_LEFT : SPEED_KEY_RIGHT);
        }
    }

    @Override
    public void keyUp(int keycode) {
        if (keycode == currentKey) {
            currentKey = 0;
            speed.set(SPEED_ZERO);
        }
    }
}
