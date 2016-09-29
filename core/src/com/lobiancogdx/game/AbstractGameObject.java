package com.lobiancogdx.game;
/**
 * The AbstractGameObject class is used to give its children all the 
 * values and methods a game object will need.
 * @author Jason LoBianco
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject 
{
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	public Vector2 velocity;
	public Vector2 terminalVelocity;
	public Vector2 friction;
	public Vector2 acceleration;
	public Rectangle bounds;
	
	/**
	 * Creates an AbstractGameObject with starting values.
	 */
	public AbstractGameObject()
	{
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
		velocity = new Vector2();
		terminalVelocity = new Vector2(1, 1);
		friction = new Vector2();
		acceleration = new Vector2();
		bounds = new Rectangle();
	}
	
	/**
	 * updates the game objects position based of a velocity
	 * @param deltaTime
	 */
	public void update(float deltaTime)
	{
		updateMotionX(deltaTime);
		updateMotionY(deltaTime);
		//Move to new position
		position.x += velocity.x * deltaTime;
		position.y += velocity.y * deltaTime;
	}
	
	/**
	 * needs to be overridden to draw game objects
	 * @param batch
	 */
	public abstract void render(SpriteBatch batch);
	
	/**
	 * updates the motion in the x direction.
	 * @param deltaTime
	 */
	protected void updateMotionX(float deltaTime)
	{
		if(velocity.x != 0)
		{
			//Apply friction
			if(velocity.x > 0)
			{
				velocity.x = Math.max(velocity.x - friction.x * deltaTime, 0);
			}
			else
			{
				velocity.x = Math.min(velocity.x + friction.x * deltaTime, 0);
			}
		}
		//Apply acceleration
		velocity.x += acceleration.x * deltaTime;
		//Make sure the object's velocity does not exceed the
		//positive or negative terminal velocity
		velocity.x = MathUtils.clamp(velocity.x, -terminalVelocity.x, terminalVelocity.x);
	}
	
	/**
	 * updates the motion in the y direction.
	 * @param deltaTime
	 */
	protected void updateMotionY(float deltaTime)
	{
		if(velocity.y != 0)
		{
			//Apply friction
			if(velocity.y > 0)
			{
				velocity.y = Math.max(velocity.y - friction.y * deltaTime, 0);
			}
			else
			{
				velocity.y = Math.min(velocity.y + friction.y * deltaTime, 0);
			}
		}
		//Apply acceleration
		velocity.y += acceleration.y * deltaTime;
		//Make sure the object's velocity does not exceed the
		//positive or negative terminal velocity
		velocity.y = MathUtils.clamp(velocity.y, -terminalVelocity.y, terminalVelocity.y);
	}
}