package com.lobiancogdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;

public class Assets implements Disposable, AssetErrorListener
{
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	
	private AssetManager assetManager;
	
	//singleton: prevent instantiation from other classes
	private Assets()
	{
		
	}
	
	public AssetThief thief;
	public AssetFloor floor;
	public AssetGoldCoin goldCoin;
	public AssetClock clock;
	public AssetLevelDecoration levelDecoration;
	/**
	 * set asset manager error handler, load texture atlas, then 
	 * start loading assets and wait until finished, enable
	 * texture filtering for pixel smoothing, create game
	 * resource objects
	 * @param assetManager
	 */
	public void init(AssetManager assetManager)
	{
		this.assetManager = assetManager;
		assetManager.setErrorListener(this);
		assetManager.load(Constants.TEXTURE_ATLAS_OBJECTS, TextureAtlas.class);
		assetManager.finishLoading();
		Gdx.app.debug(TAG, "# of assets loaded: " + assetManager.getAssetNames().size);
		for(String a : assetManager.getAssetNames())
		{
			Gdx.app.debug(TAG, "asset: " + a);
		}
		
		TextureAtlas atlas = assetManager.get(Constants.TEXTURE_ATLAS_OBJECTS);
		
		for(Texture t : atlas.getTextures())
		{
			t.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
		
		thief = new AssetThief(atlas);
		floor = new AssetFloor(atlas);
		goldCoin = new AssetGoldCoin(atlas);
		clock = new AssetClock(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
	}
	
	/**
	 * Calls the dispose method to clean up resources.
	 */
	@Override
	public void dispose()
	{
		assetManager.dispose();
	}
	
	/**
	 * Will print out to the console when an error occurs loading assets.
	 * @param filename
	 * @param type
	 * @param throwable
	 */
	public void error(String filename, Class type, Throwable throwable)
	{
		Gdx.app.error(TAG, "Couldn't load asset '" + filename + "'", (Exception)throwable);
	}
	
	/**
	 * Will print out to the console when an error occurs loading assets.
	 */
	@Override
	public void error(AssetDescriptor asset, Throwable throwable)
	{
		Gdx.app.error(TAG, "Couldn't load asset '" + asset.fileName + "'", (Exception)throwable);
	}
	
	/**
	 * Inner class of the thief image to help group assets.
	 */
	public class AssetThief
	{
		public final AtlasRegion thief;
		
		public AssetThief(TextureAtlas atlas)
		{
			thief = atlas.findRegion("thief");
		}
	}
	
	/**
	 * Inner class of the floor image to help group assets.
	 */
	public class AssetFloor
	{
		public final AtlasRegion floor;
		
		public AssetFloor(TextureAtlas atlas)
		{
			floor = atlas.findRegion("floor");
		}
	}
	
	/**
	 * Inner class of the gold coin image to help group assets.
	 */
	public class AssetGoldCoin
	{
		public final AtlasRegion goldCoin;
		
		public AssetGoldCoin(TextureAtlas atlas)
		{
			goldCoin = atlas.findRegion("item_gold_coin");
		}
	}
	
	/**
	 * Inner class of the clock image to help group assets.
	 */
	public class AssetClock
	{
		public final AtlasRegion clock;
		
		public AssetClock(TextureAtlas atlas)
		{
			clock = atlas.findRegion("item_clock");
		}
	}
	
	/**
	 * Inner class of the level decoration images to help group assets.
	 */
	public class AssetLevelDecoration
	{
		public final AtlasRegion background01;
		public final AtlasRegion background02;
		public final AtlasRegion crate;
		public final AtlasRegion windowClosed;
		public final AtlasRegion windowOpen;
		public final AtlasRegion spike;
		
		public AssetLevelDecoration(TextureAtlas atlas)
		{
			background01 = atlas.findRegion("white_background");
			background02 = atlas.findRegion("gray_background");
			crate = atlas.findRegion("crate");
			windowClosed = atlas.findRegion("window_closed");
			windowOpen = atlas.findRegion("window_open");
			spike = atlas.findRegion("spike");
		}
	}
}
