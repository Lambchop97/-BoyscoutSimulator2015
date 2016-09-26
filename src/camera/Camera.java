package camera;

import java.awt.Rectangle;

import display.GameDisplay;
import entity.Entity;
import userInput.BSInputHandler;
import userInput.Control;
import utility.Vector2f;

/**
 * @author Joshua Kuennen
 *
 * A class the defines where the current camera is, which shows a certain area of the game
 */
public class Camera extends Entity{
	
	/**
	 * The 2 dimension vector representation of the offset, kept as whole Integers.
	 * Used for rendering purposes.
	 */
	public Vector2f offset;
	
	/**
	 * The real offset of the camera with floating point number accuracy.
	 * Keeps track of the offset with a greater accuracy.
	 * The value from which offset is calculated.
	 */
	private Vector2f actualOffset;
	
	/**
	 * Creates a camera at the initial position position.
	 * Sets the offset to be == position.
	 * Creates a new Vector2f for the actualOffset, which separates the two from each other.
	 * @param position the initial position of the camera.
	 */
	public Camera(Vector2f position){
		super(position);
		offset = this.position;
		actualOffset = new Vector2f();
	}
	
	/**
	 * Updates the offsets based on delta.
	 * Recalculates the offset from actualOffset to maintain accuracy between the two.
	 * @param delta the distance that the camera moves
	 */
	public void move(Vector2f delta){
		actualOffset.add(delta);
		offset.x = (float) ((int) actualOffset.x);
		offset.y = (float) ((int) actualOffset.y);
	}
	
	/**
	 * Returns a copy of the actualOffset so the only way that it can be changed is with move.
	 * @return actualOffset a copy of actualOffset
	 */
	public Vector2f getActualOffset(){
		return new Vector2f(actualOffset.x, actualOffset.y);
	}
	
	public void update(BSInputHandler input){
		if(!GameDisplay.overGui && !GameDisplay.onUi){
			Rectangle mouse = new Rectangle(GameDisplay.instance().getBSCursor().x - 4, GameDisplay.instance().getBSCursor().y - 4, 1, 1);
			Rectangle top = new Rectangle(0, -50, GameDisplay.MAX_WIDTH / GameDisplay.SCALE, 80);
			Rectangle bottom = new Rectangle(0, GameDisplay.MAX_HEIGHT / GameDisplay.SCALE - 30, GameDisplay.MAX_WIDTH / GameDisplay.SCALE, 80);
			Rectangle left = new Rectangle(-50, 0, 80, GameDisplay.MAX_HEIGHT / GameDisplay.SCALE);
			Rectangle right  = new Rectangle(GameDisplay.MAX_WIDTH / GameDisplay.SCALE - 30, 0, 80, GameDisplay.MAX_HEIGHT / GameDisplay.SCALE);
			
			if(mouse.intersects(top)){
				GameDisplay.instance().butt.changeText("Top");
				move(new Vector2f(0, (-34 + GameDisplay.instance().getBSCursor().y) / 2));
			}
			if(mouse.intersects(bottom)){
				GameDisplay.instance().butt.changeText("Bottom");
				move(new Vector2f(0, (34 - (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE - GameDisplay.instance().getBSCursor().y)) / 2));
			}
			if(mouse.intersects(left)){
				GameDisplay.instance().butt.changeText("Left");
				move(new Vector2f((-34 + GameDisplay.instance().getBSCursor().x) / 2, 0));
			}
			if(mouse.intersects(right)){
				GameDisplay.instance().butt.changeText("Right");
				move(new Vector2f((34 - (GameDisplay.MAX_WIDTH / GameDisplay.SCALE - GameDisplay.instance().getBSCursor().x)) / 2, 0));
			}			
		}
		if(Control.getControlFromName("Up").isPressed()){
			move(new Vector2f(0, -10));
		}
		if(Control.getControlFromName("Left").isPressed()){
			move(new Vector2f(-10, 0));
		}
		if(Control.getControlFromName("Down").isPressed()){
			move(new Vector2f(0, 10));
		}
		if(Control.getControlFromName("Right").isPressed()){
			move(new Vector2f(10, 0));
		}
		super.update(input);
	}
}
