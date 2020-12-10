package ru.beerbis;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.function.Consumer;
import java.util.function.Function;

public class Sprite {
    final Texture img;
    public final float width;
    public final float height;
    public final Vector2 position = new Vector2(0, 0);
    private BasicMoving moving;

    public Sprite(Texture img, float width, float height) {
        this.img = img;
        this.width = width;
        this.height = height;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(img, position.x, position.y, width, height);
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
