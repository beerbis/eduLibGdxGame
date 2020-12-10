package ru.beerbis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.function.Consumer;
import java.util.function.Function;

import ru.beerbis.math.Rect;

public class Sprite extends Rect {
    private static final float angle = 0;
    private static final float scale = 1;
    private TextureRegion[] regions;
    private int frame;
    private BasicMoving moving;

    public Sprite(TextureRegion region, float height) {
        regions = new TextureRegion[1];
        regions[0] = region;
        setHeightProportion(height);
    }

    public Sprite(Texture img, float height) {
        this(new TextureRegion(img), height);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    protected void setHeightProportion(float height) {
        setSize(
                height * regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight(),
                height);
    }

    public void resize(Rect worldBounds) {}

    public void setMovement(BasicMoving moving) {
        this.moving = moving;
    }

    public void move() {
        if (moving == null) return;
        if (!moving.move(pos)) moving = null;
    };
}
