package ru.beerbis.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.beerbis.base.Sprite;

public class GameOver extends Sprite {
    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"), 0.08f);
    }
}
