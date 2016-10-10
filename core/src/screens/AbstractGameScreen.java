package screens;
/**
 * An abstract class for the game screens to extend.
 */
import objects.Assets;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;

public abstract class AbstractGameScreen implements Screen 
{
	protected Game game;
	
	/**
	 * creates an AbstractGameScreen.
	 * @param game
	 */
	public AbstractGameScreen(Game game)
	{
		this.game = game;
	}
	
	public abstract void render(float deltaTime);
	public abstract void resize(int width, int height);
	public abstract void show();
	public abstract void hide();
	public abstract void pause();
	
	/**
	 * resumes the game.
	 */
	public void resume()
	{
		Assets.instance.init(new AssetManager());
	}
	
	/**
	 * cleans up the game screen
	 */
	public void dispose()
	{
		Assets.instance.dispose();
	}
}