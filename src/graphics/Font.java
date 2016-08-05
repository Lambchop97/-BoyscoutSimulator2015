package graphics;

import debug.Logger;
import display.GameDisplay;
import display.Screen;
import utility.Vector2f;

public class Font {
	private static SpriteSheet font, symbol;
	private static final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789-";
	private static final String symbols = ",'.!?/:()";
	private static final int FONT_SIZE = 11, SYMBOL_SIZE = 5;
	private static int color = 0;
	private static boolean sxAlign = false;
	
	
	public static int getStringWidth(String text){
		return text.length() * 11;
	}
	
	public static void init(){
		font = ArtManager.fontSpriteSheet;
		symbol = ArtManager.symbolsSpriteSheet;
		Logger.log("Font initialized");
	}

	public static void staticDraw(Screen screen, String message, int sx, int sy){
		draw(screen, message, sx + (int) GameDisplay.camera.offset.x, sy + (int) GameDisplay.camera.offset.y, 1, 1, true, GameDisplay.MAX_WIDTH, GameDisplay.MAX_HEIGHT);
	}
	
	public static void staticDraw(Screen screen, String message, int sx, int sy, float scale){
		draw(screen, message, sx + (int) GameDisplay.camera.offset.x, sy + (int) GameDisplay.camera.offset.y, scale, scale, true, GameDisplay.MAX_WIDTH, GameDisplay.MAX_HEIGHT);
	}
	
	public static void staticDraw(Screen screen, String message, int sx, int sy, float xScale, float yScale){
		draw(screen, message, sx + (int) GameDisplay.camera.offset.x, sy + (int) GameDisplay.camera.offset.y, xScale, yScale, true, GameDisplay.MAX_WIDTH, GameDisplay.MAX_HEIGHT);
	}
	
	public static void staticDraw(Screen screen, String message, int sx, int sy, float xScale, float yScale, boolean wrap){
		draw(screen, message, sx + (int) GameDisplay.camera.offset.x, sy + (int) GameDisplay.camera.offset.y, xScale, yScale, wrap, GameDisplay.MAX_WIDTH, GameDisplay.MAX_HEIGHT);
	}
	
	
	public static void staticDraw(Screen screen, String message, int sx, int sy, float xScale, float yScale, int wrapPoint, int maxHeight){
		draw(screen, message, sx + (int) GameDisplay.camera.offset.x, sy + (int) GameDisplay.camera.offset.y, xScale, yScale, true, wrapPoint, maxHeight);
	}
	
	public static void draw(Screen screen, String message, int sx, int sy){
		draw(screen, message, sx, sy, 1, 1, false, GameDisplay.MAX_WIDTH, GameDisplay.MAX_HEIGHT);
	}
	
	public static void draw(Screen screen, String message, int sx, int sy, float scale){
		draw(screen, message, sx, sy, scale, scale, false, GameDisplay.MAX_WIDTH, GameDisplay.MAX_HEIGHT);
	}
	
	public static void draw(Screen screen, String message, int sx, int sy, float xScale, float yScale, boolean wrap, int wrapPoint, int maxHeight){
		int x = sx;
		int y = sy;
		message = message.toUpperCase();
		int length = message.length();
		for(int i = 0; i < length; i++){
			if(message.charAt(i) == (',') || message.charAt(i) == ('\'') || message.charAt(i) == ('.') || message.charAt(i) == ('!') || message.charAt(i) == ('?') || message.charAt(i) == ('/') || message.charAt(i) == (':') || message.charAt(i) == ('(') || message.charAt(i) == (')')){
				int c = symbols.indexOf(message.charAt(i));
				if(c < 0) continue;
				Sprite symbol_ =  new Sprite(symbol, 5 * c, 0, 5, 16);
				if(color != 0){
					symbol_.changeColor(0xffffffff, color);	
				}
				screen.render(symbol_, new Vector2f(x, y), xScale, yScale);
				x += (int) (SYMBOL_SIZE * xScale);
			} else {
				int c = letters.indexOf(message.charAt(i));
				if(c < 0) continue;
				Sprite letter =  new Sprite(font, 11 * c, 0, 11, 16);
				if(color != 0){
					letter.changeColor(0xffffffff, color);	
				}
				screen.render(letter, new Vector2f(x, y), xScale, yScale);
				x += (int) (FONT_SIZE * xScale);
			}
			if(wrap){
				if((wrapPoint == GameDisplay.MAX_WIDTH && x - (int) GameDisplay.camera.offset.x > GameDisplay.MAX_WIDTH / GameDisplay.SCALE - 11) || (wrapPoint != GameDisplay.MAX_WIDTH && x - (int) GameDisplay.camera.offset.x > wrapPoint)){					
					if(sxAlign){
						x = sx;
					} else {
						x = 3 + (int) GameDisplay.camera.offset.x;					
					}				
					y += (int) (16 * yScale);
					if(y - sy > maxHeight){
						return;
					}
				}
			}
		}
	}
	
	public static void setColor(int color_){
		color = color_;
	}
	
	public static void setAlign(boolean align){
		sxAlign = align;
	}
}
