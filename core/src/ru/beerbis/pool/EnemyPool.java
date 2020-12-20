package ru.beerbis.pool;

import ru.beerbis.base.SpritesPool;
import ru.beerbis.math.Rect;
import ru.beerbis.sprite.Enemy;

public class EnemyPool extends SpritesPool<Enemy> {

    private final BulletPool bulletPool;
    private final Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
    }

    @Override
    public Enemy newObject() {
        return new Enemy(bulletPool, worldBounds);
    }
}
