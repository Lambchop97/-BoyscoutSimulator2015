package gui;

import display.GameDisplay;
import display.Screen;
import userInput.BSInputHandler;
import utility.Vector2f;

public class Border extends UIComponent{
	public static int[] colors = {0xffff0000, 0xffff6A00, 0xffffD800, 0xff00ff21, 0xff00ffff, 0xff0026ff, 0xff4800ff, 0xffb200ff, 0xffff006e};
	public int colorIndex;
	
	public Border(Vector2f position, int width, int height, int colorIndex){
		super(position, width, height);
		this.colorIndex = colorIndex;
		for(int i = 0; i < height; i++){
			for(int k = 0; k < width; k++){
				if(i == 0 || i == height - 1 || k == 0 || k == width - 1){
					sprite.pixels[i * width + k] = colors[colorIndex];					
				}
			}
		}
	}
	
	public void render(Screen screen){
		screen.render(sprite, new Vector2f(position.x + GameDisplay.camera.offset.x, position.y + GameDisplay.camera.offset.y));
	}

	public void update(BSInputHandler input) {
		return;
	}
}
