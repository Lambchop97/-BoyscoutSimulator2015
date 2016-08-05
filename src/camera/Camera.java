package camera;

import entity.Entity;
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
}
