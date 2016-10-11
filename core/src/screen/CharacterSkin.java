package screen;

import com.badlogic.gdx.graphics.Color;

/**
 * The CharacterSkin class is responsible for holding all of the
 * selectable character skins.
 * @author Jason LoBianco
 */
public enum CharacterSkin 
{
	WHITE("White", 1.0f, 1.0f, 1.0f),
	GRAY("Gray", 0.7f, 0.7f, 0.7f),
	BROWN("Brown", 0.7f, 0.5f, 0.3f);
	
	private String name;
	private Color color = new Color();
	
	private CharacterSkin(String name, float r, float g, float b)
	{
		this.name = name;
		color.set(r, g, b, 1.0f);
	}
	
	/**
	 * returns the name of the skin
	 */
	@Override
	public String toString()
	{
		return name;
	}
	
	/**
	 * returns the color of the skin
	 * @return
	 */
	public Color getColor()
	{
		return color;
	}
}