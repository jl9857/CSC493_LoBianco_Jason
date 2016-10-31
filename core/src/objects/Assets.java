package objects;
/**
 * @author Jason LoBianco
 */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.utils.Disposable;
import com.lobiancogdx.game.Constants;

public class Assets implements Disposable, AssetErrorListener
{
	public static final String TAG = Assets.class.getName();
	public static final Assets instance = new Assets();
	
	private AssetManager assetManager;
	public AssetFonts fonts;
	
	//singleton: prevent instantiation from other classes
	private Assets()
	{
		
	}
	
	public AssetThief thief;
	public AssetFloor floor;
	public AssetGoldCoin goldCoin;
	public AssetClock clock;
	public AssetLevelDecoration levelDecoration;
	public AssetSounds sounds; 
	public AssetMusic music;
	
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
		// load sounds  
		assetManager.load("assets-raw/sounds/jump.wav", Sound.class);  
		assetManager.load("assets-raw/sounds/jump_with_clock.wav", Sound.class);  
		assetManager.load("assets-raw/sounds/pickupCoin.wav", Sound.class);  
		assetManager.load("assets-raw/sounds/pickupClock.wav", Sound.class);  
		assetManager.load("assets-raw/sounds/live_lost.wav", Sound.class);  
		// load music  
		assetManager.load("assets-raw/music/keith303_-_brand_new_highscore.mp3",  Music.class);
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
		
		//create game resource objects
		fonts = new AssetFonts();
		thief = new AssetThief(atlas);
		floor = new AssetFloor(atlas);
		goldCoin = new AssetGoldCoin(atlas);
		clock = new AssetClock(atlas);
		levelDecoration = new AssetLevelDecoration(atlas);
		sounds = new AssetSounds(assetManager);  
		music = new AssetMusic(assetManager);
	}
	
	/**
	 * Calls the dispose method to clean up resources.
	 */
	@Override
	public void dispose()
	{
		assetManager.dispose();
		fonts.defaultSmall.dispose();
		fonts.defaultNormal.dispose();
		fonts.defaultBig.dispose();
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
	
	/**
	 * Inner class of the asset fonts to be used by GUI objects.
	 */
	public class AssetFonts
	{
		public final BitmapFont defaultSmall;
		public final BitmapFont defaultNormal;
		public final BitmapFont defaultBig;
		
		public AssetFonts()
		{
			//create three fonts using Libgdx's 15px bitmap font
			defaultSmall = new BitmapFont(Gdx.files.internal("assets-raw/justanotherescape/arial-15.fnt"), true);
			defaultNormal = new BitmapFont(Gdx.files.internal("assets-raw/justanotherescape/arial-15.fnt"), true);
			defaultBig = new BitmapFont(Gdx.files.internal("assets-raw/justanotherescape/arial-15.fnt"), true);
			
			//set font sizes
			defaultSmall.getData().setScale(0.75f);
			defaultNormal.getData().setScale(1.0f);
			defaultBig.getData().setScale(2.0f);
			
			//enable linear texture filtering for smooth fonts
			defaultSmall.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultNormal.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
			defaultBig.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		}
	}
	
	/**
	 * Inner class for loading all of the sounds.
	 * @author Jason LoBianco
	 */
	public class AssetSounds 
	{  
		public final Sound jump;  
		public final Sound jumpWithClock;  
		public final Sound pickupCoin;  
		public final Sound pickupClock;  
		public final Sound liveLost;
		
		/**
		 * Sets all of the sounds variables to the correct sound file.
		 * @param am
		 */
		public AssetSounds (AssetManager am) 
		{    
			jump = am.get("assets-raw/sounds/jump.wav", Sound.class);
		    jumpWithClock = am.get("assets-raw/sounds/jump_with_clock.wav",  Sound.class);
		    pickupCoin = am.get("assets-raw/sounds/pickupCoin.wav", Sound.class);
		    pickupClock = am.get("assets-raw/sounds/pickupClock.wav",  Sound.class);
		    liveLost = am.get("assets-raw/sounds/live_lost.wav", Sound.class);
		} 
	}
	
	/**
	 * Inner class for loading the music.
	 * @author Jason LoBianco
	 */
	public class AssetMusic 
	{  
		public final Music song01;
		
		/**
		 * loads the music into a variable.
		 * @param am
		 */
		public AssetMusic (AssetManager am) 
		{    
			song01 = am.get("assets-raw/music/keith303_-_brand_new_highscore.mp3",  Music.class);  
		} 
	}
}
