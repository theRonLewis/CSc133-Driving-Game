package a3;

import java.io.IOException;
import java.util.Scanner;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.border.*;

// this class is used to represent a currently running game
// it instantiates a game world and provides an interface
// for the player to interact with it. Currently only has console
// output for testing, and no 2d graphical representation (for now...)

// have to suppress this when not adding a certain serial var 
@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener, MouseListener {
	
	// create a game world
	private GameWorld gw;
	
	// create the necessary views
	private ScoreView myScoreView;
	private MapView myMapView;
	
	// is the game paused? Starts out playing
	private boolean isPaused;
	
	// make buttons, need here for enable/disable?
	private JButton playPauseButton,
					addFuelButton,
					addPylonButton,
					deleteButton,
					quitButton;
	
	// make commands
	private AccelerateCommand myAccelerateCommand = AccelerateCommand.getInstance();
	private BrakeCommand myBrakeCommand = BrakeCommand.getInstance();
	private AddOilSlickCommand myAddOilSlickCommand = AddOilSlickCommand.getInstance();
	private CarHitsBirdCommand myCollideWithBirdCommand = CarHitsBirdCommand.getInstance();
	private CarHitsCarCommand myCollideWithNPCCommand = CarHitsCarCommand.getInstance();
	private CarHitsPylonCommand myCollideWithPylonCommand = CarHitsPylonCommand.getInstance();
	private CarGetsFuelCommand myPickUpFuelCommand = CarGetsFuelCommand.getInstance();
	private PlayerEntersOilSlickCommand myEnterOilSlickCommand = PlayerEntersOilSlickCommand.getInstance();
	private PlayerExitsOilSlickCommand myExitOilSlickCommand = PlayerExitsOilSlickCommand.getInstance();
	private LeftTurnCommand myLeftTurnCommand = LeftTurnCommand.getInstance();
	private NewColorsCommand myNewColorsCommand = NewColorsCommand.getInstance();	
	private RightTurnCommand myRightTurnCommand = RightTurnCommand.getInstance();
	private ChangeNPCStrategyCommand myChangeNPCStrategyCommand = ChangeNPCStrategyCommand.getInstance();
	private DeleteCommand myDeleteCommand = DeleteCommand.getInstance();
	private AddPylonCommand myAddPylonCommand = AddPylonCommand.getInstance();
	private AddFuelCanCommand myAddFuelCanCommand = AddFuelCanCommand.getInstance();
	
	// timer will go off every 20 ms
	// this is not hard coded in the rest of the game,
	// the game world will handle changing based on this
	// timer value alone
	private Timer myTimer = new Timer(20, this);
	private TickCommand myTickCommand = TickCommand.getInstance();
	
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
		
		// set game to NOT paused
		isPaused = false;
		
		// FRAME STUFF
		// make title
		setTitle("Ron's A3 - 2D Driving Sim, w/Graphics");
		
		// where to load
		setLocation(100, 100);
		
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
			KeyStroke qKey = KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0);
			
			// delete key
			KeyStroke delKey = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
		
		// MENU BAR STUFF
		JMenuBar bar = createMenuBar();
		this.setJMenuBar(bar);
		this.setVisible(true);
		
		// LEFT COMMAND PANEL
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new TitledBorder(" Commands"));
		leftPanel.setLayout(new GridLayout(10, 1));
		this.add(leftPanel, BorderLayout.WEST);
		
		// add the add fuel can button
		addFuelButton = new JButton("Add Fuel Can");
		addFuelButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		addFuelButton.setAction(myAddFuelCanCommand);
		leftPanel.add(addFuelButton);
		
		// add the add pylon button
		addPylonButton = new JButton("Add Pylon");
		addPylonButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		addPylonButton.setAction(myAddPylonCommand);
		leftPanel.add(addPylonButton);
		
		// add the delete button
		deleteButton = new JButton("Delete");
		deleteButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		deleteButton.setAction(myDeleteCommand);
		leftPanel.add(deleteButton);
		
		// add the play/pause button
		playPauseButton = new JButton("PlayPause");
		playPauseButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		playPauseButton.setAction(PlayPauseCommand.getInstance());
		playPauseButton.setText("Pause");
		leftPanel.add(playPauseButton);
		
		// add the quit button
		quitButton = new JButton("Quit");
		quitButton.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "none");
		quitButton.setAction(QuitCommand.getInstance());
		leftPanel.add(quitButton);
		
		// GAME WORLD & STUFF
		
		// create new game world, and initialize
		gw = new GameWorld();
		gw.initLayout();
		
		// TOP PANEL FOR SCORES AND STATUS
		myScoreView = new ScoreView();
		add(myScoreView, BorderLayout.NORTH);
		
		// need map initialized for inputMap
		myMapView = new MapView(gw.getProxy());
		add(myMapView, BorderLayout.CENTER);
		myMapView.addMouseListener(this);
		
		// register the views so they update at initialization
		gw.addObserver(myScoreView);
		gw.addObserver(myMapView);
		
		

		// map keystrokes to commands

		int mapName = JComponent.WHEN_IN_FOCUSED_WINDOW;
		InputMap imap = myMapView.getInputMap(mapName);
		
		// map keys to actions
		imap.put(upArrowKey, "Accelerate");
		imap.put(downArrowKey, "Brake");
		imap.put(leftArrowKey, "LeftTurn");
		imap.put(rightArrowKey, "RightTurn");
		imap.put(pKey, "CollidePylon");
		imap.put(oKey, "AddOilSlick");
		imap.put(qKey, "Quit");
		imap.put(delKey, "Delete");
		
		// make the action map
		ActionMap amap = myMapView.getActionMap();
		
		// map actions to commands
		amap.put("Accelerate", AccelerateCommand.getInstance());
		amap.put("Brake", BrakeCommand.getInstance());
		amap.put("LeftTurn", LeftTurnCommand.getInstance());
		amap.put("RightTurn", RightTurnCommand.getInstance());
		amap.put("CollidePylon", CarHitsPylonCommand.getInstance());
		amap.put("AddOilSlick", AddOilSlickCommand.getInstance());
		amap.put("Quit", QuitCommand.getInstance());
		amap.put("Delete", DeleteCommand.getInstance());
		
		//have this window request focus
		this.requestFocus();
		
		// now that game world is initialized, make it the target where needed
		AccelerateCommand.setTarget(gw);
		BrakeCommand.setTarget(gw);
		AddOilSlickCommand.setTarget(gw);
		AddPylonCommand.setTarget(gw);
		AddFuelCanCommand.setTarget(gw);
		CarHitsBirdCommand.setTarget(gw);
		CarHitsCarCommand.setTarget(gw);
		CarHitsPylonCommand.setTarget(gw);
		PlayerEntersOilSlickCommand.setTarget(gw);
		PlayerExitsOilSlickCommand.setTarget(gw);
		LeftTurnCommand.setTarget(gw);
		NewColorsCommand.setTarget(gw);		
		CarGetsFuelCommand.setTarget(gw);
		RightTurnCommand.setTarget(gw);
		ToggleSoundCommand.setTargets(gw, this);
		ChangeNPCStrategyCommand.setTarget(gw);
		DeleteCommand.setTarget(gw);
		PlayPauseCommand.setTarget(this);
		
		// setup and initialize timer
		TickCommand.setTarget(gw);
		TickCommand.setInterval( myTimer.getDelay() );
		
		
		
		// SHOW THE FRAME NOW
		setVisible(true);
		
		// START THE TIMER/GAME, and enable/disable
		// actions as needed
		resumeGame();

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
	// Cannot properly simulate collisions unless modified to instantiate
	// objects creating collisions
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
		//		gw.playerHitPylon();
				
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
			//		gw.carCarCollision();
					System.out.println("\n simulated collision between player and another car...\n");
					break;
				
				// case for picking up first fuel can in world
				// and adding a new one
				case 'f':
			//		gw.playerGetsFuelCan();
					System.out.println("\n simulated player car picking up first fuel can in game world...\n"
									   + " also added a new random fuel can back to the world...\n");
					break;
				
				// case for player colliding with a bird
				case 'g':
		//			gw.playerBirdCollision();
					System.out.println("\n simulated collision between player and a bird...\n");
					break;
				
				// case for player entering oil slick
				case 'e':
		//			gw.playerEntersOil();
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
					gw.advanceGameClock( myTimer.getDelay() );
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
	
	// is the game paused
	public boolean isPaused(){
		return isPaused;
	}
	
	// is the game running
	public boolean isRunning(){
		return !isPaused;
	}
	
	// resume the game, if paused
	public void resumeGame(){
		playPauseButton.setText("Pause");
		
		// start bgm again
		if(gw.soundIsOn())
			gw.playBGM();
		
		// enabled commands
		myAccelerateCommand.setEnabled(true);
		myBrakeCommand.setEnabled(true);
		myCollideWithBirdCommand.setEnabled(true);
		myCollideWithNPCCommand.setEnabled(true);
		myCollideWithPylonCommand.setEnabled(true);
		myEnterOilSlickCommand.setEnabled(true);
		myExitOilSlickCommand.setEnabled(true);
		myLeftTurnCommand.setEnabled(true);	
		myRightTurnCommand.setEnabled(true);
		myChangeNPCStrategyCommand.setEnabled(true);
		myPickUpFuelCommand.setEnabled(true);
		
		// disabled commands
		myDeleteCommand.setEnabled(false);
		myNewColorsCommand.setEnabled(false);
		myAddOilSlickCommand.setEnabled(false);
		myAddPylonCommand.setEnabled(false);
		myAddFuelCanCommand.setEnabled(false);
		
		// clear the add item flags incase they were set
		gw.setNewFuelCanFlag(false);
		gw.setNewPylonFlag(false);
		
		// start the timer, set game state
		myTimer.start();
		isPaused = false;
	}
	
	// pause the game, if running
	public void pauseGame(){
		myTimer.stop();
		isPaused = true;
		playPauseButton.setText("Play");
		
		// stop bgm
		gw.stopBGM();

		// disabled commands
		myAccelerateCommand.setEnabled(false);
		myBrakeCommand.setEnabled(false);
		myCollideWithBirdCommand.setEnabled(false);
		myCollideWithNPCCommand.setEnabled(false);
		myCollideWithPylonCommand.setEnabled(false);
		myEnterOilSlickCommand.setEnabled(false);
		myExitOilSlickCommand.setEnabled(false);
		myLeftTurnCommand.setEnabled(false);	
		myRightTurnCommand.setEnabled(false);
		myChangeNPCStrategyCommand.setEnabled(false);
		myPickUpFuelCommand.setEnabled(false);
		
		// enabled commands
		myDeleteCommand.setEnabled(true);
		myNewColorsCommand.setEnabled(true);
		myAddOilSlickCommand.setEnabled(true);
		myAddPylonCommand.setEnabled(true);
		myAddFuelCanCommand.setEnabled(true);
	}
	
	// uses requested to quit game, this method confirms
	// and does so via the console
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
	
	// NOTE This scripted test will no longer
	// simulate collisions properly, as collisions will
	// only be executed if they are colliding
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
	//		gw.playerBirdCollision();
			System.out.println(gw.getPlayerCar()+ "\n");
			consoleSleep(450);
		}
		
		consolePause();
		
		// hit 2 cars
		System.out.println(" hitting 2 cars... \n");
		for( i = 1 ; i <= 2 ; i++ ){
	//		gw.carCarCollision();
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// pass a fake action to initiate the game tick
		myTickCommand.actionPerformed(e);
	}
	
	public void mousePressed(MouseEvent e){
		
		// make float point from mouse click location
		FloatPoint p = new FloatPoint( (float)e.getPoint().getX(), (float)e.getPoint().getY() );
		
		// am I adding an obj? if not, then go to selection
		
		// dynamically add a new pylon
		if( gw.doAddNewPylon() ){
			
			// placeholder GameObject for pylon
			GameObject newPylon = gw.getGOF().getGameObject("PYLON", p);
			
			// add the pylon if not null
			if (newPylon != null)
				gw.getObjects().add( newPylon );
			
			// kill the flag
			gw.setNewPylonFlag(false);
			
		}else if ( gw.doAddNewFuelCan() ){
			
			// placeholder GameObject for fuel can
			GameObject newFuelCan = gw.getGOF().getGameObject("FUEL", p);
			
			// add a new fuel can if not null
			if( newFuelCan != null)
				gw.getObjects().add( newFuelCan );
			
			// kill the flag
			gw.setNewFuelCanFlag(false);
			
		} else { // wasn't adding anything, select an object
			
			// iterator and placeholder object
			IIterator anIterator = gw.getObjects().getIterator();
			Object currentObj;
			
			// check to see if any were selected
			while( anIterator.hasNext() ){
				currentObj = anIterator.next();
				// if the click was in the obj, it is selected
				if( ((GameObject)currentObj).contains(p) )
					((GameObject)currentObj).setSelected(true);
				// if ctrl is pressed, keep the current selected state
				else if( e.isControlDown() )
					((GameObject)currentObj).setSelected( ((GameObject)currentObj).isSelected() );
				else
					((GameObject)currentObj).setSelected(false);
			}
		}
		myMapView.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}