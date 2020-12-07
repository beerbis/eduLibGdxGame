package ru.beerbis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.function.Consumer;
import java.util.function.Function;

public class Sprite {
    final Texture img;
    final Vector2 centerOffset;
    public final Vector2 position = new Vector2(0, 0);
    private BasicMoving moving;

    public Sprite(Texture img) {
        this(img, img.getWidth() / 2, img.getHeight() / 2);
    }

    public Sprite(Texture img, int centerX, int centerY) {
        this.img = img;
        this.centerOffset = new Vector2(centerX, centerY);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(img, position.x - centerOffset.x, position.y - centerOffset.y);
    }

    public void setMovement(BasicMoving moving) {
        this.moving = moving;
    }

    public void move() {
        if (moving == null) return;
        if (!moving.move(position)) moving = null;
    };

    public void dispose() {
        img.dispose();
    }
}
