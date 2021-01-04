package ru.beerbis.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.Sprite;
import ru.beerbis.math.Rect;
import ru.beerbis.math.Rnd;

public class Star extends Sprite {

    private static final float MIN_HEIGHT = 0.005f;
    private static final float MAX_HEIGHT = 0.011f;

    protected final Vector2 speed;
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"), Rnd.nextFloat(MIN_HEIGHT, MAX_HEIGHT));
        speed = new Vector2(
                Rnd.nextFloat(-0.005f, 0.005f),
                Rnd.nextFloat(-0.15f, -0.005f));
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float x = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float y = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(x, y);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speed, delta);
        checkBounds();
        changeHeight();
    }

    protected void checkBounds() {
        if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
    }

    protected void changeHeight() {
        float height = getHeight() + 0.00001f;
        if (height > MAX_HEIGHT) {
            height = MIN_HEIGHT;
        }
        setHeightProportion(height);
    }
}
