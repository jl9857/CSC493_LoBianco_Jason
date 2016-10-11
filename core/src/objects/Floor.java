package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The Floor class is used to handle the floor in the game.
 * @author Jason LoBianco
 */
public class Floor extends AbstractGameObject
{
	private TextureRegion regFloor;
	
	private int length;
	
	/**
	 * Constructor that calls init() method.
	 */
	public Floor()
	{
		init();
	}
	
	/**
	 * set the dimensions of the floor
	 */
	private void init()
	{
		dimension.set(1, 1.5f);
		
		regFloor = Assets.instance.floor.floor;
		
		//Start length of this floor
		setLength(1);
	}
	
	/**
	 * set the length of the floor
	 * @param length
	 */
	public void setLength(int length)
	{
		this.length = length;
		
		//Update bounding box for collision detection
		bounds.set(0, 0, dimension.x * length, dimension.y);
	}
	
	/**
	 * increase the length of the floor
	 * @param amount
	 */
	public void increaseLength(int amount)
	{
		setLength(length + amount);
	}
	
	/**
	 * Draws the floor.
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = null;
		
		float relX = 0;
		float relY = 0;
		
		//Draw floor
		relX = 0;
		reg = regFloor;
		for(int i =0; i < length; i++)
		{
			batch.draw(reg.getTexture(), position.x + relX, position.y + relY, origin.x, origin.y,
					dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(), reg.getRegionY(),
					reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			relX += dimension.x;
		}
	}
}
