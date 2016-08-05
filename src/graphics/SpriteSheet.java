package graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import debug.Logger;
import display.GameDisplay;

public class SpriteSheet {

	private BufferedImage image;
	private int width, height;
	int[] pixels;
	
	public SpriteSheet(String path){
		try {
			image = ImageIO.read(GameDisplay.class.getResourceAsStream(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			Logger.log("Loaded SpriteSheet @ " + path);
			image.getRGB(0, 0, width, height, pixels, 0, width);
		} catch (IOException e) {
			//Logger.log(e.toString() + Thread.currentThread().getStackTrace()[2].getLineNumber());
			//e.printStackTrace();
		}
		width = image.getWidth();
		height = image.getHeight();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int[] getPixels() {
		return pixels;
	}
	
	public int[] getSprite(int xPos, int yPos, int width, int height){
		int[] spritePixels = new int[width * height];
		for(int i = yPos; i < yPos + height; i++){
			for(int k = xPos; k < xPos + width; k++){
				spritePixels[(i - yPos) * width + (k - xPos)] = pixels[i * this.width + k];
			}
		}
		return spritePixels;
	}
}
