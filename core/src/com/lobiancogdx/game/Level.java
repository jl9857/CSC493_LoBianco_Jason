package com.lobiancogdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * The Level class is responsible for setting up the level for
 * the game world.
 * @author Jason LoBianco
 */
public class Level 
{
	public static final String TAG = Level.class.getName();
	
	public enum BLOCK_TYPE 
	{
		EMPTY(0, 0, 0),							//black
		FLOOR(255, 255, 255),					//white
		PLAYER_SPAWNPOINT(0, 255, 0),			//green
		ITEM_CLOCK(255, 0, 255),				//purple
		ITEM_GOLD_COIN(255, 255, 0);			//yellow
		
		private int color;
		
		private BLOCK_TYPE(int r, int g, int b)
		{
			color = r << 24 | g << 16 | b << 8 | 0xff;
		}
		
		/**
		 * checks to see if a pixel is the same color
		 * @param color
		 * @return
		 */
		public boolean sameColor(int color)
		{
			return this.color == color;
		}
		
		/**
		 * returns the color of a pixel
		 * @return
		 */
		public int getColor()
		{
			return color;
		}
	}
	
	//objects
	public Array<Floor> floor;
	
	//decoration
	public Windows windows;
	public Crates crates;
	public Backgrounds backgrounds;
	public Spike spike;
	
	public Thief thief;
	public Array<GoldCoin> goldCoins;
	public Array<Clock> clocks;
	
	/**
	 * Constructor to call init() method.
	 * @param filename
	 */
	public Level(String filename)
	{
		init(filename);
	}
	
	/**
	 * init the level for the game.
	 * @param filename
	 */
	private void init(String filename)
	{
		//player character
		thief = null;
		
		//objects
		floor = new Array<Floor>();
		goldCoins = new Array<GoldCoin>();
		clocks = new Array<Clock>();
		
		//load image file that represents the level data
		Pixmap pixmap = new Pixmap(Gdx.files.internal(filename));
		
		//scan pixels from top-left to bottom-right
		int lastPixel = -1;
		for(int pixelY = 0; pixelY < pixmap.getHeight(); pixelY++)
		{
			for(int pixelX = 0; pixelX < pixmap.getWidth(); pixelX++)
			{
				AbstractGameObject obj = null;
				float offsetHeight = 0;
				
				//height grows from bottom to top
				float baseHeight = pixmap.getHeight() - pixelY;
				
				//get color of current pixel as 32-bit RGBA value
				int currentPixel = pixmap.getPixel(pixelX, pixelY);
				//find matching color value to identify block type at (x,y)
				//point and create the corresponding game object if there is
				//a match
				
				//empty space
				if(BLOCK_TYPE.EMPTY.sameColor(currentPixel))
				{
					//do nothing
				}
				//rock
				else if(BLOCK_TYPE.FLOOR.sameColor(currentPixel))
				{
					if(lastPixel != currentPixel)
					{
						obj = new Floor();
						float heightIncreaseFactor = 0.25f;
						offsetHeight = -2.5f;
						obj.position.set(pixelX, baseHeight * obj.dimension.y * heightIncreaseFactor + offsetHeight);
						floor.add((Floor)obj);
					}
					else
					{
						floor.get(floor.size - 1).increaseLength(1);
					}
				}
				//player spawn point
				else if(BLOCK_TYPE.PLAYER_SPAWNPOINT.sameColor(currentPixel))
				{
					obj = new Thief();
					offsetHeight = -3.0f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					thief = (Thief)obj;
				}
				//clock
				else if(BLOCK_TYPE.ITEM_CLOCK.sameColor(currentPixel))
				{
					obj = new Clock();
					offsetHeight = -1.5f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					clocks.add((Clock)obj);
				}
				//gold coin
				else if(BLOCK_TYPE.ITEM_GOLD_COIN.sameColor(currentPixel))
				{
					obj = new GoldCoin();
					offsetHeight = -1.5f;
					obj.position.set(pixelX, baseHeight * obj.dimension.y + offsetHeight);
					goldCoins.add((GoldCoin)obj);
				}
				//unknown object/pixel color
				else 
				{
					int r = 0xff & (currentPixel >>> 24);		//red color channel
					int g = 0xff & (currentPixel >>> 16);		//green color channel
					int b = 0xff & (currentPixel >>> 8);		//blue color channel
					int a = 0xff & currentPixel;				//alpha channel
					Gdx.app.error(TAG, "Unknown object at x<" + pixelX + "> y<" + pixelY +
							">: r<" + r+ "> g<" + g + "> b<" + b + "> a<" + a + ">");
				}
				lastPixel = currentPixel;
			}
		}
		//decoration
		windows = new Windows(pixmap.getWidth());
		windows.position.set(0, 2);
		crates = new Crates(pixmap.getWidth());
		crates.position.set(0, 0.35f);
		backgrounds = new Backgrounds(pixmap.getWidth());
		backgrounds.position.set(-1, -1);
		spike = new Spike(pixmap.getWidth());
		spike.position.set(0, -3.75f);
		
		//free memory
		pixmap.dispose();
		Gdx.app.debug(TAG, "level '" + filename + "' loaded");
	}
	
	/**
	 * draws all the objects to the world.
	 * @param batch
	 */
	public void render(SpriteBatch batch)
	{
		//Draw Backgrounds
		backgrounds.render(batch);
		
		//Draw Floors
		for(Floor floor : floor)
		{
			floor.render(batch);
		}
		
		//Draw Gold Coins
		for(GoldCoin goldCoin : goldCoins)
		{
			goldCoin.render(batch);
		}
				
		//Draw Clocks
		for(Clock clock : clocks)
		{
			clock.render(batch);
		}
				
		//Draw Player Character
		thief.render(batch);
		
		//Draw Spikes
		spike.render(batch);
		
		//Draw Windows
		windows.render(batch);
		
		//Draw Crates
		crates.render(batch);
	}
	
	/**
	 * updates all of the game objects of the game.
	 * @param deltaTime
	 */
	public void update(float deltaTime)
	{
		thief.update(deltaTime);
		
		for(Floor floor : floor)
		{
			floor.update(deltaTime);
		}
		
		for(GoldCoin goldCoin : goldCoins)
		{
			goldCoin.update(deltaTime);
		}
		
		for(Clock clock : clocks)
		{
			clock.update(deltaTime);
		}
		
		windows.update(deltaTime);
		
		crates.update(deltaTime);
	}
}