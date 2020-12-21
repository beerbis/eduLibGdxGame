package ru.beerbis.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.math.Rect;
import ru.beerbis.pool.BulletPool;
import ru.beerbis.sprite.Bullet;

public abstract class Ship extends Sprite {

    protected TextureRegion bulletRegion;
    protected Sound bulletSound;
    protected Vector2 bulletV;
    protected Vector2 bulletPos;
    protected float bulletHeight;
    protected int damage;
    protected int hp;

    protected Vector2 v;
    protected Vector2 v0;

    protected Rect worldBounds;

    protected float reloadInterval;
    private float reloadTimer;

    private final BulletPool bulletPool;

    public Ship(BulletPool bulletPool) {
        this.bulletPool = bulletPool;
    }

    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }

        applyShootingCollisions();
    }

    private void shoot() {
        bulletSound.play(0.1f);
        Bullet bullet = bulletPool.obtain();
        updateBulletPosition();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
    }

    protected abstract void updateBulletPosition();

    public void preloadWeapons() {
        reloadTimer = reloadInterval;
    }

    public final void applyShootingCollisions() {
        for (Bullet bullet: bulletPool.getActiveObjects()) {
            if (!bullet.isDestroyed() && !bullet.isOutside(this) && bullet.getOwner() != this) {
                hp -= bullet.getDamage();
                bullet.destroy();
                if (hp <= 0) blastMe();
            }
        }
    }

    protected abstract void blastMe();
}
