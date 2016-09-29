package com.lobiancogdx.game;
/**
 * The rock class is responsible for creating and maintaining the 
 * rocks in the game.
 * @author Jason LoBianco
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Rock extends AbstractGameObject
{
	private TextureRegion regEdge;
	private TextureRegion regMiddle;
	
	private int length;
	
	/**
	 * Constructor that calls the init() method to set up the
	 * rock asset.
	 */
	public Rock()
	{
		init();
	}
	
	/**
	 * set the edge and middle of the rocks with dimensions
	 */
	private void init()
	{
		dimension.set(1, 1.5f);
		
		regEdge = Assets.instance.rock.edge;
		regMiddle = Assets.instance.rock.middle;
		
		//Start length of this rock
		setLength(1);
	}
	
	/**
	 * set the length of the rocks
	 * @param length
	 */
	public void setLength(int length)
	{
		this.length = length;
		
		//Update bounding box for collision detection
		bounds.set(0, 0, dimension.x * length, dimension.y);
	}
	
	/**
	 * increase the length of the rocks
	 * @param amount
	 */
	public void increaseLength(int amount)
	{
		setLength(length + amount);
	}
	
	/**
	 * Draws the rocks in three steps, first draws the left edge
	 * then the middle of the rock up to n amount of rocks, and 
	 * finishes by drawing the right edge.
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = null;
		
		float relX = 0;
		float relY = 0;
		
		//Draw left edge
		reg = regEdge;
		relX -= dimension.x/4;
		batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y,
				dimension.x/4, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
				reg.getRegionWidth(), reg.getRegionHeight(), false, false);
		
		//Draw middle
		relX = 0;
		reg = regMiddle;
		for(int i =0; i < length; i++)
		{
			batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y,
					dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			relX += dimension.x;
		}
		
		//Draw right edge
		reg = regEdge;
		batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x + dimension.x / 8,
				origin.y, dimension.x / 4, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
				reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), true, false);
	}
}