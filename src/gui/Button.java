package gui;

import java.awt.Rectangle;

import display.GameDisplay;
import display.Screen;
import graphics.Font;
import graphics.Sprite;
import userInput.BSInputHandler;
import utility.Vector2f;

public class Button extends UIComponent{
	private boolean clicked;
	private boolean hover;
	private boolean lastUpdateClicked;
	private String text;
	private Border border;
	private int color1 = 0xff5b5b5b;
	private int color2 = 0xff808080;
	private int color3 = 0xffd3d3d3;
	
	
	public Button(Vector2f position, String text){
		super(position, Font.getStringWidth(text) + 4, 20);
		clicked = false;
		hover = false;
		lastUpdateClicked = false;
		this.text = text;
		changeText(text);
	}
	
	public void update(BSInputHandler input){
		Rectangle mouse = new Rectangle(input.mouse.x, input.mouse.y, 1, 1);
		Rectangle butt = new Rectangle((int) ((position.x) * GameDisplay.frame().getContentPane().getSize().width), (int) ((position.y) * GameDisplay.frame().getContentPane().getSize().height), (int) (width * GameDisplay.SCALE * ((float) GameDisplay.instance().width / GameDisplay.MAX_WIDTH)), (int) (height * GameDisplay.SCALE * ((float) GameDisplay.instance().height / GameDisplay.MAX_HEIGHT)));//-1-3

		if(butt.intersects(mouse)){
			if(!lastUpdateClicked || clicked){
				hover = true;				
			}
			if(!lastUpdateClicked){
				GameDisplay.onUi = true;
				clicked = input.mouse.leftButton;				
			}
		}
		if(!input.mouse.leftButton && clicked){
			clicked = false;			
			hover = false;
		}
		if(!mouse.intersects(butt) && hover && !clicked){
			hover = false;
		}
		lastUpdateClicked = input.mouse.leftButton;
	}
	
	public void changeText(String text){
		this.text = text;
		width = Font.getStringWidth(text) + 4;
		sprite = new Sprite(width, height);
		for(int i = 0; i < height; i++){
			for(int k = 0; k < width; k++){
				if(i == 0 || k == 0){
					sprite.pixels[i * width + k] = color3;
				} else if(i == height - 1 || k == width - 1){
					sprite.pixels[i * width + k] = color1;
				} else {
					sprite.pixels[i * width + k] = color2;
				}
			}
		}
		border = new Border(new Vector2f((position.x * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE)), (position.y * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE))), width, height, 0);
	}
	
	public void render(Screen screen){
		screen.render(sprite, new Vector2f((position.x * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE)) + GameDisplay.camera.offset.x, (position.y * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE)) + GameDisplay.camera.offset.y));
		if(hover){
			border.render(screen);
		}
		Font.setColor(0xffffffff);
		Font.staticDraw(screen, text, (int) ((position.x + .0033f) * (GameDisplay.MAX_WIDTH / GameDisplay.SCALE)), (int) ((position.y + .008f) * (GameDisplay.MAX_HEIGHT / GameDisplay.SCALE)));
	}
	
	public boolean isClicked(){
		return clicked;
	}
	
	public void changeColors(int col1, int col2, int col3){
		color1 = col1;
		color2 = col2;
		color3 = col3;
		changeText(text);
	}
	public void move(Vector2f pos){
		position = pos;
		changeText(text);
	}
}
