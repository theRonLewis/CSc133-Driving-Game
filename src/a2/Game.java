package a2;

import java.io.IOException;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.border.*;


// this class is used to represent a currently running game
// it instantiates a game world and provides an interface
// for the player to interact with it. Currently only has console
// output for testing, and no 2d graphical representation (for now...)

// have to suppress this when not adding a certain serial var 
@SuppressWarnings("serial")
public class Game extends JFrame {
	
	// create a game world
	private GameWorld gw;
	
	// create the necessary views
	private ScoreView myScoreView;
	private MapView myMapView;
	
	// scanner for input
	private Scanner in;
	
	/* Old game class from A1
	
	// construct a console Game for testing
	public Game(){
		
		// create new game world and initialize it
		gw = new GameWorld();
		gw.initLayout();
		
		// scanner for input
		in = new Scanner(System.in);
		
		// run the consoleTest() method
		// currently console output only
		consoleTest();
		
		// close the scanner before exiting
		in.close();
	}
	
	*/
	
	public Game(){
		
		// initialize the command targets if needed
		//AccelerateCommand.setTarget(gw);
		
		
		// FRAME STUFF
		// make title
		setTitle("Ron's A2 - 2D Driving Sim");
		
		// where to load
		setLocation(0, 300);
		
		// set size of the window
		setSize(1000, 800);
		
		// close on X-button icon click
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		// DEFINE KEYBOARD SHORTCUT KEYS
		
		// arrow keys, keystrokes
		KeyStroke upArrowKey = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
		KeyStroke downArrowKey = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
		KeyStroke leftArrowKey = KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0);
		KeyStroke rightArrowKey = KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0);
		
		// letter keys, keystrokes
		KeyStroke pKey = KeyStroke.getKeyStroke(KeyEvent.VK_P, 0);
		KeyStroke oKey = KeyStroke.getKeyStroke(KeyEvent.VK_O, 0);
		KeyStroke tKey = KeyStroke.getKeyStroke(KeyEvent.VK_T, 0);
		KeyStroke qKey = KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0);
		
		// space bar, keystroke - STILL TO BE IMPLEMENTED
		KeyStroke spaceBarKey = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
		
		// MENU BAR STUFF
		JMenuBar bar = createMenuBar();
		this.setJMenuBar(bar);
		this.setVisible(true);
		
		// TOP PANEL FOR SCORES AND STATUS
		myScoreView = new ScoreView();
		add(myScoreView, BorderLayout.NORTH);
		
		// LEFT COMMAND PANEL
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(" Commands"));
		leftPanel.setLayout(new GridLayout(10, 1));
		this.add(leftPanel, BorderLayout.WEST);
		
		JButton collideNPCButton = new JButton("Collide With NPC");
		collideNPCButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		collideNPCButton.setAction(CollideWithNPCCommand.getInstance());
		leftPanel.add(collideNPCButton);
		
		JButton collidePylonButton = new JButton("Collide With Pylon");
		collidePylonButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		collidePylonButton.setAction(CollideWithPylonCommand.getInstance());
		leftPanel.add(collidePylonButton);
		
		JButton collideBirdButton = new JButton("Collide With Bird");
		collideBirdButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		collideBirdButton.setAction(CollideWithBirdCommand.getInstance());
		leftPanel.add(collideBirdButton);
		
		JButton getFuelCanButton = new JButton("Pick Up Fuel Can");
		getFuelCanButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		getFuelCanButton.setAction(PickUpFuelCommand.getInstance());
		leftPanel.add(getFuelCanButton);
		
		JButton enterOilButton = new JButton("Entered Oil Slick");
		enterOilButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		enterOilButton.setAction(EnterOilSlickCommand.getInstance());
		leftPanel.add(enterOilButton);
		
		JButton exitOilButton = new JButton("Exited Oil Slick");
		exitOilButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		exitOilButton.setAction(ExitOilSlickCommand.getInstance());
		leftPanel.add(exitOilButton);
		
		JButton switchStratButton = new JButton("Switch Strategy");
		switchStratButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		switchStratButton.setAction(ChangeNPCStrategyCommand.getInstance());
		leftPanel.add(switchStratButton);
		
		JButton tickButton = new JButton("Tick");
		tickButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		tickButton.setAction(TickCommand.getInstance());
		leftPanel.add(tickButton);
		
		JButton quitButton = new JButton("Quit");
		quitButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		quitButton.setAction(QuitCommand.getInstance());
		leftPanel.add(quitButton);
		
		// RIGHT/CENTER MAP PANEL
		myMapView = new MapView();
		this.add(myMapView, BorderLayout.CENTER);
		
		// map keystrokes to commands
		
			// get the focus in this window
		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = myMapView.getInputMap(mapName);
		
		// map keys to actions
		imap.put(upArrowKey, "Accelerate");
		imap.put(downArrowKey, "Brake");
		imap.put(leftArrowKey, "LeftTurn");
		imap.put(rightArrowKey, "RightTurn");
		imap.put(pKey, "CollidePylon");
		imap.put(oKey, "AddOilSlick");
		imap.put(tKey, "TickClock");
		imap.put(spaceBarKey, "ChangeNPCStrats");
		imap.put(qKey, "Quit");
		
		// make the action map
		ActionMap amap = myMapView.getActionMap();
		
		// map actions to commands
		amap.put("Accelerate", AccelerateCommand.getInstance());
		amap.put("Brake", BrakeCommand.getInstance());
		amap.put("LeftTurn", LeftTurnCommand.getInstance());
		amap.put("RightTurn", RightTurnCommand.getInstance());
		amap.put("CollidePylon", CollideWithPylonCommand.getInstance());
		amap.put("AddOilSlick", AddOilSlickCommand.getInstance());
		amap.put("TickClock", TickCommand.getInstance());
		amap.put("ChangeNPCStrats", ChangeNPCStrategyCommand.getInstance());
		amap.put("Quit", QuitCommand.getInstance());
		
		//have this window request focus
		this.requestFocus();
		//requestFocus();
		// MAYBE this needs to be done at the end???
		
		// GAME WORLD & STUFF
		
		// create new game world
		gw = new GameWorld();
		
		// register the views so they update at initialization
		gw.addObserver(myScoreView);
		gw.addObserver(myMapView);
		
		// create the initial world
		gw.initLayout();
		
		// now that game world is initialized, make it the target where needed
		AccelerateCommand.setTarget(gw);
		BrakeCommand.setTarget(gw);
		AddOilSlickCommand.setTarget(gw);
		CollideWithBirdCommand.setTarget(gw);
		CollideWithNPCCommand.setTarget(gw);
		CollideWithPylonCommand.setTarget(gw);
		EnterOilSlickCommand.setTarget(gw);
		ExitOilSlickCommand.setTarget(gw);
		LeftTurnCommand.setTarget(gw);
		NewColorsCommand.setTarget(gw);		
		PickUpFuelCommand.setTarget(gw);
		RightTurnCommand.setTarget(gw);
		TickCommand.setTarget(gw);
		ToggleSoundCommand.setTarget(gw);
		ChangeNPCStrategyCommand.setTarget(gw);
		
		// SHOW THE FRAME NOW
		setVisible(true);
		
		
	}
	
	private JMenuBar createMenuBar(){
		
		// init the bar to return
		JMenuBar bar = new JMenuBar();
		
		// create the file menu
		JMenu fileMenu = new JMenu ("File");
		
			// file -> new
			JMenuItem mItem = new JMenuItem("New");
			fileMenu.add(mItem);
			
			// file -> sound toggle
			JCheckBoxMenuItem soundMenuItem = new JCheckBoxMenuItem("Sound");
			soundMenuItem.setAction(ToggleSoundCommand.getInstance());
			soundMenuItem.setState(true);
			fileMenu.add(soundMenuItem);
		
			// file -> about
			mItem = new JMenuItem("About");
			mItem.setAction(AboutCommand.getInstance());
			fileMenu.add(mItem);
			
			// file -> Quit
			mItem = new JMenuItem("Quit");
			mItem.setAction(QuitCommand.getInstance());
			fileMenu.add(mItem);
			
			
			// add the file menu to the bar
			bar.add(fileMenu);
			
		// create the commands menu
		JMenu commandMenu = new JMenu("Commands");
			
			// C -> add oil slick 
			mItem = new JMenuItem("Add oil slick");
			mItem.setAction(AddOilSlickCommand.getInstance());
			commandMenu.add(mItem);
			
			// C -> pick up fuel can
			mItem = new JMenuItem("Pick up fuel can");
			mItem.setAction(PickUpFuelCommand.getInstance());
			commandMenu.add(mItem);
			
			// C -> new colors on objects
			mItem = new JMenuItem("New colors (if OK)");
			mItem.setAction(NewColorsCommand.getInstance());
			commandMenu.add(mItem);
			
			// add the menu to the bar
			bar.add(commandMenu);
			
		return bar;
	}
	
	// Used for testing the game
	// unused unless manually changed to implement
	@SuppressWarnings("unused")
	private void consoleTest(){
		
		// make string for command processing
		// and printing game details
		String 	command,
				gameDetails;
		
		// placeholder for attempt to hit pylon
		int hitPylon = 0;
		
		// boolean for quit confirmation
		boolean quit = false;
		

		// print initial map to user
		System.out.println("\n printing initial map for console play...\n");
		gw.printTextMap();
		System.out.println();
		
		// menu, only way to exit is to choose command q
		do{
			// get command from user
			command = getCommand();
			
			// now process that command
			
			// check for pylon command
			// the only multi char command that 
			// getCommand() will return is a valid pylon request
			if(command.length() > 1){
				
				// entry is 2 char, extract single digit pylon to hit
				if(command.length() == 2){
					hitPylon = command.charAt(1) - '0';
				}
				
				// entry is 3 char, extract double digit pylon to hit
				if(command.length() == 3){
					hitPylon = ((command.charAt(1) - '0') * 10);
					hitPylon += (command.charAt(2) - '0');
				}
				
				// simulate hiting the given pylon
				System.out.println(" simulating player reaching pylon # " + hitPylon + "\n");
				gw.playerHitPylon(hitPylon);
				
			}

			
			// all other possible commands
			switch(command.charAt(0)){
			
				//case for already processed pylon
				case 'p':
					break;
					
				// case for invalid pylon command entry
				case 'i':
					System.out.println("\n Invalid use of p command. Please enter pX or pXX where \n X/XX is single or double digit number...\n");
					break;
			
				// case for accelerate player
				case 'a':
					gw.acceleratePlayer();
					System.out.println("\n attempted to accelerate the player...\n");
					break;
				
				// case for brake player
				case 'b':
					gw.brakePlayer();
					System.out.println("\n attempted to brake the player...\n ");
					break;
				
				// case for turn player left
				case 'l':
					gw.turnPlayerLeft();
					System.out.println("\n attempted to turn player left...\n");
					break;
				
				// case for turn player right
				case 'r':
					gw.turnPlayerRight();
					System.out.println("\n attempted to turn player right...\n");
					break;
				
				// case for add oil slick
				case 'o':
					gw.addOilSlick();
					System.out.println("\n added a new oil slick to the world...\n");
					break;
				
				// case for player colliding with another car
				case 'c':
					gw.playerCarCollision();
					System.out.println("\n simulated collision between player and another car...\n");
					break;
				
				// case for picking up first fuel can in world
				// and adding a new one
				case 'f':
					gw.playerGetsFuelCan();
					System.out.println("\n simulated player car picking up first fuel can in game world...\n"
									   + " also added a new random fuel can back to the world...\n");
					break;
				
				// case for player colliding with a bird
				case 'g':
					gw.playerBirdCollision();
					System.out.println("\n simulated collision between player and a bird...\n");
					break;
				
				// case for player entering oil slick
				case 'e':
					gw.playerEntersOil();
					System.out.println("\n player now in oil slick...\n" + 
							             " cannot accelerate, brake, or change heading based on steering...\n");
					break;
				
				// case for player exiting oil slick
				case 'x':
					gw.playerExitsOil();
					System.out.println("\n player no longer in oil slick...\n" + 
									     " can now accelerate, brake, and change heading based on steering...\n");
					break;
				
				// case for changing/randomizing all game object colors (where allowed)
				case 'n':
					gw.changeObjColors();
					System.out.println("\n just created new random colors for all objects, where allowed...\n");
					break;
				
				// case for advancing game time
				// also moves objects where appropriate
				// should also check for player status and 
				// lose a life or initiate Game Over if needed
				case 't':
					gw.advanceGameClock();
					System.out.println("\n advanced game clock and moved objects where appropriate...\n");
					break;
				
				// case for displaying game state variables
				case 'd':
					gameDetails = gw.getGameState();
					System.out.println("\n displaying game state variable values...\n\n" + gameDetails);
					break;
					
				// case for displaying textual map
				case 'm':
					System.out.println("\n ==> CURRENT \"MAP\"\n");
					gw.printTextMap();
					System.out.println();
					break;
				
				// case for quit, asks for confirmation
				case 'q':	
					quit = quit();
					if(quit)
						return;
					else break;
					
				// print command help to user
				case 'h':
					System.out.println("\n VALID CONSOLE COMMANDS:\n" + 
										 " =======================");
					System.out.println(" a - accelerate player");
					System.out.println(" b - brake player");
					System.out.println(" l - turn player left");
					System.out.println(" r - turn player right");
					System.out.println(" o - add new oil slick to world");
					System.out.println(" c - simulate player collision with other car");
					System.out.println(" f - simulate player picking up first fuel can in world");
					System.out.println(" g - simulate player collision with a bird");
					System.out.println(" e - simulate player entering an oil slick");
					System.out.println(" x - simulate player exiting an oil slick");
					System.out.println(" n - randomize the color of all objects (where appropriate)");
					System.out.println(" t - advance the game clock by 1");
					System.out.println(" d - display gaame state values");
					System.out.println(" m - print textual representation of the game world");
					System.out.println(" y - run a scripted console test");
					System.out.println(" z - print only the details for player car");
					System.out.println(" q - quit\n");
					break;
					
				// run a scripted console test of objects to view
				case 'y':
					System.out.println("\n The following printConsoleTest method was used for testing additions to\n" +
									   " the game and game world prior to the play() function implementing console\n" +
									   " command testing. It does not implement all game features, but it likely will\n" +
									   " with future assignment submissions. \n");
					System.out.println("\n about to run a console test...\n");
					consolePause();
					printConsoleTest();
					break;
					
				// case for print details for car only
				case 'z':
					System.out.println("\n" + gw.getPlayerCar() + "\n");
					break;
					
				// case for invalid entry, offer help
				default:
					System.out.println("\n Invalid command. For help, enter command h \n");
					
			}
		// only way to quit is via command q
		}while(true);
	}
	
	// this user asks for a command and returns a known valid single char command
	// OR a string for known complex commands (pX, pXX)
	// OR a space (" ") for play() to offer help
	private String getCommand(){

		System.out.print(" Input your command to change or view the game world: ");
		
		String input;
		
		// check for null entry w/length 
		do{
			input = in.nextLine();
		} while(input.length() == 0);
		
		// check for special cases first...
		// check for pylon command
		if(input.charAt(0) == 'p'){
			
			// entry is 2 char, return if valid
			if(input.length() == 2){
				if( ('0' <= input.charAt(1) && input.charAt(1) <= '9') ){
						return input;
				}
			}
			
			// entry is 3 char, return if valid w/ numbers
			if(input.length() == 3){
				if( ('0' <= input.charAt(1) && input.charAt(1) <= '9') ){
					if( ('0' <= input.charAt(2) && input.charAt(2) <= '9') )
						return input;
				}
			}
			
			// entry is invalid, return i to print pXX command help
			return "i";
		}
		
		// check for all other cases 
		// (these are listed in detail in play() )
		// comments below for commands that are 
		// not mentioned in the assignment specification
		switch(input){
			case "a": 	return "a";
			
			case "b":	return "b";
			
			case "l":	return "l";
			
			case "r":	return "r";
			
			case "o":	return "o";
			
			case "c":	return "c";
			
			case "f":	return "f";
			
			case "g":	return "g";
			
			case "e":	return "e";
			
			case "x":	return "x";
			
			case "n":	return "n";
			
			case "t":	return "t";
			
			case "d":	return "d";
			
			case "m":	return "m";
			
			case "q":	return "q";
			
			// for displaying help
			case "h":	return "h";
			
			// for running a scripted console test
			case "y":	return "y";
			
			// for printing only the car
			case "z":	return "z";
			
			// have to return something, play() handles validity beyond this
			default: 	return " ";
		}
	}
	
	// uses requested to quit game, this method confirms
	private boolean quit(){
		
		// string for input
		String input;
		
		// first char of input for y/Y/n/N checking
		// initialize to 'a' to suppress warnings 
		char choice = 'a';
		
		// prep isValid for proper looping
		boolean isValid = false;
		
		while(!isValid){
			// prompt user
			System.out.print("\n You chose to quit... Are you sure? (y/n) ");
			
			// check for valid keyboard entry
			do{
				input = in.nextLine();
			} while(input.length() == 0);
			
			// check first char for y/Y/n/N, and set isValid (or skip)
			choice = input.charAt(0);
			if(choice == 'y' || choice == 'Y' || choice == 'n' || choice == 'N')
				isValid = true;
		}
		
		// if user enters y, return
		if (choice == 'y' || choice == 'Y')
			return true;
		
		// otherwise, return false
		return false;
	}
	
	/*	The following printConsoleTest method was used for testing additions to 
	 *	the game and game world prior to the play() function implementing console
	 *	command testing. It does not implement all game features, but it likely will
	 *	with future assignment submissions. 
	 */	
	
	// run a scripted console test
	private void printConsoleTest() {
		
		int i; // for loops
		
		// print initial game map
		System.out.println("\n printing initial map...\n");
		gw.printTextMap();
		
		System.out.println();
		consolePause();
		
		// change colors and print again
		gw.changeObjColors();
		System.out.println("\n just changed all obj colors (where applicable)...\n");
		gw.printTextMap();
		
		System.out.println();
		consolePause();
		
		// incrementally turn and print player car turning left
		System.out.println(" turning car left to max...\n");
		for( i = 1 ; i <= 9 ; i++){
			gw.turnPlayerLeft();
			System.out.println(gw.getPlayerCar()+ "\n");
			consoleSleep(450);
		}
		
		consolePause();
		
		// incrementally turn and print player car turning right
		System.out.println(" turning car right to max...\n");
		for( i = 1 ; i <= 17 ; i++){
			gw.turnPlayerRight();
			System.out.println(gw.getPlayerCar()+ "\n");
			consoleSleep(450);
		}
		
		consolePause();
		
		// accelerate car to max
		System.out.println(" trying to accelerate car past max...\n");
		for( i = 1 ; i <= 17 ; i++){
			gw.acceleratePlayer();
			System.out.println(gw.getPlayerCar()+ "\n");
			consoleSleep(450);
		}
		
		consolePause();
		
		// braking a little
		System.out.println(" trying to brake care below 0...\n");
		for( i = 1 ; i <= 18 ; i++){
			gw.brakePlayer();
			System.out.println(gw.getPlayerCar()+ "\n");
			consoleSleep(450);
		}
		
		consolePause();
		
		// accelerate back up a little
		System.out.println(" accelerating car a bit before taking damage...\n");
		for( i = 1 ; i <= 10 ; i++){
			gw.acceleratePlayer();
			System.out.println(gw.getPlayerCar()+ "\n");
			consoleSleep(450);
		}
		
		consolePause();
		
		// hit 4 birds
		System.out.println(" hitting 4 birds...\n");
		for ( i = 1; i <= 4; i++){
			gw.playerBirdCollision();
			System.out.println(gw.getPlayerCar()+ "\n");
			consoleSleep(450);
		}
		
		consolePause();
		
		// hit 2 cars
		System.out.println(" hitting 2 cars... \n");
		for( i = 1 ; i <= 2 ; i++ ){
			gw.playerCarCollision();
			System.out.println(gw.getPlayerCar()+ "\n");
			consoleSleep(450);
		}
		
		consolePause();
		
		// add 3 new oil slicks
		System.out.println(" adding 3 new oil slicks... \n");
		for( i = 1 ; i <= 3 ; i++ ){
			gw.addOilSlick();
			gw.printTextMap();
			System.out.println();
			consoleSleep(1000);
		}
		
		System.out.println(" all tests concluded...\n");
		System.out.println(" returning to input command...\n");
		consolePause();
			
	}
	
	// methods to aid in console print tests and readability
	
	// pause the console, and prompt user before continuing
	private void consolePause(){
		System.out.println("Press enter to continue...");
		try {
			System.in.read();
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// pause the output for given milliseconds
	// to help with incrementing and decrementing
	// certain variables
	private void consoleSleep(int ms){
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}

