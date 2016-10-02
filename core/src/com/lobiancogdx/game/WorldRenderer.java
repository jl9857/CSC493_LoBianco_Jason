package com.lobiancogdx.game;
/**
 * The WorldRender is responsible for redrawing the game objects
 * any time they are updated by the WorldController.
 * @author Jason LoBianco
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;

public class WorldRenderer implements Disposable 
{
	private OrthographicCamera camera;
	private OrthographicCamera cameraGUI;
	private SpriteBatch batch;
	private WorldController worldController;
	
	/**
	 * Constructor that calls the init() method.
	 * @param worldController
	 */
	public WorldRenderer(WorldController worldController)
	{
		this.worldController = worldController;
		init();
	}
	
	/**
	 * sets up the SpriteBatch and the world camera
	 * and the GUI camera.
	 */
	private void init()
	{
		batch = new SpriteBatch();
		camera = new OrthographicCamera(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT);
		camera.position.set(0, 0, 0);
		camera.update();
		cameraGUI = new OrthographicCamera(Constants.VIEWPORT_GUI_WIDTH, Constants.VIEWPORT_GUI_HEIGHT);
		cameraGUI.position.set(0, 0, 0);
		//flip y-axis
		cameraGUI.setToOrtho(true);
		cameraGUI.update();
	}
	
	/**
	 * redraws the game objects
	 */
	public void render()
	{
		renderWorld();
		renderGui(batch);
	}
	
	/**
	 * draws the world.
	 */
	private void renderWorld()
	{
		worldController.cameraHelper.applyTo(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		worldController.level.render(batch);
		batch.end();
	}
	
	/**
	 * used when the window is resized to keep the size of objects.
	 * @param width
	 * @param height
	 */
	public void resize(int width, int height)
	{
		camera.viewportWidth = (Constants.VIEWPORT_HEIGHT / height) * width;
		camera.update();
		cameraGUI.viewportHeight = Constants.VIEWPORT_GUI_HEIGHT;
		cameraGUI.viewportWidth = (Constants.VIEWPORT_GUI_HEIGHT / (float) height) * (float) width;
		cameraGUI.position.set(cameraGUI.viewportWidth / 2, cameraGUI.viewportHeight / 2, 0);
		cameraGUI.update();
	}
	
	/**
	 * cleans up the game world
	 */
	@Override
	public void dispose()
	{
		batch.dispose();
	}
	
	/**
	 * draws the score GUI in the upper level corner.
	 * @param batch
	 */
	private void renderGuiScore(SpriteBatch batch)
	{
		float x = -15;
		float y = -15;
		batch.draw(Assets.instance.goldCoin.goldCoin, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
		Assets.instance.fonts.defaultBig.draw(batch, "" + worldController.score, x + 75, y + 37);
	}
	
	/**
	 * draws the lives in the upper right corner
	 * @param batch
	 */
	private void renderGuiExtraLive(SpriteBatch batch)
	{
		float x = cameraGUI.viewportWidth - 50 - Constants.LIVES_START * 50;
		float y = -15;
		for(int i = 0; i < Constants.LIVES_START; i++)
		{
			if(worldController.lives <= i)
			{
				batch.setColor(0.5f, 0.5f, 0.5f, 0.5f);
			}
			batch.draw(Assets.instance.thief.thief, x + i * 50, y, 50, 50, 120, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
		}
	}
	
	/**
	 * draws fps counter in the lower right corner.
	 * @param batch
	 */
	private void renderGuiFpsCounter(SpriteBatch batch)
	{
		float x = cameraGUI.viewportWidth - 55;
		float y = cameraGUI.viewportHeight - 15;
		int fps = Gdx.graphics.getFramesPerSecond();
		BitmapFont fpsFont = Assets.instance.fonts.defaultNormal;
		if(fps >= 45)
		{
			//45 or more FPS show up in green
			fpsFont.setColor(0, 1, 0, 1);
		}
		else if(fps >= 30)
		{
			//30 or more FPS show up in yellow
			fpsFont.setColor(1, 1, 0, 1);
		}
		else
		{
			//less than 30 FPS show up in red
			fpsFont.setColor(1, 0, 0, 1);
		}
		fpsFont.draw(batch, "FPS: " + fps, x, y);
		fpsFont.setColor(1, 1, 1, 1);		//white
	}
	
	/**
	 * draws the timer in the lower left corner.
	 * @param batch
	 */
	private void renderGuiTimer(SpriteBatch batch)
	{
		
	}
	
	/**
	 * one call to call all over GUI objects to draw.
	 * @param batch
	 */
	private void renderGui(SpriteBatch batch)
	{
		batch.setProjectionMatrix(cameraGUI.combined);
		batch.begin();
		//draw collected gold coins icon + text
		//(anchored to top left edge)
		renderGuiScore(batch);
		//draw collected feather icon (anchored to top left edge)
		renderGuiClockPowerup(batch);
		//draw extra lives icon + text (anchored to top right edge)
		renderGuiExtraLive(batch);
		//draw FPS text (anchored to bottom right edge)
		renderGuiFpsCounter(batch);
		//draw Timer text (anchored to bottom left edge)
		renderGuiTimer(batch);
		//draw game over text
		renderGuiGameOverMessage(batch);
				
		batch.end();
	}
	
	/**
	 * draws the game over message if you lose.
	 * @param batch
	 */
	private void renderGuiGameOverMessage(SpriteBatch batch)
	{
		float x = cameraGUI.viewportWidth / 2;
		float y = cameraGUI.viewportHeight / 2;
		if(worldController.isGameOver())
		{
			BitmapFont fontGameOver = Assets.instance.fonts.defaultBig;
			fontGameOver.setColor(1, 0.75f, 0.25f, 1);
			fontGameOver.draw(batch, "GAME OVER", x, y);
			fontGameOver.setColor(1, 1, 1, 1);
		}
	}
	
	private void renderGuiClockPowerup(SpriteBatch batch)
	{
		float x = -15;
		float y = 30;
		float timeLeftClockPowerup = worldController.level.thief.timeLeftClockPowerup;
		if(timeLeftClockPowerup > 0)
		{
			//Start icon fade in/out if the left power-up time
			//is less than 4 seconds.  The fade interval is set
			//to 5 changes per second.
			if(timeLeftClockPowerup < 4)
			{
				if(((int) (timeLeftClockPowerup * 5) % 2) != 0)
				{
					batch.setColor(1, 1, 1, 0.5f);
				}
			}
			batch.draw(Assets.instance.clock.clock, x, y, 50, 50, 100, 100, 0.35f, -0.35f, 0);
			batch.setColor(1, 1, 1, 1);
			Assets.instance.fonts.defaultSmall.draw(batch, "" + (int)timeLeftClockPowerup, x + 60, y + 57);
		}
	}
}
