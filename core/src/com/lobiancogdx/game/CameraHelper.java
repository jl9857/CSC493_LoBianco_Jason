package com.lobiancogdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * @author Jason LoBianco
 */

public class CameraHelper 
{
	private static final String TAG = CameraHelper.class.getName();
	
	private final float MAX_ZOOM_IN = 0.25f;
	private final float MAX_ZOOM_OUT = 10.0f;
	
	private Vector2 position;
	private float zoom;
	private AbstractGameObject target;
	
	/**
	 * init the camera helper
	 */
	public CameraHelper()
	{
		position = new Vector2();
		zoom = 1.0f;
	}
	
	/**
	 * update the position of the camera
	 * @param deltaTime
	 */
	public void update(float deltaTime)
	{
		if (!hasTarget()) return;
		
		position.x = target.position.x + target.origin.x;
		position.y = target.position.y + target.origin.y;
	}
	
	/**
	 * set position of the camera
	 * @param x
	 * @param y
	 */
	public void setPosition(float x, float y)
	{
		this.position.set(x,y);
	}
	
	/**
	 * returns the position of the camera
	 * @return
	 */
	public Vector2 getPosition()
	{
		return position;
	}
	
	/**
	 * zooms in the camera
	 * @param amount
	 */
	public void addZoom(float amount)
	{
		setZoom(zoom + amount);
	}
	
	/**
	 * sets camera zoom
	 * @param zoom
	 */
	public void setZoom(float zoom)
	{
		this.zoom = MathUtils.clamp(zoom, MAX_ZOOM_IN, MAX_ZOOM_OUT);
	}
	
	/**
	 * returns the camera zoom
	 * @return
	 */
	public float getZoom()
	{
		return zoom;
	}
	
	/**
	 * set the target of the camera
	 * @param target
	 */
	public void setTarget(AbstractGameObject target)
	{
		this.target = target;
	}
	
	/**
	 * returns the target of the camera
	 * @return
	 */
	public AbstractGameObject getTarget()
	{
		return target;
	}
	
	/**
	 * checks to see if the camera has a target
	 * @return
	 */
	public boolean hasTarget()
	{
		return target != null;
	}
	
	/**
	 * checks to see if the camera has a target
	 * @param target
	 * @return
	 */
	public boolean hasTarget(AbstractGameObject target)
	{
		return hasTarget() && this.target.equals(target);
	}
	
	/**
	 * apply the updates to the camera
	 * @param camera
	 */
	public void applyTo(OrthographicCamera camera)
	{
		camera.position.x = position.x;
		camera.position.y = position.y;
		camera.zoom = zoom;
		camera.update();
	}
}