package ru.beerbis.pool;

import ru.beerbis.base.SpritesPool;
import ru.beerbis.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    public Bullet newObject() {
        return new Bullet();
    }
}
