package com.lobiancogdx.game.desktop;
/**
 * @author Jason LoBianco
 */

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.badlogic.gdx.tools.texturepacker.TexturePacker.Settings;
import com.lobiancogdx.game.CanyonBunnyMain;

public class DesktopLauncher 
{
	private static boolean rebuildAtlas = false;
	private static boolean drawDebugOutline = true;
	
	/**
	 * Build the texture atlas and creates the window for the game, then
	 * creates the game.
	 * @param arg
	 */
	public static void main (String[] arg) 
	{
		if(rebuildAtlas)
		{
			Settings settings = new Settings();
			settings.maxWidth = 1024;
			settings.maxHeight = 1024;
			settings.duplicatePadding = false;
			settings.debug = drawDebugOutline;
			TexturePacker.process(settings, "assets-raw/justanotherescape", "../core/assets-raw/justanotherescape", "justanotherescape");
		}
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "JustAnotherEscape";
		config.width = 800;
		config.height = 480;
		
		new LwjglApplication(new CanyonBunnyMain(), config);
	}
}
