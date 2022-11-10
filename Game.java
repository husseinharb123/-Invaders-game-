import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/**
 * The main hook of our game. This class with both act as a manager
 * for the display and central mediator for the game logic. 
 * 
 * Display management will consist of a loop that cycles round all
 * entities in the game asking them to move and then drawing them
 * in the appropriate place. With the help of an inner class it
 * will also allow the player to control the main ship.
 * 
 * As a mediator it will be informed when entities within our game
 * detect events (e.g. alient killed, played died) and will take
 * appropriate game actions.
 * 
 * @author islam and hussien 
 */
public class Game extends Canvas  {

	

	static String user;
	static String link = "alien.gif";
	/** The stragey that allows us to use accelerate page flipping */
	private BufferStrategy strategy;
	/** True if the game is currently "running", i.e. the game loop is looping */
	private boolean gameRunning = true;
	/** The list of all the entities that exist in our game */
	private ArrayList entities = new ArrayList();
	/** The list of entities that need to be removed from the game this loop */
	private ArrayList removeList = new ArrayList();
	/** The entity representing the player */
	private Entity ship;
	/** The speed at which the player's ship should move (pixels/sec) */
	private double moveSpeed = 300;
	/** The time at which last fired a shot */
	private long lastFire = 0;
	/** The interval between our players shot (ms) */
	private long firingInterval = 200;
	/** The number of aliens left on the screen */
	private int alienCount;
	private int enemyCount;
	int score=0;
	int counter1;
	boolean shoot = true;
	/** The message to display which waiting for a key press */
	private String message = "";
	/** True if we're holding up game play until a key has been pressed */
	private boolean waitingForKeyPress = true;
	/** True if the left cursor key is currently pressed */
	private boolean leftPressed = false;
	/** True if the right cursor key is currently pressed */
	private boolean rightPressed = false;
	/** True if we are firing */
	private boolean firePressed = false;
	/** True if game logic needs to be applied this loop, normally as a result of a game event */
	private boolean logicRequiredThisLoop = false;
	
	public static int angle =0;
	
	
	
	public void changevalues() {
		
		 Connection connection = null;
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:./gamedata.db");

			List<Integer> columns = new ArrayList<>();
        	String sql = "SELECT *  FROM Gamesetting WHERE  id = 123";
        	Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData mrs = rs.getMetaData();
    
            for(int i = 1; i <= mrs.getColumnCount(); i++) {
                columns.add(rs.getInt(i));
            }
            
            moveSpeed = 250+ 300*(columns.get(2)/100);
            firingInterval = 150+ 200*(columns.get(3)/100);
            enemyCount = columns.get(4) ;
            if(columns.get(1)==0) {
            	link = "alien.gif";
            }else if(columns.get(1)==1) {
            	link = "1.gif.jpg";
            }else {
            	link ="2.gif.jpg";
            }
            
//            moveSpeed = 300;
//            firingInterval = 200;
//            enemyCount = 4 ;
            
	          
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		
	}
	
	
	
	
	
	
	
	JLabel Username = new JLabel("Username:");
	JLabel Score = new JLabel("Score: 0");
	JLabel Shotsleft = new JLabel("Shots left: 2");
	JLabel computert = new JLabel("This game is played by the computer only enjoy the show!");
	JLabel firespeed = new JLabel("firespeed:");
	JLabel firerate= new JLabel("firerate:");



	
	
	
	
	
	/**
	 * Construct our game and set it running.
	 */
	public Game() {
		
		
		// create a frame to contain our game
		JFrame container = new JFrame("435 Lab Game 2.0");
		

	;
		
		Username.setBounds(20,30, 100, 30);
		Username.setForeground(Color.white);
		Username.setOpaque(true);
		Username.setBackground(Color.BLACK);
		
		Score.setBounds(20,50, 100, 30);
		Score.setForeground(Color.white);
		Score.setOpaque(true);
		Score.setBackground(Color.BLACK);
		
		Shotsleft.setBounds(20,70, 100, 30);
		Shotsleft.setForeground(Color.white);
		Shotsleft.setOpaque(true);
		Shotsleft.setBackground(Color.BLACK);
		
		computert.setBounds(240,90, 500, 100);
		computert.setForeground(Color.white);
		computert.setOpaque(true);
		computert.setBackground(Color.BLACK);
		
		firespeed.setBounds(20,130, 120, 40);
		firespeed.setForeground(Color.white);
		firespeed.setOpaque(true);
		firespeed.setBackground(Color.BLACK);
		
		firerate.setBounds(20,170, 120, 40);
		firerate.setForeground(Color.white);
		firerate.setOpaque(true);
		firerate.setBackground(Color.BLACK);
		
		
		container.add(Username);
		container.add(Score);

		container.add(computert);

	
		

		

		
		
		// get hold the content of the frame and set up the resolution of the game
		JPanel panel = (JPanel) container.getContentPane();
		panel.setPreferredSize(new Dimension(800,600));
		panel.setLayout(null);
		
		// setup our canvas size and put it into the content of the frame
		setBounds(0,0,800,600);
		panel.add(this);
		
		// Tell AWT not to bother repainting our canvas since we're
		// going to do that our self in accelerated mode
		setIgnoreRepaint(true);
		
		// finally make the window visible 
		container.pack();
		
		container.setResizable(false);
		container.setVisible(true);
		
		// add a listener to respond to the user closing the window. If they
		// do we'd like to exit the game
		container.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// add a key input system (defined below) to our canvas
		// so we can respond to key pressed
		addKeyListener(new KeyInputHandler());
		
		// request the focus so key events come to us
		requestFocus();

		// create the buffering strategy which will allow AWT
		// to manage our accelerated graphics
		createBufferStrategy(2);
		strategy = getBufferStrategy();
		
		// initialise the entities in our game so there's something
		// to see at startup
		initEntities();
	}
	
	/**
	 * Start a fresh game, this should clear out any old data and
	 * create a new set.
	 */
	private void startGame() {
		changevalues();
		// clear out any existing entities and intialise a new set
		entities.clear();
		initEntities();
		
		// blank out any keyboard settings we might currently have
		leftPressed = false;
		rightPressed = false;
		firePressed = false;
		String str1 = "Enemies_to_kill:" + Integer.toString(enemyCount);
//		enemycount1.setText(str1);
		
		String str2 = "moveSpeed:" + Double.toString(moveSpeed);
		firespeed.setText(str2);
		
		String str4 = "firingInterval:" + Double.toString(firingInterval);
		firerate.setText(str4);
		
		String str3 = "Username:" + user ;
		Username.setText(str3);
	}
	
	/**
	 * Initialise the starting state of the entities (ship and aliens). Each
	 * entitiy will be added to the overall list of entities in the game.
	 */
	private void initEntities() {
		counter1 = 0 ;
		// create a block of aliens (5 rows, by 12 aliens, spaced evenly)
		alienCount = 0;

//		Entity alien = new AlienEntity(this,"sprites/"+link,370,800,0);
//		entities.add(alien);
		alienCount++;
		// create the player ship and place it roughly in the center of the screen
		ship = new ShipEntity(this,"sprites/ship.gif",370,550);
		entities.add(ship);
		
	
			}
		
	
	/**
	 * Notification from a game entity that the logic of the game
	 * should be run at the next opportunity (normally as a result of some
	 * game event)
	 */
	public void updateLogic() {
		logicRequiredThisLoop = true;
	}
	
	/**
	 * Remove an entity from the game. The entity removed will
	 * no longer move or be drawn.
	 * 
	 * @param entity The entity that should be removed
	 */
	public void removeEntity(Entity entity) {
		removeList.add(entity);
	}
	
	/**
	 * Notification that the player has died. 
	 */
	public void notifyDeath() {
		message = "Oh no! They got you, try again?";
		waitingForKeyPress = true;
	}
	
	/**
	 * Notification that the player has won since all the aliens
	 * are dead.
	 */
	public void notifyWin() {
		message = "Well done! Nice Score!";
		waitingForKeyPress = true;
	}
	
	/**
	 * Notification that an alien has been killed
	 */
	public void notifyAlienKilled() {
		score+=1;
		String str = "Score: " + Integer.toString(score);
		Score.setText(str);
	
		for (int i=0;i<entities.size();i++) {
			Entity entity = (Entity) entities.get(i);
			
			if (entity instanceof AlienEntity) {
				// speed up by 2%
				entity.setHorizontalMovement(entity.getHorizontalMovement() * 1.02);
			}
		}
	}
	
	/**
	 * Attempt to fire a shot from the player. Its called "try"
	 * since we must first check that the player can fire at this 
	 * point, i.e. has he/she waited long enough between shots
	 */
	public void tryToFire() {
		// check that we have waiting long enough to fire
		if (System.currentTimeMillis() - lastFire < firingInterval) {
			return;
		}
		shoot =true;
		if(counter1 >  0) {
			shoot = false;	
		}
		if(shoot == true){
			// if we waited long enough, create the shot entity, and record the time.
			lastFire = System.currentTimeMillis();
			ShotEntity shot = new ShotEntity(this,"sprites/shot.gif",ship.getX(),ship.getY(), angle+1);
			entities.add(shot);
		}
		counter1+=1;
		if(counter1 == 0 || counter1==1 || counter1==2) {
		String shots = "Shots Left: " + Integer.toString(2-counter1);
		Shotsleft.setText(shots);
		}
		
	
	}
	
	/**
	 * The main game loop. This loop is running during all game
	 * play as is responsible for the following activities:
	 * <p>
	 * - Working out the speed of the game loop to update moves
	 * - Moving the game entities
	 * - Drawing the screen contents (entities, text)
	 * - Updating game events
	 * - Checking Input
	 * <p>
	 */
	public void gameLoop() {
		long lastLoopTime = System.currentTimeMillis();
		long addDelta = 0;
		long addfire = 0;


		// keep looping round til the game ends
		while (gameRunning) {
			// work out how long its been since the last update, this
			// will be used to calculate how far the entities should
			// move this loop
			long delta = System.currentTimeMillis() - lastLoopTime;
			lastLoopTime = System.currentTimeMillis();
			addDelta += delta;
			addfire +=delta;
			
			// Get hold of a graphics context for the accelerated 
			// surface and blank it out
			Graphics2D g = (Graphics2D) strategy.getDrawGraphics();
			g.setColor(Color.black);
			g.fillRect(0,0,800,600);
			
			// cycle round asking each entity to move itself
			if (!waitingForKeyPress) {
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					
					entity.move(delta);
				}
			}
			
			// cycle round drawing all the entities we have in the game
			for (int i=0;i<entities.size();i++) {
				Entity entity = (Entity) entities.get(i);
				
				entity.draw(g);
			}
			
			// brute force collisions, compare every entity against
			// every other entity. If any of them collide notify 
			// both entities that the collision has occured
			for (int p=0;p<entities.size();p++) {
				for (int s=p+1;s<entities.size();s++) {
					Entity me = (Entity) entities.get(p);
					Entity him = (Entity) entities.get(s);
					
					if (me.collidesWith(him)) {
						me.collidedWith(him);
						him.collidedWith(me);
					}
				}
			}
			
			
			
			// remove any entity that has been marked for clear up
			entities.removeAll(removeList);
			removeList.clear();

			// if a game event has indicated that game logic should
			// be resolved, cycle round every entity requesting that
			// their personal logic should be considered.
			if (logicRequiredThisLoop) {
				for (int i=0;i<entities.size();i++) {
					Entity entity = (Entity) entities.get(i);
					entity.doLogic();
				}
				
				logicRequiredThisLoop = false;
			}
			
			// if we're waiting for an "any key" press then draw the 
			// current message 
			if (waitingForKeyPress) {
				g.setColor(Color.white);
				g.drawString(message,(800-g.getFontMetrics().stringWidth(message))/2,250);
				g.drawString("Press any key",(800-g.getFontMetrics().stringWidth("Press any key"))/2,300);
			}
			
			// finally, we've completed drawing so clear up the graphics
			// and flip the buffer over
			g.dispose();
			strategy.show();
			
			// resolve the movement of the ship. First assume the ship 
			// isn't moving. If either cursor key is pressed then
			// update the movement appropraitely
			ship.setHorizontalMovement(0);
			
//			if ((leftPressed) && (!rightPressed)) {
//				ship.setHorizontalMovement(-moveSpeed);
//			} else if ((rightPressed) && (!leftPressed)) {
//				ship.setHorizontalMovement(moveSpeed);
//			}
			
			// if we're pressing fire, attempt to fire
//			if (firePressed) {
//				tryToFire();
//			
//				}
			if(addfire>4020) {
				tryToFire();
				addfire=0;
			}
			
			if(addDelta>4000) {
				Random rand1 = new Random();
				int temp1 = 1;
//				if(rand1.nextInt(3)>=2) {
//					temp1 = -1;
//				}
				angle= (temp1*rand1.nextInt(4));
				Entity alien1 = new AlienEntity(this,"sprites/"+link,370,500, angle);
				entities.add(alien1);
				addDelta=0;	
				counter1 = 0;
				Shotsleft.setText("Shots left: 2");
				alienCount++;
				
			
				
				if(alienCount==enemyCount) {
					notifyWin();
				}
			}
			
			// finally pause for a bit. Note: this should run us at about
			// 100 fps but on windows this might vary each loop due to
			// a bad implementation of timer
			try { Thread.sleep(10); } catch (Exception e) {}
		}
	}
	
	/**
	 * A class to handle keyboard input from the user. The class
	 * handles both dynamic input during game play, i.e. left/right 
	 * and shoot, and more static type input (i.e. press any key to
	 * continue)
	 * 
	 * This has been implemented as an inner class more through 
	 * habbit then anything else. Its perfectly normal to implement
	 * this as seperate class if slight less convienient.
	 * 
	 * @author islam and hussein
	 */
	private class KeyInputHandler extends KeyAdapter {
		/** The number of key presses we've had while waiting for an "any key" press */
		private int pressCount = 1;
		
		/**
		 * Notification from AWT that a key has been pressed. Note that
		 * a key being pressed is equal to being pushed down but *NOT*
		 * released. Thats where keyTyped() comes in.
		 *
		 * @param e The details of the key that was pressed 
		 */
		public void keyPressed(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "press"
			if (waitingForKeyPress) {
				return;
			}
			
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = true;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = true;
			}
		} 
		
		/**
		 * Notification from AWT that a key has been released.
		 *
		 * @param e The details of the key that was released 
		 */
		public void keyReleased(KeyEvent e) {
			// if we're waiting for an "any key" typed then we don't 
			// want to do anything with just a "released"
			if (waitingForKeyPress) {
				return;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				leftPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				rightPressed = false;
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				firePressed = false;
			}
		}

		/**
		 * Notification from AWT that a key has been typed. Note that
		 * typing a key means to both press and then release it.
		 *
		 * @param e The details of the key that was typed. 
		 */
		public void keyTyped(KeyEvent e) {
			// if we're waiting for a "any key" type then
			// check if we've recieved any recently. We may
			// have had a keyType() event from the user releasing
			// the shoot or move keys, hence the use of the "pressCount"
			// counter.
			if (waitingForKeyPress) {
				if (pressCount == 1) {
					// since we've now recieved our key typed
					// event we can mark it as such and start 
					// our new game
					waitingForKeyPress = false;
					startGame();
					pressCount = 0;
				} else {
					pressCount++;
				}
			}
			
			// if we hit escape, then quit the game
			if (e.getKeyChar() == 27) {
				System.exit(0);
			}
		}
	}
	static int counter =0;
	/**
	 * The entry point into the game. We'll simply create an
	 * instance of class which will start the display and game
	 * loop.
	 * 
	 * @param argv The arguments that are passed into our game
	 */
	public static void main(String argv[]) {
		
        LoginFrame frame = new LoginFrame();
        frame.setTitle("Login Form");
        frame.setVisible(true);
        frame.setBounds(0,0,800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        while(true) {	
        if(!frame.isActive()) {
        	
        	menu menu = new menu();
        	menu .setVisible(true);
        	
	
			break;
        }
        }
        
        while(true) {	
            if(!frame.isActive() && counter ==1 ) {
            	
            	Game g =new Game();
				// Start the main game loop, note: this method will not
				// return until the game has finished running. Hence we are
				// using the actual main thread to run the game.
				g.gameLoop();
            	
    	
    			break;
            }
            }

		
         	
        	
    	
        
	}


}
