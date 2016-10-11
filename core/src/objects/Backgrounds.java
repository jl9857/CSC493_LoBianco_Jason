package objects;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

/**
 * The Backgrounds class is responsible for switching
 * the background color of the game.
 * @author Jason LoBianco
 */
public class Backgrounds extends AbstractGameObject
{
	private TextureRegion regBackgroundWhite;			//left is white
	private TextureRegion regBackgroundGray;			//right is gray
	
	private int length;
	
	public Backgrounds(int length)
	{
		this.length = length;
		init();
	}
	
	/**
	 * sets the dimensions of the backgrounds and loads the assets for use.
	 */
	private void init()
	{
		dimension.set(10, 2);
		
		regBackgroundWhite = Assets.instance.levelDecoration.background01;			//background01 = white
		regBackgroundGray = Assets.instance.levelDecoration.background02;			//background02 = gray
		
		//shift background color and extend length
		origin.x = -dimension.x * 2;
		length += dimension.x * 2;
	}
	
	/**
	 * draws the backgrounds starting with the white then gray.
	 * @param batch
	 * @param offsetX
	 * @param offsetY
	 * @param tintColor
	 */
	private void drawMountain(SpriteBatch batch, float offsetX, float offsetY, float tintColor)
	{
		TextureRegion reg = null;
		batch.setColor(tintColor, tintColor, tintColor, 1);
		float xRel = dimension.x * offsetX;
		float yRel = dimension.y * offsetY;
		
		//backgrounds span the whole level
		int backgroundLength = 0;
		backgroundLength += MathUtils.ceil(length / (2 * dimension.x));
		backgroundLength += MathUtils.ceil(0.5f + offsetX);
		for(int i = 0; i < backgroundLength; i++)
		{
			//white background
			reg = regBackgroundWhite;
			batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y + yRel, origin.x,
					origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
					reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			xRel += dimension.x;
			
			//gray background
			reg = regBackgroundGray;
			batch.draw(reg.getTexture(), origin.x + xRel, position.y + origin.y + yRel, origin.x,
					origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
					reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(), false, false);
			xRel += dimension.x;
		}
		
		//reset color to white
		batch.setColor(1, 1, 1, 1);
	}
	
	/**
	 * draws the backgrounds
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		//backgrounds (gray)
		drawMountain(batch, 0.25f, 0.25f, 0.7f);
	}
}
