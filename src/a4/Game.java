package a4;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import javax.swing.*;
import javax.swing.border.*;

//
// this class is used to represent a currently running game
// it instantiates a game world and provides an interface
// for the player to interact with it. Currently only has console
// output for testing, and no 2d graphical representation (for now...)
//
// TODO the list
//
//		additional controls (fix turning/acceleration)
//
//		compile all commands into a single GameCommand class? reduce class complexity
//
//		Make things look prettier? Birds, Pylons are left to do
//

// have to suppress this when not adding a certain serial variable
@SuppressWarnings("serial")
public class Game extends JFrame implements ActionListener, MouseListener, MouseWheelListener, MouseMotionListener {
	
	// create a game world
	private GameWorld gw;
	
	// for determining panning stuff
	private int mouseX, mouseY;
	
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
	
	public Game(){
		
		// set game to NOT paused
		isPaused = false;
		
		// FRAME STUFF
		// make title
		setTitle("Ron's A4 - 2D Driving Sim");
		
		// where to load
		setLocation(100, 100);
		
		// set size of the window...
		// if changed, you probably need to change the initial
		// window to world size as well
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
		myMapView.addMouseWheelListener(this);
		myMapView.addMouseMotionListener(this);
		
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// check to see if event is from timer
		if(e.getSource() instanceof Timer)
			myTickCommand.actionPerformed(e);
		
		

	}
	
	public void mousePressed(MouseEvent e){
		
		// save x and y of click location
		//float x = (float)e.getPoint().getX();
		//float y = (float)e.getPoint().getY();
		
		// am I adding a new object? if not, then go to selection
		
		Point2D.Float thePoint = new Point2D.Float((float)e.getPoint().getX(), (float)e.getPoint().getY());
		
		// translate thePoint to world coordinates...
		try {
			myMapView.getVTM().inverseTransform(thePoint, thePoint);
		} catch (NoninvertibleTransformException e1) {
			System.out.println(" *** BEGIN STACK TRACE ***");
			e1.printStackTrace();
			System.out.println(" *** END STACK TRACE ***");
		}
		
		float x = (float) thePoint.getX();
		float y = (float) thePoint.getY();
		
		
		// dynamically add a new pylon
		if( gw.doAddNewPylon() ){
			
			// placeholder GameObject for new pylon
			GameObject newPylon = gw.getGOF().getGameObject("PYLON", x, y);
			
			// add the pylon if not null
			if (newPylon != null)
				gw.getObjects().add( newPylon );
			
			// kill the flag
			gw.setNewPylonFlag(false);
			
		}else if ( gw.doAddNewFuelCan() ){
			
			// placeholder GameObject for new fuel can
			GameObject newFuelCan = gw.getGOF().getGameObject("FUEL", x, y);
			
			// add a new fuel can if not null
			if( newFuelCan != null)
				gw.getObjects().add( newFuelCan );
			
			// kill the flag
			gw.setNewFuelCanFlag(false);
			
		} else { // wasn't adding anything, select an object if paused
			
			if(isPaused){
				// iterator and placeholder object
				IIterator anIterator = gw.getObjects().getIterator();
				Object currentObj;
				
				// check to see if any were selected
				while( anIterator.hasNext() ){
					currentObj = anIterator.next();
					// if the click was in the obj, it is selected
					if( ((GameObject)currentObj).contains(x, y) )
						((GameObject)currentObj).setSelected(true);
					// if ctrl is pressed, keep the current selected state
					else if( e.isControlDown() )
						((GameObject)currentObj).setSelected( ((GameObject)currentObj).isSelected() );
					else
						((GameObject)currentObj).setSelected(false);
				}
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

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
	
		//if(isRunning()){
			if(e.getWheelRotation() == 1)
				myMapView.zoomOut();
			if(e.getWheelRotation() == -1)
				myMapView.zoomIn();
		//}
	}

	@Override
	public void mouseDragged(MouseEvent e) {

		//if(isRunning()){
			if(e.getX() > mouseX)
				myMapView.panRight();
			else if(e.getX() < mouseX)
				myMapView.panLeft();
			
			if(e.getY() > mouseY)
				myMapView.panDown();
			else if(e.getY() < mouseY)
				myMapView.panUp();
			
			myMapView.repaint();
		//}
		
		// update the stored mouse location values
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// update stored mouse location values
		mouseX = e.getX();
		mouseY = e.getY();
	}
}