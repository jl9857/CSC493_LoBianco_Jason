package objects;
/**
 * The GoldCoin class is responsible for creating and maintaining
 * GoldCoins in the game.
 * @author Jason LoBianco
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class GoldCoin extends AbstractGameObject
{
	private TextureRegion regGoldCoin;
	
	public boolean collected;
	
	/**
	 * Constructor that calls the init() method for setup.
	 */
	public GoldCoin()
	{
		init();
	}
	
	/**
	 * sets up the GoldCoin asset.
	 */
	private void init()
	{
		dimension.set(0.5f, 0.5f);
		regGoldCoin = Assets.instance.goldCoin.goldCoin;
		
		//Set bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		collected = false;
	}
	
	/**
	 * draws the gold coin.
	 */
	public void render(SpriteBatch batch)
	{
		if(collected) return;
		
		TextureRegion reg = null;
		reg = regGoldCoin;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y,
				dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
				reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
	}
	
	/**
	 * returns a score of 100.
	 * @return
	 */
	public int getScore()
	{
		return 100;
	}
}