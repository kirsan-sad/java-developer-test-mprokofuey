package com.space.game;

import com.badlogic.gdx.Game;
import com.space.game.screens.StartScreen;

public class Main extends Game {

	@Override
	public void create() {
		setScreen(new StartScreen(this));
	}
}
