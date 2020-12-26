package ru.beerbis.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class ParallaxStar extends Star {

    private Vector2 refHeroSpeed;
    private Vector2 tmp = new Vector2();

    public ParallaxStar(TextureAtlas atlas, Vector2 refHeroSpeed) {
        super(atlas);
        this.refHeroSpeed = refHeroSpeed;
    }

    @Override
    public void update(float delta) {
        tmp.setZero().mulAdd(refHeroSpeed, 0.2f).rotate(180).add(speed);
        pos.mulAdd(tmp, delta);
        checkBounds();
        changeHeight();
    }
}
