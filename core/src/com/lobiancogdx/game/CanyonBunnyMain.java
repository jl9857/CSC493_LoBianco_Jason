package com.lobiancogdx.game;
/**
 * Creates the menu screen and starts the game
 * @author Jason LoBianco
 */
import objects.Assets;
import screen.MenuScreen;
import world.WorldController;
import world.WorldRenderer;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.GL20;

public class CanyonBunnyMain extends Game
{
	/**
	 * creates an instance of the menu screen
	 */
	@Override
	public void create()
	{
		//Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Load assets
		Assets.instance.init(new AssetManager());
		//Start game at menu screen
		setScreen(new MenuScreen(this));
	}
}