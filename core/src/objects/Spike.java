package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * The Spike class is responsible for draws the spikes
 * all the way across the bottom.
 * @author Jason LoBianco
 */
public class Spike extends AbstractGameObject
{
	private TextureRegion regSpike;
	private float length;
	
	public Spike(float length)
	{
		this.length = length;
		init();
	}
	
	/**
	 * sets the dimensions and loads the spike asset
	 */
	private void init()
	{
		dimension.set(length * 10, 3);
		
		regSpike = Assets.instance.levelDecoration.spike;
		origin.x = -dimension.x / 2;
	}
	
	/**
	 * draws the spikes across the whole map
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = null;
		reg = regSpike;
		batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y,
				origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
				reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				false, false);
	}
}
