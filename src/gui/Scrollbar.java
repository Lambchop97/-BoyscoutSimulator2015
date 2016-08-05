package gui;

import java.awt.Rectangle;
import java.util.Arrays;

import display.GameDisplay;
import display.Screen;
import graphics.ArtManager;
import graphics.Sprite;
import userInput.BSInputHandler;
import utility.Vector2f;

public class Scrollbar extends UIComponent{
	private boolean lastTickLeftClick = false;
	private boolean hashtagFreePass = false;
	private boolean horizontal;
	private Vector2f delta;
	private float length;
	public static int barColor = 0xff494949;
	private boolean clicked = false;
	private int mouseX = 0, mouseY = 0;
	private float deltaMouseX = 0, deltaMouseY = 0;
	
	public Scrollbar(float length, Vector2f position){
		super(position, 0, 0);
		this.length = length;
		horizontal = false;
		this.position = position;
		sprite = new Sprite(ArtManager.scrollbarSpriteSheet, 0, 0, 11, 15);
		delta = new Vector2f(0, 0);
		
	}
	
	public void update(BSInputHandler input){
		if((input.mouse.leftButton && !lastTickLeftClick) || hashtagFreePass){
			if(!clicked){
				Rectangle mouse = new Rectangle(input.mouse.x, input.mouse.y, 1, 1);
				Rectangle slider;
				if(horizontal){
					slider = new Rectangle((int) ((position.x + delta.x) * GameDisplay.frame().getContentPane().getSize().width), (int) ((position.y + delta.y) * GameDisplay.frame().getContentPane().getSize().height), (int) (45 * ((float) GameDisplay.instance().width / GameDisplay.MAX_WIDTH)), (int) (33 * ((float) GameDisplay.instance().height / GameDisplay.MAX_HEIGHT)));//-1-3
				} else {
					slider = new Rectangle((int) ((position.x + delta.x) * GameDisplay.frame().getContentPane().getSize().width), (int) ((position.y + delta.y) * GameDisplay.frame().getContentPane().getSize().height), (int) (33 * ((float) GameDisplay.instance().width / GameDisplay.MAX_WIDTH)), (int) (45 * ((float) GameDisplay.instance().height / GameDisplay.MAX_HEIGHT)));
				}
				
				if(slider.intersects(mouse)){
					clicked = true;
					mouseX = input.mouse.x;
					mouseY = input.mouse.y;
					hashtagFreePass = true;
					deltaMouseX = (mouseX - ((position.x + delta.x) * GameDisplay.frame().getContentPane().getSize().width)) / GameDisplay.instance().width;
					deltaMouseY = (mouseY - ((position.y + delta.y) * GameDisplay.frame().getContentPane().getSize().height)) / GameDisplay.instance().height;
				}				
			} else {
				if(horizontal){
					if(input.mouse.x != mouseX){
						delta.x = ((float) input.mouse.x / GameDisplay.frame().getContentPane().getSize().width) - position.x - deltaMouseX;
						
						if(delta.x > length){
							delta.x = length;
						}
						if(delta.x < 0){
							delta.x = 0;
						}
					}
				} else {
					if(input.mouse.y != mouseY){
						delta.y = ((float) input.mouse.y / GameDisplay.frame().getContentPane().getSize().height) - position.y - deltaMouseY;

						if(delta.y > length){
							delta.y = length;
						}
						if(delta.y < 0){
							delta.y = 0;
						}

					}
				}
			}
		} else if(clicked = true){
			clicked = false;
		}
		if(input.mouse.leftButton && !lastTickLeftClick){
			lastTickLeftClick = true;
		}
		if(!input.mouse.leftButton && lastTickLeftClick){
			lastTickLeftClick = false;
			hashtagFreePass = false;
		}
		
	}
	
	public void render(Screen screen){
		Sprite bar;
		int x = 0, y = 0;
		if(horizontal){
			bar = new Sprite((int) (length * GameDisplay.MAX_WIDTH / GameDisplay.SCALE), 3);
			 x = 7; y = 4;
		} else {
			bar = new Sprite(3, (int) (length * GameDisplay.MAX_HEIGHT / GameDisplay.SCALE));
			x = 4; y = 7;
		}
		Arrays.fill(bar.pixels, barColor);
		screen.render(bar, new Vector2f(position.x * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE) + GameDisplay.camera.offset.x + x, position.y * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE) + GameDisplay.camera.offset.y + y));
		screen.render(sprite, new Vector2f(position.x * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE) + GameDisplay.camera.offset.x + delta.x * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE), position.y * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE) + GameDisplay.camera.offset.y + delta.y * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE)));
		
	
	}
	
	public float getValue(){
		if(horizontal){
			return (float) (delta.x);
		} else {			
			return (float) (delta.y);
		}
	}
	
	public void setHorizontal(boolean horizontal){
		if(this.horizontal != horizontal){
			sprite.flipPixels();
			this.horizontal = horizontal;			
		}
	}
	
	public boolean isHorizontal(){
		return horizontal;
	}
	
	public boolean getClicked(){
		return clicked;
	}
}
