package objects;
/**
 * The Feather class is responsible for creating and maintaining
 * Feathers in the game.
 * @author Jason LoBianco
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Feather extends AbstractGameObject
{
	private TextureRegion regFeather;
	
	public boolean collected;
	
	/**
	 * Constructor that calls the init() method to set up a
	 * feather.
	 */
	public Feather()
	{
		init();
	}
	
	/**
	 * sets up the feather asset.
	 */
	private void init()
	{
		dimension.set(0.5f, 0.5f);
		regFeather = Assets.instance.feather.feather;
		
		//Set bouding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		collected = false;
	}
	
	/**
	 * draws the feather.
	 */
	public void render(SpriteBatch batch)
	{
		if(collected) return;
		
		TextureRegion reg = null;
		reg = regFeather;
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
