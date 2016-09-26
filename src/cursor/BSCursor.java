package cursor;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import display.GameDisplay;
import display.Screen;
import graphics.Sprite;
import graphics.SpriteSheet;
import userInput.BSInputHandler;
import utility.Vector2f;

public class BSCursor {
	
	private Vector2f position;
	private List<Sprite> variations;
	private int currentVariation;
	
	public int x, y;
	private float sensitivity;
	private boolean pressed = false;
	private int counter = 0;
	
	public BSCursor(List<Sprite> variations, Vector2f position){
		this.variations = variations;
		this.position = position;
		sensitivity = 1;
		currentVariation = 0;
		x = (int) position.x;
		y = (int) position.y;
	}
	
	public void setSensitivity(float value){
		sensitivity = value;
	}
	
	public float getSensitivity(){
		return sensitivity;
	}
	
	public void update(BSInputHandler input){
		position.add(new Vector2f(input.mouse.dx / (float) GameDisplay.SCALE * sensitivity, input.mouse.dy / (float) GameDisplay.SCALE * sensitivity));
		x = (int) position.x + 4;
		y = (int) position.y + 4;
		if(position.x < 0) position.x = 0;
		if(position.y < 0) position.y = 0;
		if(position.x > GameDisplay.MAX_WIDTH / GameDisplay.SCALE) position.x = GameDisplay.MAX_WIDTH / GameDisplay.SCALE - 1;
		if(position.y > GameDisplay.MAX_HEIGHT / GameDisplay.SCALE) position.y = GameDisplay.MAX_HEIGHT / GameDisplay.SCALE - 1;
		
		if(input.keyboard.isKeyPressed(KeyEvent.VK_UP) && !pressed){
			position.y -= 1.0f;
			pressed = true;
			counter = 10;
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_DOWN) && !pressed){
			position.y += 1.0f;
			pressed = true;
			counter = 10;
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_LEFT) && !pressed){
			position.x -= 1.0f;
			pressed = true;
			counter = 10;
		}
		if(input.keyboard.isKeyPressed(KeyEvent.VK_RIGHT) && !pressed){
			position.x += 1.0f;
			pressed = true;
			counter = 10;
		}
		if(pressed){
			if(counter == 0){
				pressed = false;
			} else {
				counter--; 				
			}
		}
	}
	
	public void render(Screen screen){
		if(variations.size() > 0){
			screen.render(variations.get(currentVariation), new Vector2f(position.x + GameDisplay.camera.offset.x, position.y + GameDisplay.camera.offset.y));			
		}
	}
	
	public static BSCursor createDefaultCursor(){
		SpriteSheet cursor = new SpriteSheet("/Cursor.png");
		List<Sprite> variations = new ArrayList<Sprite>();
		variations.add(new Sprite(cursor, 0, 0, 9, 9));
		BSCursor cur = new BSCursor(variations, new Vector2f(GameDisplay.MAX_WIDTH / GameDisplay.SCALE / 2, GameDisplay.MAX_HEIGHT / GameDisplay.SCALE / 2));
		return cur;
	}
}
