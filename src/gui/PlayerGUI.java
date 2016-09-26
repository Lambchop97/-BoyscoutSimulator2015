package gui;

import java.awt.Rectangle;

import display.GameDisplay;
import display.Screen;
import entity.Entity;
import entitycomponent.AnimationComponent;
import graphics.ArtManager;
import graphics.Font;
import graphics.Sprite;
import userInput.BSInputHandler;
import utility.Vector2f;
import world.Tile;

public class PlayerGUI extends UIComponent{

	private Sprite playerSprite;
	private Sprite base;
	private Entity player;
	private int health;
	private int mana;
	private int exp;
	private boolean leftPressedLastUpdate;
	
	//locate entity function variables
	private int reset;
	private int clicks;
	private boolean clicked;
	
	// tile on background
	private Tile background;
	
	public PlayerGUI(Vector2f position, Entity player){
		super(position, 100, 50);
		health = 0;
		mana = 0;
		exp = 0;
		playerSprite = new Sprite(ArtManager.terrainSpriteSheet, 2 * 32, 11 * 32, 32, 32);
		base = new Sprite(ArtManager.basePlayerGUISpriteSheet, 0, 0, 100, 50);
		this.player = player;
		leftPressedLastUpdate = false;
		reset = 0;
		clicks = 0;
		clicked = false;
		background = Tile.tiles[0];
	}

	public void render(Screen screen) {
		screen.render(base, new Vector2f(position.x + GameDisplay.camera.offset.x, position.y + GameDisplay.camera.offset.y));
		background.render(screen, new Vector2f(position.x + 1 + GameDisplay.camera.offset.x, position.y + 1 + GameDisplay.camera.offset.y));
		screen.render(playerSprite, new Vector2f(position.x + 1 + GameDisplay.camera.offset.x, position.y + 1 + GameDisplay.camera.offset.y));
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
		Rectangle mouse = new Rectangle(GameDisplay.instance().getBSCursor().x, GameDisplay.instance().getBSCursor().y, 1, 1);
		Rectangle gui = new Rectangle((int) position.x, (int) position.y, width, height);//-1-3
		if(player.hasComponent("AnimationComponent")){
			playerSprite = ((AnimationComponent) player.getComponent("AnimationComponent")).getAnimation().getFrame();
			
		}
		if(mouse.intersects(gui)){
			GameDisplay.overGui = true;
			if(!leftPressedLastUpdate && input.mouse.leftButton){
				GameDisplay.onUi = true;				
			}
			Rectangle sprite = new Rectangle((int) position.x + 1, (int) position.y + 1, 32, 32);
			if(mouse.intersects(sprite)){
				if(input.mouse.leftButton && !clicked){
					clicked = true;
					reset = 10;
					clicks++;
				}
				if(!input.mouse.leftButton && clicked){
					clicked = false;
				}
				if(clicks == 2){
					GameDisplay.camera.move(new Vector2f(player.getPosition().x - GameDisplay.camera.offset.x - GameDisplay.instance().width/6 + 16, player.getPosition().y - GameDisplay.camera.offset.y - GameDisplay.instance().height/6 + 16));
					reset = 0;
					clicks = 0;
				}
				if(reset > 0){
					reset--;					
				} else {
					clicks = 0;
				}
			} else {
				clicks = 0;
				reset = 0;
				clicked = false;
			}
		}
		
		background = GameDisplay.instance().getWorld().getTile(player.getPosition().x, player.getPosition().y);
		
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
