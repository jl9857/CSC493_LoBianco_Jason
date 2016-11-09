package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The carrot class is responsible for handling the carrot object.
 * @author Jason LoBianco
 */
public class Carrot extends AbstractGameObject
{
	private TextureRegion regCarrot;
	
	/**
	 * constructor that calls the init method.
	 */
	public Carrot()
	{
		init();
	}
	
	/**
	 * sets the initial values of a carrot.
	 */
	private void init()
	{
		dimension.set(0.25f, 0.25f);
		regCarrot = Assets.instance.levelDecoration.carrot;
		//Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		origin.set(dimension.x / 2, dimension.y / 2);
	}
	
	/**
	 * draws the carrot.
	 */
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = null;
		reg = regCarrot;
		batch.draw(reg.getTexture(), position.x - origin.x, position.y - origin.y, 
				origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, 
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), 
				false, false);
	}
}
