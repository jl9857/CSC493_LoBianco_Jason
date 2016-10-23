package world;
/**
 * The WOrldController is responsible for updating all the game
 * objects in the world.
 * @author Jason LoBianco
 */

import objects.Clock;
import objects.Floor;
import objects.GoldCoin;
import objects.Thief;
import objects.Thief.JUMP_STATE;
import screen.MenuScreen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Rectangle;
import com.lobiancogdx.game.Constants;

public class WorldController extends InputAdapter
{
	private static final String TAG = WorldController.class.getName();
	public CameraHelper cameraHelper;
	public Level level;
	public int lives;
	public int score;
	private float timeLeftGameOverDelay;
	private Game game;
	public float scoreVisual;
	
	//Rectangles for collision detection
	private Rectangle r1 = new Rectangle();
	private Rectangle r2 = new Rectangle();
	
	/**
	 * Constructor that calls the init() method to init the level.
	 */
	public WorldController(Game game)
	{
		this.game = game;
		init();
	}
	
	/**
	 * Sets up the cameraHelper and calls the initLevel()
	 * method to set up the level.
	 */
	private void init()
	{
		Gdx.input.setInputProcessor(this);
		cameraHelper = new CameraHelper();
		lives = Constants.LIVES_START;
		timeLeftGameOverDelay = 0;
		initLevel();
	}
	
	/**
	 * Sets score and level
	 */
	private void initLevel()
	{
		score = 0;
		scoreVisual = score;
		level = new Level(Constants.LEVEL_01);
		cameraHelper.setTarget(level.thief);
	}
	
	/**
	 * updates the debug input and cameraHelper
	 * @param deltaTime
	 */
	public void update(float deltaTime)
	{
		handleDebugInput(deltaTime);
		if(isGameOver())
		{
			timeLeftGameOverDelay -= deltaTime;
			if(timeLeftGameOverDelay < 0) init();
		}
		else
		{
			handleInputGame(deltaTime);
		}
		level.update(deltaTime);
		testCollisions();
		cameraHelper.update(deltaTime);
		if(!isGameOver() && isPlayerInSpikes())
		{
			lives--;
			if(isGameOver())
			{
				timeLeftGameOverDelay = Constants.TIME_DELAY_GAME_OVER;
			}
			else
			{
				initLevel();
			}
		}
		
		if(scoreVisual < score)
		{
			scoreVisual = Math.min(scoreVisual,  scoreVisual + 250 * deltaTime);
		}
	}
	
	/**
	 * gives the user a way to move around the world to debug.
	 * @param deltaTime
	 */
	private void handleDebugInput(float deltaTime)
	{
		if(Gdx.app.getType() != ApplicationType.Desktop) return;
		
		if(!cameraHelper.hasTarget(level.thief))
		{
			//Camera Controls (move)
			float camMoveSpeed = 5 * deltaTime;
			float camMoveSpeedAccelerationFactor = 5;
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camMoveSpeed *= camMoveSpeedAccelerationFactor;
			if(Gdx.input.isKeyPressed(Keys.LEFT)) moveCamera(-camMoveSpeed, 0);
			if(Gdx.input.isKeyPressed(Keys.RIGHT)) moveCamera(camMoveSpeed, 0);
			if(Gdx.input.isKeyPressed(Keys.UP)) moveCamera(0, camMoveSpeed);
			if(Gdx.input.isKeyPressed(Keys.DOWN)) moveCamera(0, -camMoveSpeed);
			if(Gdx.input.isKeyPressed(Keys.BACKSPACE)) cameraHelper.setPosition(0, 0);
		}
		
		//Camera Controls (zoom)
		float camZoomSpeed = 1 * deltaTime;
		float camZoomSpeedAccelerationFactor = 5;
		if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT)) camZoomSpeed *= camZoomSpeedAccelerationFactor;
		if(Gdx.input.isKeyPressed(Keys.COMMA)) cameraHelper.addZoom(camZoomSpeed);
		if(Gdx.input.isKeyPressed(Keys.PERIOD)) cameraHelper.addZoom(-camZoomSpeed);
		if(Gdx.input.isKeyPressed(Keys.SLASH)) cameraHelper.setZoom(1);
	}
	
	/**
	 * moves the camera by an x, y value.
	 * @param x
	 * @param y
	 */
	private void moveCamera(float x, float y)
	{
		x += cameraHelper.getPosition().x;
		y += cameraHelper.getPosition().y;
		cameraHelper.setPosition(x, y);
	}
	
	/**
	 * Gives us a way to reset the world.
	 */
	@Override
	public boolean keyUp(int keycode)
	{
		//Reset game world
		if(keycode == Keys.R)
		{
			init();
			Gdx.app.debug(TAG, "Game world resetted");
		}
		
		//Toggle camera follow
		else if(keycode == Keys.ENTER)
		{
			cameraHelper.setTarget(cameraHelper.hasTarget() ? null : level.thief);
			Gdx.app.debug(TAG, "Camera follow enabled: " + cameraHelper.hasTarget());
		}
		
		//Back to Menu
		else if(keycode == Keys.ESCAPE || keycode == Keys.BACK)
		{
			backToMenu();
		}
		return false;
	}
	
	/**
	 * checks for a collision with the thief and floor.
	 * @param floor
	 */
	private void onCollisionThiefWithFloor(Floor floor)
	{
		Thief thief = level.thief;
		float heightDifference = Math.abs(thief.position.y - 
				(floor.position.y + floor.bounds.height));
		if(heightDifference > 0.25f)
		{
			boolean hitRightEdge = thief.position.x >
			(floor.position.x + floor.bounds.width / 2.0f);
			if(hitRightEdge)
			{
				thief.position.x = floor.position.x + floor.bounds.width;
			}
			else
			{
				thief.position.x = floor.position.x - thief.bounds.width;
			}
			return;
		}
		
		switch(thief.jumpState)
		{
		case GROUNDED:
			break;
		case FALLING:
		case JUMP_FALLING:
			thief.position.y = floor.position.y + thief.bounds.height + thief.origin.y;
			thief.jumpState = JUMP_STATE.GROUNDED;
			break;
		case JUMP_RISING:
			thief.position.y = floor.position.y + thief.bounds.height + thief.origin.y;
			break;
		}
	}
	
	/**
	 * checks for a collision with the thief and gold coins.
	 * @param goldcoin
	 */
	private void onCollisionThiefWithGoldCoin(GoldCoin goldcoin)
	{
		goldcoin.collected = true;
		score += goldcoin.getScore();
		Gdx.app.log(TAG, "Gold coin collected");
	}
	
	/**
	 * checks for a collision with the thief and clock
	 * @param clock
	 */
	private void onCollisionThiefWithClock(Clock clock)
	{
		clock.collected = true;
		score += clock.getScore();
		level.thief.setClockPowerup(true);
		Gdx.app.log(TAG, "Clock collectd");
	}
	
	/**
	 * a test for all of the collisions
	 */
	private void testCollisions()
	{
		r1.set(level.thief.position.x, level.thief.position.y,
				level.thief.bounds.width, level.thief.bounds.height);
		
		//Test collision: Thief <-> Floor
		for(Floor floor: level.floor)
		{
			r2.set(floor.position.x, floor.position.y, floor.bounds.width, floor.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionThiefWithFloor(floor);
			//IMPORTANT: must do all collisions for valid
			//edge testing on floors.
		}
		
		//Test collision: Thief <-> Gold Coins
		for(GoldCoin goldcoin: level.goldCoins)
		{
			if(goldcoin.collected) continue;
			r2.set(goldcoin.position.x, goldcoin.position.y,
					goldcoin.bounds.width, goldcoin.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionThiefWithGoldCoin(goldcoin);
		}
		
		//Test collision: Thief <-> Clocks
		for(Clock clock: level.clocks)
		{
			if(clock.collected) continue;
			r2.set(clock.position.x, clock.position.y,
					clock.bounds.width, clock.bounds.height);
			if(!r1.overlaps(r2)) continue;
			onCollisionThiefWithClock(clock);
		}
	}
	
	/**
	 * handles the game input to the player
	 * @param deltaTime
	 */
	private void handleInputGame(float deltaTime)
	{
		if(cameraHelper.hasTarget(level.thief))
		{
			//Player Movement
			if(Gdx.input.isKeyPressed(Keys.LEFT))
			{
				level.thief.velocity.x = -level.thief.terminalVelocity.x;
				if(level.thief.hasClockPowerup())
				{
					level.thief.velocity.x = (-level.thief.terminalVelocity.x * 2);
				}
			}
			else if(Gdx.input.isKeyPressed(Keys.RIGHT))
			{
				level.thief.velocity.x = level.thief.terminalVelocity.x;
				if(level.thief.hasClockPowerup())
				{
					level.thief.velocity.x = (level.thief.terminalVelocity.x * 2);
				}
			}
			else
			{
				//Execute auto-forward movement on non-desktop platform
				if(Gdx.app.getType() != ApplicationType.Desktop)
				{
					level.thief.velocity.x = level.thief.terminalVelocity.x;
				}
			}
			
			//Thief Jump
			if(Gdx.input.isTouched() || Gdx.input.isKeyPressed(Keys.SPACE))
			{
				level.thief.setJumping(true);
			}
			else
			{
				level.thief.setJumping(false);
			}
		}
	}
	
	/**
	 * checks if the game is over
	 * @return
	 */
	public boolean isGameOver()
	{
		return lives < 0;
	}
	
	/**
	 * checks if the player is on spikes
	 * @return
	 */
	public boolean isPlayerInSpikes()
	{
		return level.thief.position.y < -5;
	}
	
	/**
	 * switches back to the menu screen.
	 */
	private void backToMenu()
	{
		//switch to menu screen
		game.setScreen(new MenuScreen(game));
	}
}