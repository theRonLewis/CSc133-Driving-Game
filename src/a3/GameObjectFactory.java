package a3;

import java.awt.Color;

import javax.swing.JOptionPane;

// CURRENT MAP SIZE SEEMS TO BE ROUNDD ABOUTS....
// Width:  865
// Height: 710

public class GameObjectFactory {

	private GameWorldProxy myGameWorld;
	
	private PlayerCar playerOne;
	private NonPlayerCar npc1, npc2, npc3;
	
	// currently only support 4 pylon world
	private int initPylonCounter = 0;
	
	// count npcs to know where to place them
	private int npcCounter = 0;
	
	// set a uniform color for pylons
	private Color PYLON_COLOR = new Color(64, 64, 64);
	
	// set a uniform color for npcs
	private Color NPC_COLOR = Color.green;
	
	public GameObjectFactory(GameWorldProxy myGW){
		myGameWorld = myGW;
	}
	
	public GameObject getGameObject(String objType){
		if(objType == null){
			return null;
		}
		if(objType.equalsIgnoreCase("PLAYER")){
			playerOne = new PlayerCar(new FloatPoint(200, 200), Color.blue, 0, 0);
			return playerOne;
		} else if(objType.equalsIgnoreCase("NPC")){
			npcCounter++;
			switch(npcCounter){
			case 1: 
				npc1 = new NonPlayerCar(new FloatPoint(50, 200), NPC_COLOR, 0, 10);
				npc1.setStrategy(new DestructionDerbyStrategy(npc1, playerOne));
				return npc1;
			case 2: 
				npc2 = new NonPlayerCar(new FloatPoint(125, 200), NPC_COLOR, 0, 10);
				npc2.setStrategy(new RaceStrategy(npc2, myGameWorld));
				return npc2;
			case 3: 
				npc3 = new NonPlayerCar(new FloatPoint(275, 200), NPC_COLOR, 0, 10);
				npc3.setStrategy(new DestructionDerbyStrategy(npc3, playerOne));
				return npc3;
			}
			return null;
		} else if(objType.equalsIgnoreCase("PYLON")){
			initPylonCounter++;
			switch(initPylonCounter){
			case 1: 
				return new Pylon(new FloatPoint(200, 200), PYLON_COLOR);
			case 2: 
				return new Pylon(new FloatPoint(200, 450), PYLON_COLOR);
			case 3: 
				return new Pylon(new FloatPoint(500, 580), PYLON_COLOR);
			case 4: 
				return new Pylon(new FloatPoint(650, 450), PYLON_COLOR);
			}
		} else if(objType.equalsIgnoreCase("BIRD")){ 
			return new Bird();
		} else if(objType.equalsIgnoreCase("OIL")){
			return new OilSlick();
		} else if(objType.equalsIgnoreCase("FUEL")){
			return new FuelCan();
		}
		return null;
	}
	
	// support for dynamically adding objects at a given point from a click
	public GameObject getGameObject(String objType, FloatPoint loc){
		
		// input stuffs for dynamically creating objects
		String inputString;
		int inputNum;
		
		if(objType.equalsIgnoreCase("PYLON")){
			// get the new pylon sequence number
			inputString = JOptionPane.showInputDialog("Please enter the new pylon sequnce number\n"
					                                + "There is no limitation on the sequnce number you enter,\n"
					                                + "And it is possible to break the game with invalid sequence\n"
					                                + "(EG: Pylons numbered 1, 2, 3, 5, and missing a Pylon#4)");
			
			// check if string was null from cancel
			if(inputString == null)
				return null;
			
			inputNum = Integer.parseInt(inputString);
			
			
			// create and return pylon
			return new Pylon(loc, new Color(64, 64, 64), inputNum );
			
		}else if(objType.equalsIgnoreCase("FUEL")){
			// get the new pylon sequence number
			inputString = JOptionPane.showInputDialog("Please enter the size of the new fuel can.\n"
					                                + "Randomly generated cans have size 3-6 inclusive.\n"
					                                + "There are no limitations on what you may enter.");
			
			// check is string was null from cancel
			if(inputString == null)
				return null;
			
			inputNum = Integer.parseInt(inputString);
			
			// create and return the fuel can
			return new FuelCan(loc, inputNum);
			
		}else if(objType.equalsIgnoreCase("OIL")){
			// create and return the fuel can
			return new OilSlick(loc);
		}
		
		
		return null;
	}
	
	public PlayerCar getPlayer(){
		return playerOne;
	}
	
	public int getPylonCount(){
		return Pylon.getCount();
	}
}