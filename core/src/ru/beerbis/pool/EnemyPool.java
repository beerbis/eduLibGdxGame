package ru.beerbis.pool;

import com.badlogic.gdx.graphics.Texture;

import ru.beerbis.base.SpritesPool;
import ru.beerbis.math.Rect;
import ru.beerbis.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private final Rect worldBounds;
    private final Texture hbTexture;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Texture hbTexture) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.hbTexture = hbTexture;
    }

    @Override
    public Enemy newObject() {
        return new Enemy(bulletPool, explosionPool, worldBounds, hbTexture);
    }
}
