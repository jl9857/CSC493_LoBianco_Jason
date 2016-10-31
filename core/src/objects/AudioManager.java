package objects;

import screen.GamePreferences;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * The AudioManager class handles all of the music an sounds for the game.
 * @author Jason LoBianco
 */
public class AudioManager 
{  
	public static final AudioManager instance = new AudioManager();
	private Music playingMusic;
	
	// singleton: prevent instantiation from other classes  
	private AudioManager() 
	{
		
	}
	
	/**
	 * plays the sound.
	 * @param sound
	 */
	public void play(Sound sound) 
	{    
		play(sound, 1);  
	}
	
	/**
	 * plays the sound at a set volume
	 * @param sound
	 * @param volume
	 */
	public void play(Sound sound, float volume)
	{    
		play(sound, volume, 1);
	}
	
	/**
	 * plays the sound at a set volume and pitch
	 * @param sound
	 * @param volume
	 * @param pitch
	 */
	public void play(Sound sound, float volume, float pitch)
	{    
		play(sound, volume, pitch, 0);  
	}
	
	/**
	 * plays the sound at a set volume, pitch, and pan
	 * @param sound
	 * @param volume
	 * @param pitch
	 * @param pan
	 */
	public void play(Sound sound, float volume, float pitch,  float pan)
	{    
		if (!GamePreferences.instance.sound) return;
		sound.play(GamePreferences.instance.volSound * volume,   pitch, pan);  
	}
	
	/**
	 * plays the music
	 * @param music
	 */
	public void play(Music music) 
	{  
		stopMusic();  
		playingMusic = music;  
		if (GamePreferences.instance.music)
		{    
			music.setLooping(true);    
			music.setVolume(GamePreferences.instance.volMusic);    
			music.play();  
		} 
	}
	
	/**
	 * stops the music
	 */
	public void stopMusic() 
	{    
		if (playingMusic != null) playingMusic.stop();  
	}
	
	/**
	 * updates the music settings based on options settings
	 */
	public void onSettingsUpdated() 
	{  
		if (playingMusic == null) return;
		playingMusic.setVolume(GamePreferences.instance.volMusic);
		if (GamePreferences.instance.music)
		{    
			if (!playingMusic.isPlaying()) playingMusic.play();
		} 
		else 
		{    
			playingMusic.pause();  
		} 
	}
}