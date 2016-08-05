package debug;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import display.GameDisplay;
import userInput.BSInputHandler;
/**
 * @author Joshua Kuennen
 * A class that is in charge of keeping track of debugging info and displaying it.
 */
public class DebugDisplay {
	
	/**
	 * Keeps track of whether of not to render the display.
	 */
	private static boolean toggled = false;
	
	/**
	 * Makes sure that it can't be toggled without releasing the key to toggle the display.
	 */
	private static boolean justToggled = false;
	
	/**
	 * Both the number of frames and ticks per second.
	 */
	public static int frames = 0, updates = 0;
	
	/**
	 * How many times screen.render is called vs finished
	 */
	public static int renderCalls = 0, renderFinishes = 0;
	
	/**
	 * Used to check whether or not to toggle the display
	 * @param input the instance of the BSInputHandler that the game uses.
	 */
	public static void update(BSInputHandler input){
		if(input.keyboard.isKeyPressed(KeyEvent.VK_F3) && !justToggled){
			toggled = !toggled;
			justToggled = true;
		}
		if(!input.keyboard.isKeyPressed(KeyEvent.VK_F3) && justToggled){
			justToggled = false;
		}
	}
	
	/**
	 * Renders the info to the screen if toggled is true.
	 * @param g the Graphics object owned by the BufferStrategy of the game.
	 */
	public static void render(Graphics g){
		if(!toggled) return;
		g.setFont(new Font(g.getFont().getName(), g.getFont().getStyle(), 25));
		g.setColor(Color.CYAN);
		g.drawString("Debug Menu", 5, 25);
		g.drawString("Boyscout Simulator v" + GameDisplay.VERSION, 200, 25);
		g.drawString("Camera: " + GameDisplay.camera.getActualOffset().x + ", " + GameDisplay.camera.getActualOffset().y, 5, 50);
		g.drawString("Velocity: " + GameDisplay.instance().panVelocity.x + ", " + GameDisplay.instance().panVelocity.y, 400, 50);
		g.drawString("FPS: " + frames + ", UPS: " + updates, 5, 75);
		g.drawString(renderCalls + " calls, " + renderFinishes + " completions", 5, 100);
		renderCalls = 0;
		renderFinishes = 0;
	}
	
	/**
	 * Returns true if the display is toggled and rendering, and false if the display is not toggled and rendering.
	 * @return toggled the state of whether or not the display is toggled
	 */
	public static boolean toggled(){
		return toggled;
	}
}
