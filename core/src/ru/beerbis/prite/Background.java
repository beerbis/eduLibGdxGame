package ru.beerbis.prite;

import com.badlogic.gdx.graphics.Texture;

import ru.beerbis.base.Sprite;
import ru.beerbis.math.Rect;

public class Background extends Sprite {
    public Background(Texture img) {
        super(img, 1);
    }

    @Override
    public void resize(Rect worldBounds) {
        //setHeightProportion(worldBounds.getHeight());
        //  - лишено смысла, по определению worldBounds высота всегда еденица, а пляшет ширина.
        //  смысл бы возникал, если мы бы сплющивали бэк под ширину экрана, но всегда её так и
        //  задаём пропорционально оригиналу

        //this.pos.set(worldBounds.pos);
        // - лишено смыла, он же всегда 0:0, по определению worldBounds
    }
}
