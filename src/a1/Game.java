package a1;

import java.io.IOException;
import java.util.Scanner;

// this class is used to represent a currently running game
// it instantiates a game world and provides an interface
// for the player to interact with it. Currently only has console
// output for testing, and no 2d graphical representation (for now...)
public class Game {
	
	// create a game world
	private GameWorld gw;
	
	// scanner for input
	private Scanner in;
	
	// construct a Game
	public Game(){
		
		// create new game world and initialize it
		gw = new GameWorld();
		gw.initLayout();
		
		// scanner for input
		in = new Scanner(System.in);
		
		// run the play() method
		// currently console output only
		// this will likely be changed to "test()"
		// in future assignments
		play();
		
		// close the scanner before exiting
		in.close();
	}
	
	// current play() method, only provides console input
	// and output. This will likely be changed to a test()
	// method in the future.
	private void play(){
		
		// make string for command procesing
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

