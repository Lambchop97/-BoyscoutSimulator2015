package gui;

import java.util.Arrays;

import display.GameDisplay;
import display.Screen;
import graphics.Sprite;
import userInput.BSInputHandler;
import utility.Vector2f;

public class SpriteOverlay extends UIComponent{

	private int[] pixels;
	private Vector2f offset;
	private Scrollbar vbar, hbar;
	private boolean vertical = true, horizontal = false;
	private int vlength, hlength;
	
	public SpriteOverlay(Vector2f position, float hLength, float vLength, int width, int height){
		super(position, (int) (hLength * GameDisplay.MAX_WIDTH / GameDisplay.SCALE), (int) (vLength * GameDisplay.MAX_HEIGHT / GameDisplay.SCALE));
		pixels = new int[width * height];
		offset = new Vector2f();
		vbar = new Scrollbar(vLength - .044f, new Vector2f(position.x + hLength, position.y));
		hbar = new Scrollbar(hLength - .025f, new Vector2f(position.x , position.y + vLength));
		hbar.setHorizontal(true);
		vlength = width;
		hlength = height;
	}
	
	public void add(Sprite sprite, Vector2f position){
		position.subtract(offset);
		
		int width = sprite.width;
		int height = sprite.height;
		
				
		int xStart = (int) position.x;
		int xEnd = (int) (xStart + width);
		if(xStart < 0) xStart = 0;
		if(xEnd > this.width) xEnd = this.width;
		int xDifference = (xStart - (int) position.x);
		
		int yStart = (int) position.y;
		int yEnd = (int) (yStart + height);
		if(yStart < 0) yStart = 0;
		if(yEnd > this.height) yEnd = this.height;
		int yDifference = (yStart - (int) position.y);
		
		float spriteDeltaX = 1;
		float spriteIndexX = 0;
		
		float spriteDeltaY = 1;
		float spriteIndexY = 0;
		
		for(int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				if((sprite.pixels[(yDifference + (int) spriteIndexY) * sprite.width + (xDifference + (int) spriteIndexX)] >> 24) == 0){
					spriteIndexX += spriteDeltaX;
					continue;
				}
				pixels[y * this.width + x] = sprite.pixels[(yDifference + (int) spriteIndexY) * sprite.width + (xDifference + (int) spriteIndexX)];
				spriteIndexX += spriteDeltaX;
			}
			spriteIndexX = 0;
			spriteIndexY += spriteDeltaY;
		}
//		sprite.pixels = this.pixels;
	}
	
	public void render(Screen screen){
//		for(int i = 0; i < height; i ++){
//			for(int k = 0; k < width; k++){
//				pixels[i * width + k] = new Color(0.0f, 0.0f, (float) (i + offset.y) / (float) vlength, 1.0f).getRGB();
//			}
//		}
		sprite.pixels = pixels;
		screen.render(sprite, new Vector2f(position.x * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE) + GameDisplay.camera.offset.x, position.y * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE) + GameDisplay.camera.offset.y));
		if(vertical){			
			vbar.render(screen);
		}
		if(horizontal){
			hbar.render(screen);
		}
		Arrays.fill(pixels, 0);
	}
	
	public void update(BSInputHandler input){
		
		if(vertical){
			vbar.update(input);
			offset.y = vbar.getValue() * vlength;			
		}
		if(horizontal){
			hbar.update(input);
			offset.x = hbar.getValue() * hlength;
		}
	}
	
	public void setVBarPosition(Vector2f position){
		vbar.position = position;
	}
	
	public void setHBarPosition(Vector2f position){
		hbar.position = position;
	}
	
	public void setVLength(int length){
		vlength = length;
	}
	
	public void setHLength(int length){
		hlength = length;
	}
	
	public void setBars(boolean vert, boolean hori){
		vertical = vert;
		horizontal = hori;
		if(!vertical && !horizontal){
			vertical = true;
		}
	}
	
	public boolean isVertical(){
		return vertical;
	}
	
	public boolean isHorizontal(){
		return horizontal;
	}
}
