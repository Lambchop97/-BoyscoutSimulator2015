package particles;

import display.Screen;
import graphics.Sprite;
import utility.SpriteHelper;
import utility.Vector2f;

public class Particle {

	private Vector2f position;
	private Vector2f velocity;
	private float acceleration;
	private float rotation;
	private boolean rotate;
	private float rotateVal;
	private int lifespan;
	private Sprite sprite;
	private Sprite postEffectSprite;
	private float scaleFactor;
	private boolean scaling;
	
	
	public Particle(Vector2f position, Vector2f velocity, float acceleration, int lifespan, Sprite sprite){
		this.position = position;
		this.velocity = velocity;
		this.acceleration = acceleration;
		this.sprite = sprite;
		this.lifespan = lifespan;
		rotateVal = 0;
		rotate = false;
		rotation = 0;
		postEffectSprite = sprite;
	}
	
	public void render(Screen screen){
		screen.render(sprite, position);
	}
	
	public void update(){
		position.add(velocity);
		velocity.multiply(acceleration);
		if(rotate){
			rotation += (rotateVal / 60f);
		}
		if(rotation != 0){
			postEffectSprite = SpriteHelper.rotate(postEffectSprite, rotation);
		}
		lifespan--;
	}
	
	public int getLifespan(){
		return lifespan;
	}
	public void setRotate(boolean rotate, float rotateVal){
		this.rotate = rotate;
	}
	
	public void setRotateToDir(){
		if(velocity.x == 0f){
			if(velocity.y > 0){
				rotation = (float) Math.PI / 2f;
			} else if(velocity.y < 0){
				rotation = 3 * (float) Math.PI / 2f;
			} else {
				rotation = 0;
			}
			return;
		}
		rotation = (float) Math.atan((double) (velocity.y / velocity.x));
		if(velocity.x < 0) rotation *= -1;
	}
}
