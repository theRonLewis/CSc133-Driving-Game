package a4;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class SoundLibrary{
	
	private static SoundLibrary theSoundLibrary;
	
	// clips
	private AudioClip myCarCarClip;
	private AudioClip myCarBirdClip;
	private AudioClip myCarExplosionClip;
	private AudioClip myCarFuelClip;
	private AudioClip myCarPylonClip;
	private AudioClip myGameOverClip;
	private AudioClip myPlayerWinsClip;
	
	// music
	private AudioClip myBGMLoop;
	
	private SoundLibrary(){
			
		// initialize the sound directory, and strings
		String soundDir = "." + File.separator + "sounds" + File.separator;
		String fileName, filePath;
		
		// create the file for stuff
		File file;
		
		// setup the car-car collision sound
		fileName = "carCarCollision.wav";
		filePath = soundDir + fileName;
		try{
			file = new File(filePath);
			if(file.exists()){
				myCarCarClip = Applet.newAudioClip(new File(filePath).toURI().toURL() );
			}else{
				throw new RuntimeException("Sound: File not found: " + fileName);
			}
		} catch(MalformedURLException e){
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
		
		
		// setup the car-car collision sound
		fileName = "carBirdCollision.wav";
		filePath = soundDir + fileName;
		try{
			file = new File(filePath);
			if(file.exists()){
				myCarBirdClip = Applet.newAudioClip(new File(filePath).toURI().toURL() );
			}else{
				throw new RuntimeException("Sound: File not found: " + fileName);
			}
		} catch(MalformedURLException e){
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
		
		// setup the car explosion sound
		fileName = "carExplosion.wav";
		filePath = soundDir + fileName;
		try{
			file = new File(filePath);
			if(file.exists()){
				myCarExplosionClip = Applet.newAudioClip(new File(filePath).toURI().toURL() );
			}else{
				throw new RuntimeException("Sound: File not found: " + fileName);
			}
		} catch(MalformedURLException e){
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
		
		// setup the car gets fuel sound
		fileName = "CarGetsFuel.wav";
		filePath = soundDir + fileName;
		try{
			file = new File(filePath);
			if(file.exists()){
				myCarFuelClip = Applet.newAudioClip(new File(filePath).toURI().toURL() );
			}else{
				throw new RuntimeException("Sound: File not found: " + fileName);
			}
		} catch(MalformedURLException e){
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
		
		// setup the car gets next pylon sound
		fileName = "carHitsPylon.wav";
		filePath = soundDir + fileName;
		try{
			file = new File(filePath);
			if(file.exists()){
				myCarPylonClip = Applet.newAudioClip(new File(filePath).toURI().toURL() );
			}else{
				throw new RuntimeException("Sound: File not found: " + fileName);
			}
		} catch(MalformedURLException e){
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
		
		// setup the player winning the game sound
		fileName = "playerWins.wav";
		filePath = soundDir + fileName;
		try{
			file = new File(filePath);
			if(file.exists()){
				myPlayerWinsClip = Applet.newAudioClip(new File(filePath).toURI().toURL() );
			}else{
				throw new RuntimeException("Sound: File not found: " + fileName);
			}
		} catch(MalformedURLException e){
			throw new RuntimeException("Sound: malformed URL: " + e);
		}

		// setup the game over sound
		fileName = "gameOver.wav";
		filePath = soundDir + fileName;
		try{
			file = new File(filePath);
			if(file.exists()){
				myGameOverClip = Applet.newAudioClip(new File(filePath).toURI().toURL() );
			}else{
				throw new RuntimeException("Sound: File not found: " + fileName);
			}
		} catch(MalformedURLException e){
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
		
		// setup the bgm loop
		fileName = "bgmLoop.wav";
		filePath = soundDir + fileName;
		try{
			file = new File(filePath);
			if(file.exists()){
				myBGMLoop = Applet.newAudioClip(new File(filePath).toURI().toURL() );
			}else{
				throw new RuntimeException("Sound: File not found: " + fileName);
			}
		} catch(MalformedURLException e){
			throw new RuntimeException("Sound: malformed URL: " + e);
		}
	}
	
	public static SoundLibrary getInstance(){
		if(theSoundLibrary == null)
			theSoundLibrary = new SoundLibrary();
		return theSoundLibrary;
	}
	
	public void playCarCarCollisionSound(){
		myCarCarClip.play();
	}
	
	public void playCarBirdCollisionSound(){
		myCarBirdClip.play();
	}
	
	public void playCarExplosionSound(){
		myCarExplosionClip.play();
	}
	
	public void playCarFuelSound(){
		myCarFuelClip.play();
	}
	
	public void playCarPylonSound(){
		myCarPylonClip.play();
	}
	
	public void playPlayerWinsSound(){
		myPlayerWinsClip.play();
	}
	
	public void playGameOverSound(){
		myGameOverClip.play();
	}
	
	public void playBGM(){
		myBGMLoop.loop();
	}
	
	public void stopBGM(){
		myBGMLoop.stop();
	}
}