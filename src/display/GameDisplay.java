package display;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import camera.Camera;
import debug.DebugDisplay;
import debug.Logger;
import entity.Crab;
import entity.Entity;
import entity.TestEntity;
import graphics.ArtManager;
import graphics.Font;
import gui.Button;
import gui.PlayerGUI;
import gui.ResponseBox;
import gui.UIComponent;
import thread.ThreadManager;
import userInput.BSInputHandler;
import userInput.Control;
import utility.Vector2f;
import world.OverWorld;
import world.Tile;
import world.generator.Generator;

/**
 * 
 * @author Joshua Kuennen
 * A class which creates and maintains the game
 */
public class GameDisplay extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8430565387662810929L;
	
	/**
	 *  Title of the game
	 */
	private static final String TITLE = "Boyscout Simulator 2015";
	
	/**
	 *  How many pixels in the game is equal to one in the sprite
	 */
	public static int SCALE = 3; 
	
	/**
	 *  Maximum dimensions that the game can be times the scale, 
	 */
	public static final int MAX_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width;
	public static final int MAX_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height;
	
	/**
	 *  Default and current dimensions of the game
	 */
	public int width = Toolkit.getDefaultToolkit().getScreenSize().width;
	public int height = Toolkit.getDefaultToolkit().getScreenSize().height;

	/**
	 *  Ticks or updates per second
	 */
	private static final int tps = 60; 

	/**
	 *  Release version
	 */
	public static final String VERSION = "0.0.1";
	
	/**
	 *  The JFrame object of the window
	 */
	private static JFrame frame;
	
	/**
	 *  The Input handler for the game
	 */
	private BSInputHandler input;
	
	/**
	 * The state of whether or not the game is running
	 */
	private boolean running = false;
	
	/**
	 * click and drag variables
	 */
	public static boolean onUi = false;
	private Vector2f mouseClicked = new Vector2f();
	public Vector2f panVelocity = new Vector2f();
	private boolean justClicked = false;

	/**
	 * Game objects
	 */
	private OverWorld world;
	public static Camera camera;
	public static Generator generator;
	private static GameDisplay game;	
	private Screen screen;
	
	/**
	 *  Test objects
	 */
	private List<Entity> ents;
	private Button butt;
	private boolean placed;
	private boolean placin = false;
	private ResponseBox rb;
	private PlayerGUI gui;
	
	
	/**
	 * Constructor for GameDisplay, calls the init method
	 */
	public GameDisplay() {
		init();
		screen = new Screen(width / SCALE, height / SCALE);
		game = this;
	}

	/**
	 * Sets the game up, initializing the variables for the game, then starts the game loop
	 */
	public void init() {

		//Set the frame up, default is fullscreen, but can be changed in the settings
		frame = new JFrame(TITLE + " v" + VERSION);
		frame.setUndecorated(true);
		Dimension dimension = new Dimension((int) (width), (int) (height));
		this.setPreferredSize(dimension);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.add(this);
		
		input = new BSInputHandler(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		this.requestFocus();
		frame.setVisible(true);

		//cleans up everything once the window is closed;
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				closeWindow();
			}
		});

		//initialize all other Classes that need to be initialized
		Logger.init();
		ArtManager.init();
		Font.init();
		Tile.init();
		Control.init(); 
		UIComponent.init();
		
		//initialize the variables for the game
		camera = new Camera(new Vector2f(0,0));
		generator = new Generator();
		world = new OverWorld();
		
		//Test objects
		ents = new ArrayList<Entity>();
		for(int i = 0; i < 1; i++){
			ents.add(new TestEntity(new Vector2f(100f,i * 32f)));			
		}
		butt = new Button(new Vector2f(.2f, .2f), "Fangrilla");
		rb = new ResponseBox(new Vector2f(.3f, .3f), "Are you sure?");
		gui = new PlayerGUI(new Vector2f(5f, 5f));
		
		//start game loop
		this.start();
	}

	/**
	 * Cleans up everything that needs to be cleaned up when the window is closed
	 */
	protected static void cleanup() {
		Logger.cleanup();
	}
	
	/**
	 * Main method, Only creates a GameDisplay Object
	 *
	 * @param args arguments passed to the program
	 */
	public static void main(String[] args) {
		new GameDisplay();
	}

	/**
	 * Creates and starts the game thread
	 */
	public synchronized void start() {
		running = true;
		ThreadManager.gameThread = new Thread(this);
		ThreadManager.gameThread.start();
	}

	/**
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 * 
	 * Main game loop 
	 */
	public void run() {
		int frame = 0, updates = 0;
		long fpsTimer = System.currentTimeMillis();
		double nsPerTick = 1000000000.0d / tps;
		double then = System.nanoTime();
		double delta = 0;
		while (running) {
			double now = System.nanoTime();
			delta += (now - then) / nsPerTick;
			then = now;
			boolean canRender = true;
			while (delta > 1) {
				updates++;
				update();
				delta -= 1;
				canRender = true;
			}
			if (canRender) {
				frame++;
				render();
				canRender = false;
			}
			if (System.currentTimeMillis() - fpsTimer > 1000) {
				System.out.printf("%d tick,  %d tock %n", updates, frame);
				DebugDisplay.frames = frame;
				DebugDisplay.updates = updates;
				frame = 0;
				updates = 0;
				fpsTimer += 1000;
			}
		}
	}

	/**
	 * Updates the game logic
	 */
	public void update() {
		Control.update(input);
		if(Control.getControlFromName("Up").isPressed()){
			camera.move(new Vector2f(0, -10));
		}
		if(Control.getControlFromName("Left").isPressed()){
			camera.move(new Vector2f(-10, 0));
		}
		if(Control.getControlFromName("Down").isPressed()){
			camera.move(new Vector2f(0, 10));
		}
		if(Control.getControlFromName("Right").isPressed()){
			camera.move(new Vector2f(10, 0));
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_ESCAPE)){
			closeWindow();
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_R)){
			resizeWindow(Toolkit.getDefaultToolkit().getScreenSize().width, Toolkit.getDefaultToolkit().getScreenSize().height);
			}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_T)){
			resizeWindow((int) (Toolkit.getDefaultToolkit().getScreenSize().width * .75f), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * .75f));
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_Y)){
			resizeWindow((int) (Toolkit.getDefaultToolkit().getScreenSize().width * .5f), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * .5f));
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_U)){
			resizeWindow((int) (Toolkit.getDefaultToolkit().getScreenSize().width * .25f), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * .25f));
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_I)){
			resizeWindow((int) (Toolkit.getDefaultToolkit().getScreenSize().width * .125f), (int) (Toolkit.getDefaultToolkit().getScreenSize().height * .1255f));
		}
		for(Entity ent: ents){
			ent.update(input);
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_P)){
			placin = true;
		} else {
			placin = false;
		}
		if(placin && input.mouse.leftButton && !placed && !onUi){
			for(int i = 0; i < 1; i++){
				ents.add(new Crab(new Vector2f(input.mouse.x / SCALE + camera.offset.x, input.mouse.y / SCALE  + camera.offset.y)));				
			} 
			placed = true;
		}
		if(!input.mouse.leftButton && placed){
			placed = false;
		}
		butt.update(input);
		world.update();
		DebugDisplay.update(input);
		rb.update(input);
		gui.update(input);
		if(input.mouse.leftButton && !onUi && !placin){
			if(!justClicked){
				justClicked = true;
				this.mouseClicked = new Vector2f((float) input.mouse.x, (float) input.mouse.y);
			}
			Vector2f mouseNow = new Vector2f((float) input.mouse.x, (float) input.mouse.y);
			mouseClicked.subtract(mouseNow);
			if(mouseClicked.x != mouseClicked.y)
				mouseClicked.add(0f);
			mouseClicked.scale(new Vector2f((float) MAX_WIDTH / (float) frame.getContentPane().getWidth(), (float) MAX_HEIGHT / (float) frame.getContentPane().getHeight()));
			mouseClicked.multiply(.333f);
			camera.move(mouseClicked);
			panVelocity = new Vector2f(mouseClicked);
			this.mouseClicked = mouseNow;
			
			
		} else if(justClicked){
			justClicked = false;
		}
		if(!input.mouse.leftButton){
			onUi = false;			
		}
		if(!justClicked && (panVelocity.x != 0 || panVelocity.y != 0)){
			camera.move(panVelocity);
			panVelocity.multiply(.9f);
			if(Math.abs(panVelocity.x) < 1 && Math.abs(panVelocity.y) < 1){
				panVelocity.x = 0;
				panVelocity.y = 0;
			}
		}
	}

	/**
	 * Updates the graphical part of the game, draws to a back buffer, and then stores the frame in a list of 3 to stop screen tearing
	 */
	public void render() {
		//check for buffer strategy
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		//get the graphics object
		Graphics g = bs.getDrawGraphics();
		
		//render code start
		world.render(screen);
		butt.render(screen);
		for(Entity ent: ents){
			ent.render(screen);			
		}
		rb.render(screen);
		if(!DebugDisplay.toggled()){
			gui.render(screen);			
		}
		//render code end
		
		
		g.drawImage(screen.image, 0, 0, getWidth(), getHeight(), null); //draws the screen into the BufferStrategy
		DebugDisplay.render(g); // draws the Debug display over the screen
		screen.clear(); // resets the screen for the next frame, if this wasn't there, transparent pixels would go crazy
		g.dispose(); //disposes of the graphics object
		bs.show(); //shows the next frame
	}

	/**
	 * Stops and joins the threads to the main thread
	 */
	public synchronized void stop() {
		running = false;
		try {
			ThreadManager.gameThread.join();
			ThreadManager.terrainGeneration.join();
			ThreadManager.debuggingThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Calls cleanup and then exits the program
	 */
	public void closeWindow(){
		cleanup();
		System.exit(0);
	}
	
	/**
	 * Resizes the window by completely recreating the frame object with the new desired dimension
	 * @param width The desired width of the frame
	 * @param height The desired height of the frame
	 */
	public void resizeWindow(int width, int height){
		if(width > MAX_WIDTH || height > MAX_HEIGHT) return;
		if(width == this.width && height == this.height) return;
		frame.dispose();
		frame = new JFrame(TITLE + " v" + VERSION);
		if(width == MAX_WIDTH && height == MAX_HEIGHT){
			frame.setUndecorated(true); 
		} else {
			frame.setUndecorated(false);
		}
		Dimension dimension = new Dimension(width, height);
		frame.setPreferredSize(dimension);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.add(this);
		
		input = new BSInputHandler(this);
		frame.pack();
		frame.setLocationRelativeTo(null);
		this.requestFocus();
		frame.setVisible(true);

		//cleans up everything once the window is closed;
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				closeWindow();
			}
		});
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Used to get non static variables in the current instance of the game
	 * @return game the current instance of the game
	 */
	public static GameDisplay instance(){
		return game;
	}
	
	/**
	 * Used mainly to get the size of the rendered area in the frame
	 * @return frame the window in which the game is rendered
	 */
	public static JFrame frame(){
		return frame;
	}
	
	/**
	 * Calculates the height from the width in a 16 x 9 display
	 * @param width the width with which to calculate the height
	 * @return height the height calculated from the width in a 16 x 9 display
	 */
	public static float heightFromWidth(float width){
		return width * 16 / 9;
	}
	
	/**
	 * Returns true if the game is running, false otherwise
	 * @return running the current running state of the game
	 */
	public boolean isRunning(){
		return running;
	}
}
