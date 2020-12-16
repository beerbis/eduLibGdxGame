package ru.beerbis.base;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.math.Rect;
import ru.beerbis.utils.Regions;

public class Sprite extends Rect {
    protected static final float SCALE_ONE = 1f;

    protected float angle = 0;
    protected float scale = SCALE_ONE;
    private TextureRegion[] regions;
    protected int frame;

    public Sprite(TextureRegion region, float height) {
        regions = new TextureRegion[1];
        regions[0] = region;
        setHeightProportion(height);
    }

    public Sprite(TextureRegion[] regions, float height) {
        this.regions = regions;
        setHeightProportion(height);
    }

    public Sprite(Texture img, float height) {
        this(new TextureRegion(img), height);
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames, float height) {
        this(Regions.split(region, rows, cols, frames), height);
    }

    public final void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    protected final void setHeightProportion(float height) {
        setSize(
                height * regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight(),
                height);
    }

    public void resize(Rect worldBounds) {}
    public void update(float deltaTime) {};
    public void touchDown(Vector2 touch, int pointer, int button) {}
    public void touchUp(Vector2 touch, int pointer, int button) {}
    public void touchDragged(Vector2 touch, int pointer, int button) {}

    public void keyDown(int keycode){}
    public void keyUp(int keycode) {}
}
