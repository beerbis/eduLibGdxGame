package ru.beerbis.movements;

import com.badlogic.gdx.math.Vector2;

import ru.beerbis.base.BasicMoving;

public class SimpleDirectMovement implements BasicMoving {
    Vector2 destination;
    Vector2 speed;
    int tickCount;

    public SimpleDirectMovement(Vector2 speed, int tickCount) {
        this.speed = speed;
        this.tickCount = tickCount;
    }

    public SimpleDirectMovement(Vector2 destination, Vector2 source, float sec, float callPerSec) {
        float mps = sec * callPerSec;
        this.tickCount = (int) Math.ceil(mps);
        this.speed = new Vector2(destination).sub(source).scl(1 / mps);
        this.destination = destination;
    }

    @Override
    public boolean move(Vector2 position) {
        if (tickCount == 0) return false;

        if (tickCount == 1) {
            position.set(destination);
        } else {
            position.add(speed);
        }

        tickCount--;
        return true;
    }
}
