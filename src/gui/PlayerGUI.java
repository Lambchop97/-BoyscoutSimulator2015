package gui;

import java.awt.Rectangle;

import display.GameDisplay;
import display.Screen;
import graphics.ArtManager;
import graphics.Font;
import graphics.Sprite;
import userInput.BSInputHandler;
import utility.Vector2f;

public class PlayerGUI extends UIComponent{

	private Sprite player;
	private Sprite base;
	private int health;
	private int mana;
	private int exp;
	private boolean leftPressedLastUpdate;
	
	public PlayerGUI(Vector2f position){
		super(position, 100, 50);
		health = 0;
		mana = 0;
		exp = 0;
		player = new Sprite(ArtManager.terrainSpriteSheet, 2 * 32, 11 * 32, 32, 32);
		base = new Sprite(ArtManager.basePlayerGUISpriteSheet, 0, 0, 100, 50);
		leftPressedLastUpdate = false;
	}

	public void render(Screen screen) {
		screen.render(base, new Vector2f(position.x + GameDisplay.camera.offset.x, position.y + GameDisplay.camera.offset.y));
		screen.render(player, new Vector2f(position.x + 1 + GameDisplay.camera.offset.x, position.y + 1 + GameDisplay.camera.offset.y));
		bar6515[health].changeColor(0xff404040, 0xff00b600);
		bar6515[health].changeColor(0xff808080, 0x004ac100);
		screen.render(bar6515[health], new Vector2f(position.x + 34 + GameDisplay.camera.offset.x, position.y + 1 + GameDisplay.camera.offset.y));
		bar6515[health].changeColor(0xff00b600, 0xff404040);
		bar6515[health].changeColor(0x004ac100, 0xff808080);
		Font.staticDraw(screen, 65 - health + "/65", (int) position.x + 43, (int) position.y + 2, 1f, .75f);
		
		bar6515[mana].changeColor(0xff808080, 0x004ac100);
		bar6515[mana].changeColor(0xff404040, 0xff0000ff);
		screen.render(bar6515[mana], new Vector2f(position.x + 34 + GameDisplay.camera.offset.x, position.y + 18 + GameDisplay.camera.offset.y));
		bar6515[mana].changeColor(0xff0000ff, 0xff404040);
		bar6515[mana].changeColor(0x004ac100, 0xff808080);
		Font.staticDraw(screen, 65 - mana + "/65", (int) position.x + 43, (int) position.y + 19, 1f, .75f);

		
		bar9815[bar9815.length - exp - 1].changeColor(0xff808080, 0x002a2bff);
		bar9815[bar9815.length - exp - 1].changeColor(0xff404040, 0xff6024a0);
		screen.render(bar9815[bar9815.length - exp - 1], new Vector2f(position.x + 1 + GameDisplay.camera.offset.x, position.y + 34 + GameDisplay.camera.offset.y));
		bar9815[bar9815.length - exp - 1].changeColor(0x002a2bff, 0xff808080);
		bar9815[bar9815.length - exp - 1].changeColor(0xff6024a0, 0xff404040);
		Font.staticDraw(screen, exp + "/98", (int) position.x + 34, (int) position.y + 35, 1f, .75f);

	}

	public void update(BSInputHandler input) {
		Rectangle mouse = new Rectangle(input.mouse.x, input.mouse.y, 1, 1);
		Rectangle gui = new Rectangle((int) ((position.x) * (GameDisplay.frame().getContentPane().getSize().width / GameDisplay.MAX_WIDTH)), (int) ((position.y) * (GameDisplay.frame().getContentPane().getSize().height / GameDisplay.MAX_HEIGHT)), (int) (width * GameDisplay.SCALE * ((float) GameDisplay.frame().getContentPane().getSize().width / GameDisplay.MAX_WIDTH)), (int) (height * GameDisplay.SCALE * ((float) GameDisplay.frame().getContentPane().getSize().height / GameDisplay.MAX_HEIGHT)));//-1-3

		if(mouse.intersects(gui)){
			if(!leftPressedLastUpdate && input.mouse.leftButton){
				GameDisplay.onUi = true;				
			}
		}
		
		if(!leftPressedLastUpdate && input.mouse.leftButton){
			leftPressedLastUpdate = true;
		}
		if(leftPressedLastUpdate && !input.mouse.leftButton){
			leftPressedLastUpdate = false;
		}
		health+=3;
		if(health >= bar6515.length){
			mana+=3;
			health = 0;
		}
		if(mana >= bar6515.length){
			exp++;
			mana = 0;
		}
		if(exp >= bar9815.length){
			exp = 0;
		}
	}
	
}
