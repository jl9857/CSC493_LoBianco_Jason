package objects;

import screen.GamePreferences;
import screen.CharacterSkin;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lobiancogdx.game.Constants;

/**
 * The Thief class is responsible for creating and maintaining the 
 * thief asset in the game.
 * @author Jason LoBianco
 */

public class Thief extends AbstractGameObject
{
	public static final String TAG = Thief.class.getName();
	public ParticleEffect dustParticles = new ParticleEffect();
	
	private final float JUMP_TIME_MAX = 0.6f;
	private final float JUMP_TIME_MIN = 0.2f;
	private final float JUMP_TIME_OFFSET_FLYING = JUMP_TIME_MAX - 0.036f;
	
	public enum VIEW_DIRECTION
	{
		LEFT,
		RIGHT
	}
	
	public enum JUMP_STATE
	{
		GROUNDED, 
		FALLING,
		JUMP_RISING,
		JUMP_FALLING
	}
	
	private TextureRegion regThief;
	
	public VIEW_DIRECTION viewDirection;
	public float timeJumping;
	public JUMP_STATE jumpState;
	public boolean hasClockPowerup;
	public float timeLeftClockPowerup;
	
	/**
	 * Constructor that calls the init() method to set up a
	 * thief.
	 */
	public Thief()
	{
		init();
	}
	
	/**
	 * Sets up the thief asset.
	 */
	public void init()
	{
		dimension.set(1, 1);
		regThief = Assets.instance.thief.thief;
		
		//Center image on game object
		origin.set(dimension.x / 2, dimension.y / 2);
		
		//Bounding box for collision detection
		bounds.set(0, 0, dimension.x, dimension.y);
		
		//Set physics values
		terminalVelocity.set(3.0f, 4.0f);
		friction.set(12.0f, 0.0f);
		acceleration.set(0.0f, -25.0f);
		
		//View direction
		viewDirection = VIEW_DIRECTION.RIGHT;
		
		//Jump state
		jumpState = JUMP_STATE.FALLING;
		timeJumping = 0;
		
		//Power-ups
		hasClockPowerup = false;
		timeLeftClockPowerup = 0;
		
		//Particles
		dustParticles.load(Gdx.files.internal("assets-raw/particles/dust.pfx"), 
				Gdx.files.internal("assets-raw/particles"));
	}
	
	/**
	 * sets the jump state of the thief.
	 * @param jumpKeyPressed
	 */
	public void setJumping(boolean jumpKeyPressed)
	{
		switch(jumpState)
		{
		case GROUNDED: //Character is standing on a platform
			if(jumpKeyPressed)
			{
				//Start counting jump time from the beginning
				timeJumping = 0;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		case JUMP_RISING: //Rising in the air
			if(!jumpKeyPressed)
			{
				jumpState = JUMP_STATE.JUMP_FALLING;
				break;
			}
		case FALLING: //Falling down
		case JUMP_FALLING: //Falling down after jump
			if(jumpKeyPressed && hasClockPowerup)
			{
				timeJumping = JUMP_TIME_OFFSET_FLYING;
				jumpState = JUMP_STATE.JUMP_RISING;
			}
			break;
		}
	}
	
	/**
	 * sets hasClockPowerup and if true sets the duration of the
	 * power up.
	 * @param pickedUp
	 */
	public void setClockPowerup(boolean pickedUp)
	{
		hasClockPowerup = pickedUp;
		if(pickedUp)
		{
			timeLeftClockPowerup = Constants.ITEM_CLOCK_POWERUP_DURATION;
		}
	}
	
	/**
	 * returns true if the thief has the clock powerup and there
	 * is time left on the powerup.
	 * @return
	 */
	public boolean hasClockPowerup()
	{
		return hasClockPowerup && timeLeftClockPowerup > 0;
	}
	
	/**
	 * changes the view direction based on the current move direction
	 * and checks if there is remaining time on the clock power up.
	 */
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		if(velocity.x != 0)
		{
			viewDirection = velocity.x < 0 ? VIEW_DIRECTION.LEFT : VIEW_DIRECTION.RIGHT;
		}
		
		if(timeLeftClockPowerup > 0)
		{
			timeLeftClockPowerup -= deltaTime;
		}
		
		if(timeLeftClockPowerup < 0)
		{
			//disable power-up
			timeLeftClockPowerup = 0;
			setClockPowerup(false);
		}
	}
	
	/**
	 * handles the calculations and switching of states that is needed
	 * to enable jumping and falling.
	 */
	@Override
	protected void updateMotionY(float deltaTime)
	{
		switch(jumpState)
		{
		case GROUNDED:
			jumpState = JUMP_STATE.FALLING;
			break;
		case JUMP_RISING:
			//keep track of jump time
			timeJumping += deltaTime;
			//Jump time left?
			if(timeJumping <= JUMP_TIME_MAX)
			{
				//Still jumping
				velocity.y = terminalVelocity.y;
			}
			break;
		case FALLING:
			break;
		case JUMP_FALLING:
			//Add delta times to track jump time
			timeJumping += deltaTime;
			//Jump to minimal height if jump key was pressed too short
			if(timeJumping > 0 && timeJumping <= JUMP_TIME_MIN)
			{
				//Still jumping
				velocity.y = terminalVelocity.y;
			}
		}
		if(jumpState != JUMP_STATE.GROUNDED)
		{
			dustParticles.allowCompletion();
			super.updateMotionY(deltaTime);
		}
	}
	
	/**
	 * draws the image of the thief for the game.
	 */
	@Override
	public void render(SpriteBatch batch)
	{
		TextureRegion reg = null;
		
		//Draw Particles
		dustParticles.draw(batch);
		
		//Apply Skin Color
		batch.setColor(CharacterSkin.values()[GamePreferences.instance.charSkin].getColor());
		
		//Set special color when game object has a clock power-up
		if(hasClockPowerup)
		{
			batch.setColor(1.0f, 0.8f, 0.0f, 1.0f);
		}
		
		//Draw image
		reg = regThief;
		batch.draw(reg.getTexture(), position.x, position.y, origin.x, origin.y,
				dimension.x, dimension.y, scale.x, scale.y, rotation, reg.getRegionX(),
				reg.getRegionY(), reg.getRegionWidth(), reg.getRegionHeight(),
				viewDirection == VIEW_DIRECTION.LEFT, false);
		
		//Reset color to white
		batch.setColor(1, 1, 1, 1);
	}
}
