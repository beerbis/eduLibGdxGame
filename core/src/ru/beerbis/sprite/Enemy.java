package ru.beerbis.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.Ship;
import ru.beerbis.math.Rect;
import ru.beerbis.pool.BulletPool;
import ru.beerbis.pool.ExplosionPool;

public class Enemy extends Ship {

    private static final float WARPING_OUT_HALF_DELAY = 0.25f;
    private static final float WARPING_OUT_DELAY = WARPING_OUT_HALF_DELAY * 2;
    private boolean warpedOut;
    private float warpingTimer;
    private float warpingOutTop;
    private float warpingOutBottom;
    private float warpingInBottom;
    private float warpingInTop;
    private HealthBar healthBar;
    private Vector2 hbOffset = new Vector2();

    public Enemy(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Texture healthBar) {
        super(bulletPool, explosionPool);
        this.worldBounds = worldBounds;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletPos = new Vector2();
        this.healthBar = new HealthBar(healthBar, pos, hbOffset);
    }

    @Override
    public void update(float delta) {
        if (!warpedOut) {
            if (warpingTimer >= WARPING_OUT_DELAY) {
                setHeight(warpingOutTop - warpingOutBottom);
                setBottom(warpingOutBottom);
                warpedOut = true;
            } else {
                if (warpingTimer <= WARPING_OUT_HALF_DELAY) {
                    float slidingAmount = (warpingInBottom - warpingOutBottom) * (delta / WARPING_OUT_HALF_DELAY);
                    this.spreadBottom(slidingAmount);
                } else {
                    float slidingAmount = (warpingInTop - warpingOutTop) * (delta / WARPING_OUT_HALF_DELAY);
                    this.spreadTop(-slidingAmount);
                }
                warpingTimer += delta;
                return;
            }
        }

        super.update(delta);
        healthBar.update(delta);
        if (getBottom() < worldBounds.getBottom()) {
            destroy();
        }
    }

    @Override
    protected void updateBulletPosition() {
        bulletPos.set(pos.x, pos.y - getHalfHeight());
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);
        healthBar.setHealthLevel(getHp());
    }

    public void set(
            TextureRegion[] regions,
            TextureRegion bulletRegion,
            Sound bulletSound,
            float bulletHeight,
            Vector2 bulletV,
            int damage,
            int hp,
            float reloadInterval,
            Vector2 v0,
            float height
    ) {
        this.regions = regions;
        this.bulletRegion = bulletRegion;
        this.bulletSound = bulletSound;
        this.bulletHeight = bulletHeight;
        this.bulletV = bulletV;
        this.damage = damage;
        this.hp = hp;
        this.reloadInterval = reloadInterval;
        this.v.set(v0);
        setHeightProportion(height);

        hbOffset.set(getWidth() * 0.25f, getHalfHeight());
        healthBar.set(getWidth() * 0.75f, hp);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        if (warpedOut && healthBar.getHealthMax() > 1)
            healthBar.draw(batch);
    }

    public void warpOut(float destBottom, float destTop) {
        warpingOutBottom = destBottom;
        warpingOutTop = destTop;
        warpingInBottom = getBottom();
        warpingInTop = getTop();
        warpingTimer = 0;
        warpedOut = false;
    }

    public void preloadWeapons() {
        reloadTimer = reloadInterval * 0.85f;
    }

    public boolean isBulletCollision(Bullet bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y);
    }
}
