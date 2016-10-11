package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * 
 * @author Jason LoBianco
 */
public class Crates extends AbstractGameObject
{
	private float length;
	
	private TextureRegion regCrates;
	private Array<Crate> crates;
	
	
	private class Crate extends AbstractGameObject
	{
		private TextureRegion regCrate;
		
		public Crate()
		{
			
		}
		
		public void setRegion(TextureRegion region)
		{
			regCrate = region;
		}
		
		@Override
		public void render(SpriteBatch batch)
		{
			TextureRegion reg = regCrate;
			batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y,
					origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
					reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
		}
	}
	
	public Crates(float length)
	{
		this.length = length;
		init();
	}
	
	private void init()
	{
		dimension.set(3.0f, 1.5f);
		regCrates = Assets.instance.levelDecoration.crate;
		
		int distFac = 5;
		int numCrates = (int) (length /distFac);
		crates = new Array<Crate>(2 * numCrates);
		for(int i = 0; i < numCrates; i++)
		{
			Crate crate = spawnCrate();
			crate.position.x = i * distFac;
			crates.add(crate);
		}
	}
	
	private Crate spawnCrate()
	{
		Crate crate = new Crate();
		crate.dimension.set(dimension);
		//select random crate image
		crate.setRegion(regCrates);
		//position
		Vector2 pos = new Vector2();
		pos.x = length + 10;
		pos.y += 1.75;
		pos.y += MathUtils.random(0.0f, 0.2f) * (MathUtils.randomBoolean() ? 1 : -1);
		crate.position.set(pos);
		return crate;
	}
	
	/**
	 * draws the array of crates
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		for(Crate crate : crates)
		{
			crate.render(batch);
		}
	}
	
}
