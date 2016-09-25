package com.lobiancogdx.game;
/**
 * 
 * @author Jason LoBianco
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class AbstractGameObject 
{
	public Vector2 position;
	public Vector2 dimension;
	public Vector2 origin;
	public Vector2 scale;
	public float rotation;
	
	public AbstractGameObject()
	{
		position = new Vector2();
		dimension = new Vector2(1, 1);
		origin = new Vector2();
		scale = new Vector2(1, 1);
		rotation = 0;
	}
	
	/**
	 * updates the game based of a deltaTime
	 * @param deltaTime
	 */
	public void update(float deltaTime)
	{
		
	}
	
	/**
	 * needs to be overridden to draw game objects
	 * @param batch
	 */
	public abstract void render(SpriteBatch batch);
}