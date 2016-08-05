package display;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import debug.DebugDisplay;
import graphics.Sprite;
import utility.Vector2f;

/**
 * 
 * @author Joshua Kuennen
 * A class that is the instance of the screen in which the frames are rendered to.
 */
public class Screen {

	/**
	 * The Dimension Variables
	 */
	private int width, height;
	
	/**
	 * The image that stores the frame.
	 */
	public BufferedImage image;
	
	/**
	 * A reference to the pixels of image, used to draw to the screen.
	 */
	public int[] pixels;
	
	/**
	 * Constructor for the Screen. Takes in a width and height
	 * @param width the width of the screen
	 * @param height the height of the screen
	 */
	public Screen(int width, int height){
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	/**
	 * Fills the screen with a weird blueish greyish color
	 */
	public void clear(){
		for(int i = 0; i < width * height; i++){
			pixels[i] = 0xFF123456;
		}
	}
	
	/**
	 * Default render method. Calls the scaled version, dimension scales of 1 for both x and y.
	 * @param sprite the sprite to render to the screen
	 * @param position the position at which to render the sprite at
	 */
	public void render(Sprite sprite, Vector2f position){
		render(sprite, position, 1, 1);
	}
	
	/**
	 * Renders the sprite to the screen at a specified position with specified x and y scales.
	 * @param sprite the sprite to render to the screen
	 * @param position the position at which to render the sprite at
	 * @param xScale the x scale for how wide to render the sprite
	 * @param yScale the y scale for how tall to render the sprite
	 */
	public void render(Sprite sprite, Vector2f position, float xScale, float yScale){
		position.subtract(GameDisplay.camera.offset);
		
		DebugDisplay.renderCalls++;
		
		int width = sprite.width;
		int height = sprite.height;
		
		float newWidth = width * xScale;
		float newHeight = height * yScale;
		if(position.x > this.width || position.x + sprite.width < 0 || position.y > this.height || position.y + sprite.height < 0) {
			position.add(GameDisplay.camera.offset);
			return;
		}
		
		int xStart = (int) position.x;
		int xEnd = (int) (xStart + newWidth);
		if(xStart < 0) xStart = 0;
		if(xEnd > this.width) xEnd = this.width;
		int xDifference = (xStart - (int) position.x);
		
		int yStart = (int) position.y;
		int yEnd = (int) (yStart + newHeight);
		if(yStart < 0) yStart = 0;
		if(yEnd > this.height) yEnd = this.height;
		int yDifference = (yStart - (int) position.y);
		
		float spriteDeltaX = width / newWidth;
		float spriteIndexX = 0;
		
		float spriteDeltaY = height / newHeight;
		float spriteIndexY = 0;
		
		for(int y = yStart; y < yEnd; y++){
			for(int x = xStart; x < xEnd; x++){
				if((sprite.pixels[(yDifference + (int) spriteIndexY) * sprite.width + (xDifference + (int) spriteIndexX)] >> 24) == 0){
					spriteIndexX += spriteDeltaX;
					continue;
				}
				if(pixels[y * this.width + x] != 0xff123456){
					int col1 = sprite.pixels[(yDifference + (int) spriteIndexY) * sprite.width + (xDifference + (int) spriteIndexX)];
					int col2 = pixels[y * this.width + x];
					pixels[y * this.width + x] = alphaBlend(col1, col2);
				} else {					
					pixels[y * this.width + x] = sprite.pixels[(yDifference + (int) spriteIndexY) * sprite.width + (xDifference + (int) spriteIndexX)];
				}
				spriteIndexX += spriteDeltaX;
			}
			spriteIndexX = 0;
			spriteIndexY += spriteDeltaY;
		}
		position.add(GameDisplay.camera.offset);
		DebugDisplay.renderFinishes++;
	}
	
	/**
	 * Helper method to alpha blend the two colors together.
	 * @param c1 the top color to blend 
	 * @param c2 the botom color to blend
	 * @return col the blended colors col1 and col2
	 */
	public int alphaBlend(int c1, int c2) {
	    int a1 = (c1 & 0xff000000) >>> 24;
	    //int a2 = (c2 & 0xff000000) >>> 24; // Do not need for traditional alpha blending
		if(a1 == 0)
			return c2;

	    int r1 = (c1 & 0x00ff0000) >> 16;
	    int r2 = (c2 & 0x00ff0000) >> 16;

	    int g1 = (c1 & 0x0000ff00) >> 8;
	    int g2 = (c2 & 0x0000ff00) >> 8;

	    int b1 = (c1 & 0x000000ff);
	    int b2 = (c2 & 0x000000ff);

	    float src_alpha = ((float)a1) / 255.0f;

	    int red   = (int) ((r1 * src_alpha) + (r2 * (1.0f - src_alpha)));
	    int green = (int) ((g1 * src_alpha) + (g2 * (1.0f - src_alpha)));
	    int blue  = (int) ((b1 * src_alpha) + (b2 * (1.0f - src_alpha)));

	    return (a1 << 24) | (red << 16) | (green << 8) | blue;
	}
}
