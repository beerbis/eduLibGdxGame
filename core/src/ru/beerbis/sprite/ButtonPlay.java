package ru.beerbis.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.beerbis.base.BaseButton;
import ru.beerbis.math.Rect;
import ru.beerbis.scenes.GameScene;
//import ru.beerbis.scenes.GameScreen;

public class ButtonPlay extends BaseButton {

    private static final float HEIGHT = 0.24f;
    private static final float MARGIN = 0.03f;

    private final Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"), HEIGHT);
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + MARGIN);
        setLeft(worldBounds.getLeft() + MARGIN);
    }

    @Override
    public void action() {
        game.setScreen(new GameScene(game));
    }
}
