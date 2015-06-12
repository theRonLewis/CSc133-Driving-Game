package a3;

import java.awt.Dimension;
import java.text.DecimalFormat;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 * VIEW CLASS
 * 
 * This class represents the Score View of the game
 * 
 * It is meant to be implemented as a panel in
 * a frame, and should be given to an observable
 * GameWorld.
 * 
 * It displays various game state data
 * 
 */
@SuppressWarnings("serial")
public class ScoreView extends JPanel implements IObserver {

	private JLabel time;
	private JLabel lives;
	private JLabel highestPylon;
	private JLabel fuelLeft;
	private JLabel playerDamage;
	private JLabel sound;
	private DecimalFormat df = new DecimalFormat();
	
	ScoreView(){
		
		// prep decimal format
		df.applyPattern("###");
		
		// add space
		add(Box.createRigidArea(new Dimension(25,0)));
		
		// add the labels with dummy data for the panel	
		time = new JLabel("Time: ##");
		add(time);
		
		// add space
		add(Box.createRigidArea(new Dimension(25,0)));
		
		lives = new JLabel("Lives Left: ##");
		add(lives);
		
		// add space
		add(Box.createRigidArea(new Dimension(25,0)));
		
		highestPylon = new JLabel("Highest Player Pylon: ##");
		add(highestPylon);
		
		// add space
		add(Box.createRigidArea(new Dimension(25,0)));
		
		fuelLeft = new JLabel("Player Fuel Remaining: ##");
		add(fuelLeft);
		
		// add space
		add(Box.createRigidArea(new Dimension(25,0)));
		
		playerDamage = new JLabel("Player Damage Level: ##");
		add(playerDamage);
		
		// add space
		add(Box.createRigidArea(new Dimension(25,0)));
		
		sound = new JLabel("Sound: XX");
		add(sound);
		
		// add space
		add(Box.createRigidArea(new Dimension(25,0)));

		// make it visible
		this.setVisible(true);
	}

	public void update(IObservable o) {
		// update all the labels from the game world we received
		time.setText("Time: " +  (int)(((IGameWorld) o).getClockTime()) );
		lives.setText("Lives Left: " + ((IGameWorld) o).getLivesLeft());
		highestPylon.setText("Highest Player Pylon: " + ((IGameWorld) o).getHighestPylon());
		fuelLeft.setText("Player Fuel Remaining: " + (int)( ((IGameWorld) o).getPlayerFuel()) );
		playerDamage.setText("Player Damage Level: " + ((IGameWorld) o).getPlayerDamage());
		if(((IGameWorld) o).soundIsOn())
			sound.setText("Sound: ON");
		else sound.setText("Sound: OFF");
		
		// make it visible
		this.setVisible(true);
	}
}
