package com.lobiancogdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * The Windows class is responsible for placing windows on the map.
 * @author Jason LoBianco
 */
public class Windows extends AbstractGameObject
{
	private float length;
	
	private TextureRegion regWindows;
	private Array<Window> windows;
	
	private class Window extends AbstractGameObject
	{
		private TextureRegion regWindow;
		
		public Window()
		{
			
		}
		
		public void setRegion(TextureRegion region)
		{
			regWindow = region;
		}
		
		@Override
		public void render(SpriteBatch batch)
		{
			TextureRegion reg = regWindow;
			batch.draw(reg.getTexture(), position.x + origin.x, position.y + origin.y,
					origin.x, origin.y, dimension.x, dimension.y, scale.x, scale.y, rotation,
					reg.getRegionX(), reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
					false, false);
		}
	}
	
	public Windows(float length)
	{
		this.length = length;
		init();
	}
	
	private void init()
	{
		dimension.set(3.0f, 1.5f);
		regWindows = Assets.instance.levelDecoration.windowOpen;
		
		int distFac = 5;
		int numWindows = (int) (length /distFac);
		windows = new Array<Window>(2 * numWindows);
		for(int i = 0; i < numWindows; i++)
		{
			Window window = spawnWindow();
			window.position.x = i * distFac;
			windows.add(window);
		}
	}
	
	private Window spawnWindow()
	{
		Window window = new Window();
		window.dimension.set(dimension);
		//select random window image
		window.setRegion(regWindows);
		//position
		Vector2 pos = new Vector2();
		pos.x = length + 10;
		pos.y += 1.75;
		pos.y += MathUtils.random(0.0f, 0.2f) * (MathUtils.randomBoolean() ? 1 : -1);
		window.position.set(pos);
		return window;
	}
	
	/**
	 * draws the array of windows.
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		for(Window window : windows)
		{
			window.render(batch);
		}
	}
}
