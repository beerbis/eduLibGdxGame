package ru.beerbis;

import com.badlogic.gdx.Game;

import ru.beerbis.scenes.MainMenuScene;

public class SatanGame extends Game {

	@Override
	public void create () {
		setScreen(new MainMenuScene());
	}
}
