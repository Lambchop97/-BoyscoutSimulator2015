package gui;

import display.Screen;
import graphics.ArtManager;
import graphics.Sprite;
import userInput.BSInputHandler;
import utility.Vector2f;

public abstract class UIComponent {

	protected static Sprite[] bar6515;
	protected static Sprite[] bar9815;
	protected static UIComponent compWithFocus = null;
	protected int width, height;
	protected Vector2f position;
	protected Sprite sprite;
	
	public UIComponent(Vector2f position, int width, int height){
		this.position = position;
		this.width = width;
		this.height = height;
		sprite = new Sprite(width, height);
	}
	
	public static void init(){
		bar6515 = new Sprite[66];
		for(int i = 0; i < 66; i++){
			bar6515[i] = new Sprite(ArtManager.bar6515SpriteSheet, 0, i * 15, 65, 15);
		}
		bar9815 = new Sprite[99];
		for(int i = 0; i < 99; i++){
			bar9815[i] = new Sprite(ArtManager.bar9815SpriteSheet, 0, i * 15, 98, 15);
		}
	}
	
	public abstract void render(Screen screen);
	public abstract void update(BSInputHandler input);
}
