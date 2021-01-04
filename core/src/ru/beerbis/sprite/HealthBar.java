package ru.beerbis.sprite;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.Sprite;

public class HealthBar extends Sprite {
    private final Vector2 refPos;
    private Vector2 refOffset;
    private int healthMax;
    private static final int GRAD_COUNT = 6;
    private static final int GRAD_MAX = GRAD_COUNT - 1;

    public HealthBar(Texture texture, Vector2 refPos, Vector2 refOffset) {
        super(texture, GRAD_COUNT, 1, GRAD_COUNT);
        this.refPos = refPos;
        this.refOffset = refOffset;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        pos.set(refPos).add(refOffset);
    }

    public void setHealthLevel(int actualHealth) {
        frame = (int)(GRAD_MAX * (actualHealth / (float)healthMax));
    }

    public void set(
            float width,
            int maxHp
    ) {
        healthMax = maxHp;
        setWidthProportion(width);
        setHealthLevel(maxHp);
    }

    public int getHealthMax() {
        return healthMax;
    }
}
