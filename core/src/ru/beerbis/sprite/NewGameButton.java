package ru.beerbis.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.beerbis.base.BaseButton;
import ru.beerbis.base.Sprite;
import ru.beerbis.math.Rect;

public class NewGameButton extends BaseButton {
    public static final float HEIGHT = 0.06f;
    public static final float BOTTOM = -0.5f + 0.06f;


    public NewGameButton(TextureAtlas atlas) {
        super(atlas.findRegion("button_new_game"), HEIGHT);
        setBottom(BOTTOM);
    }

    public NewGameButton(TextureAtlas atlas, Runnable runnable) {
        super(atlas.findRegion("button_new_game"), HEIGHT, runnable);
        setBottom(BOTTOM);
    }
}
