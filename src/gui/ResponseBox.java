package gui;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import display.GameDisplay;
import display.Screen;
import graphics.ArtManager;
import graphics.Font;
import graphics.Sprite;
import userInput.BSInputHandler;
import utility.Vector2f;

public class ResponseBox extends UIComponent{

	private Sprite background;
	private String msg;
	private String response;
	private boolean backspacePressed;
	private long lastBackspace;
	private static int[] backspaceTimes = {300, 250, 200, 150, 100, 50};
	private int numBackspaces;
	private static String[] keys = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", " "};
	private static int[] keycodes = {KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E, KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P, KeyEvent.VK_Q, KeyEvent.VK_R, KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X, KeyEvent.VK_Y, KeyEvent.VK_Z, KeyEvent.VK_SPACE};
	private static boolean[] pressed = {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
	private int maxCharacters;
	private boolean leftPressedLastUpdate;
	private boolean canDrag;
	private int xDiff, yDiff;
	private Button ok;
	private boolean hasFocus = false;
	
	private boolean answered = false;
	private boolean answerUsed = false;
	
	public ResponseBox(Vector2f position, String msg){
		super(position, 120, 100);
		background = new Sprite(ArtManager.responseBoxSpriteSheet, 0, 0, 141, 140);
		this.msg = msg;
		response = "Yes";
		backspacePressed = false;
		lastBackspace = System.currentTimeMillis();
		numBackspaces = 0;
		maxCharacters = (width / 11 + 1) * (height / 16 + 1);
		leftPressedLastUpdate = false;
		canDrag = false;
		xDiff = 0;
		yDiff = 0;
		ok = new Button(new Vector2f(position.x + .195f, position.y + .328f), " ");
		ok.changeColors(0xff00aa00, 0xff00ff00, 0xff00aa00);
	}

	public void render(Screen screen) {
		if(!answered){
			screen.render(background, new Vector2f((position.x) * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE) + GameDisplay.camera.offset.x, (position.y) * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE) + GameDisplay.camera.offset.y));
			Font.staticDraw(screen, msg, (int) ((position.x + .0033f) * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE)), (int) ((position.y + .007f) * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE)), 1f, 1f, false);
			Font.setAlign(true);
			Font.staticDraw(screen, response, (int) ((position.x + .0033f) * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE)), (int) ((position.y + .062f) * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE)), 1.0f, 1.0f, (int) ((position.x + .0033f) * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE)) + width, height);
			Font.setAlign(false);
			ok.render(screen);
			if(hasFocus){
				new Border(new Vector2f((position.x) * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE), (position.y) * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE)), 141, 140, 3).render(screen);
			}			
		}
	}

	public void update(BSInputHandler input) {
		if(!answered){
			Rectangle mouse = new Rectangle(GameDisplay.instance().getBSCursor().x, GameDisplay.instance().getBSCursor().y, 1, 1);
			Rectangle box = new Rectangle((int) (position.x * GameDisplay.MAX_WIDTH / GameDisplay.SCALE), (int) (position.y * GameDisplay.MAX_HEIGHT / GameDisplay.SCALE), 141, 140);
			
			if(mouse.intersects(box)){
				GameDisplay.overGui = true;
				if(!leftPressedLastUpdate && input.mouse.leftButton){
					GameDisplay.onUi = true;	
					Rectangle top = new Rectangle((int) (position.x * GameDisplay.MAX_WIDTH / GameDisplay.SCALE), (int) (position.y * GameDisplay.MAX_HEIGHT / GameDisplay.SCALE), 141, 21);
					if(mouse.intersects(top)){
						canDrag = true;
						xDiff = (int) (position.x * GameDisplay.MAX_WIDTH / GameDisplay.SCALE) - GameDisplay.instance().getBSCursor().x;
						yDiff = (int) (position.y * GameDisplay.MAX_HEIGHT / GameDisplay.SCALE) - GameDisplay.instance().getBSCursor().y;
					}
					hasFocus = true;
					if(compWithFocus == null){
						compWithFocus = this;
					} else {
						if(!compWithFocus.equals(this)){
							compWithFocus = this;
						}
					}
				}
			} else {
				if(!leftPressedLastUpdate && input.mouse.leftButton){
					hasFocus = false;				
				}
			}
			
			if(!leftPressedLastUpdate && input.mouse.leftButton){
				leftPressedLastUpdate = true;
			}
			if(leftPressedLastUpdate && !input.mouse.leftButton){
				leftPressedLastUpdate = false;
				canDrag = false;
			}
			
			if(canDrag){
				position = new Vector2f((float) (GameDisplay.instance().getBSCursor().x + xDiff) / GameDisplay.MAX_WIDTH * GameDisplay.SCALE, (float) (GameDisplay.instance().getBSCursor().y + yDiff) / GameDisplay.MAX_HEIGHT * GameDisplay.SCALE);
				ok.move(new Vector2f(position.x + .195f, position.y + .328f));
				
			}
			
			ok.update(input);
			if(ok.isClicked()){
				answered = true;
			}
			if(hasFocus){
				if(input.keyboard.isKeyPressed(KeyEvent.VK_BACK_SPACE)){
					if(response.length() == 0) return;
					if(!backspacePressed){
						response = response.substring(0, response.length() - 1);
						lastBackspace = System.currentTimeMillis();
						backspacePressed = true;
					}
					if(!(System.currentTimeMillis() - lastBackspace < backspaceTimes[numBackspaces])){
						response = response.substring(0, response.length() - 1);
						lastBackspace = System.currentTimeMillis();
						if(numBackspaces < backspaceTimes.length - 1){
							numBackspaces++;
						}				
					}
				} else {
					numBackspaces = 0;
					if(backspacePressed){
						backspacePressed = false;
					}
				}
				if(response.length() >= maxCharacters){
					return;
				}
				for(int i = 0; i < keycodes.length; i++){
					if(input.keyboard.isKeyPressed(keycodes[i]) && !pressed[i]){
						pressed[i] = true;
						response += keys[i];
					}
					if(!input.keyboard.isKeyPressed(keycodes[i]) && pressed[i]){
						pressed[i] = false;
					}
				}
			}
		}
	}
}
