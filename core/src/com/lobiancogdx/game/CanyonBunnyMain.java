package com.lobiancogdx.game;
/**
 * @author Jason LoBianco
 */
import objects.Assets;
import objects.AudioManager;
import screens.GamePreferences;
import screens.MenuScreen;
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
	@Override
	public void create()
	{
		//Set Libgdx log level
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		//Load assets
		Assets.instance.init(new AssetManager());
		// Load preferences for audio settings and start playing music  
		GamePreferences.instance.load();  
		AudioManager.instance.play(Assets.instance.music.song01);
		//Start game at menu screen
		setScreen(new MenuScreen(this));
	}
}