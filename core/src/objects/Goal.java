package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The goal class is responsible for handling the goal object in the game.
 * @author Jason LoBianco
 */
public class Goal extends AbstractGameObject
{
	private TextureRegion regGoal;
	
	/**
	 * constructor that calls the init method.
	 */
	public Goal()
	{
		init();
	}
	
	/**
	 * sets the initial values of a goal.
	 */
	private void init()
	{
		dimension.set(3.0f, 3.0f);
		regGoal = Assets.instance.levelDecoration.goal;
		//Set bounding box for collision detection
		bounds.set(1, Float.MIN_VALUE, 10, Float.MAX_VALUE);
		origin.set(dimension.x / 2.0f, 0.0f);
	}
	
	/**
	 * draws the goal.
	 */
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = null;
		reg = regGoal;
		batch.draw(reg.getTexture(), position.x - origin.x, position.y - origin.y, 
				origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), 
				false, false);
	}
}
