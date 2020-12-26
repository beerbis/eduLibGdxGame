package ru.beerbis.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.beerbis.math.Rect;
import ru.beerbis.pool.BulletPool;
import ru.beerbis.pool.ExplosionPool;
import ru.beerbis.sprite.Bullet;
import ru.beerbis.sprite.Explosion;

public abstract class Ship extends Sprite {

    private static final float DAMAGE_ANIMATE_INTERVAL = 0.1f;

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
    protected float reloadTimer;
    private float damageAnimateTimer;

    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;

    public Ship(BulletPool bulletPool, ExplosionPool explosionPool) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    }

    public Ship(TextureRegion region, int rows, int cols, int frames, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(region, rows, cols, frames);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        damageAnimateTimer = DAMAGE_ANIMATE_INTERVAL;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= DAMAGE_ANIMATE_INTERVAL) {
            frame = 0;
        }
    }

    protected abstract void updateBulletPosition();

    public void blastMe() {
        boom();
        destroy();
    }

    public void damage(int damage) {
        this.hp -= damage;
        if (hp <= 0) {
            hp = 0;
            blastMe();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    public int getDamage() {
        return damage;
    }

    public Vector2 getRefSpeed() {
        return v;
    }

    private void shoot() {
        updateBulletPosition();
        bulletSound.play(0.1f);
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, bulletPos, bulletV, bulletHeight, worldBounds, damage);
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(this.pos, getHeight());
    }

    public abstract boolean isBulletCollision(Bullet bullet);
}
