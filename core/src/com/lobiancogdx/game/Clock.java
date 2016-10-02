package com.lobiancogdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The Clock class is responsible for creating and maintaining the 
 * clock asset in the game.
 * @author Jason LoBianco
 */
public class Clock extends AbstractGameObject
{
private TextureRegion regClock;
	
	public boolean collected;
	
	/**
	 * Constructor that calls the init() method to set up a
	 * clock.
	 */
	public Clock()
	{
		init();
	}
	
	/**
	 * sets up the clock asset.
	 */
	private void init()
	{
		dimension.set(0.5f, 0.5f);
		regClock = Assets.instance.clock.clock;
		
		//Set bouding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		collected = false;
	}
	
	/**
	 * draws the clock.
	 */
	public void render(SpriteBatch batch)
	{
		if(collected) return;
		
		TextureRegion reg = null;
		reg = regClock;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y,
				dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
				reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
	
	/**
	 * returns a score of 250.
	 * @return
	 */
	public int getScore()
	{
		return 250;
	}
}
