package utility;

import graphics.Sprite;

public class SpriteHelper{
	
	public static int getRed(int color){
		return (color >> 16) & 0x000000FF;
 	}
	
	public static int getBlue(int color){
		return (color >> 8) & 0x000000FF;
	}

	public static int getGreen(int color){
		return color & 0x000000FF; 
	}

	public static int getAlpha(int color){
		return (color >> 24) & 0x000000FF;
	}
	
	public static int alphaBlend(int color1, int color2){
		int alpha1 = (color1 & 0xFF000000) >> 24;
		
		int red1 = (color1 & 0x00FF0000) >> 16;
		int red2 = (color2 & 0x00FF0000) >> 16;
	
		int green1 = (color1 & 0x0000FF00) >> 8;
		int green2 = (color2 & 0x0000FF00) >> 8;
		
		int blue1 = (color1 & 0x000000FF);
		int blue2 = (color2 & 0x000000FF);
		
		float alpha1Float = ((float) alpha1) / 255.0f;
		
		int red = (int) ((red1 * alpha1Float) + (red2 * (1.0f - alpha1Float)));
		int green = (int) ((green1 * alpha1Float) + (green2 * (1.0f - alpha1Float)));
		int blue = (int) ((blue1 * alpha1Float) + (blue2 * (1.0f - alpha1Float)));
		
		return (alpha1 << 24) | (red << 16) | (green << 8) | (blue);
	}
	
	public static Sprite rotate(Sprite sprite, float rotation){
		Sprite returnSprite = new Sprite(sprite.width, sprite.height);
		double angle = (double) rotation;
		
		int diagonal = (int) (Math.sqrt(sprite.width * sprite.width + sprite.height * sprite.height));
		int nW = (int) (diagonal * Math.cos(angle));
		int nH = (int) (diagonal * Math.sin(angle));
//		System.out.println("nW: " + nW + ", nH: " + nH);
		double nx_x = rot_x(-angle, 1.0, 0.0);
		double nx_y = rot_y(-angle, 1.0, 0.0);
		double ny_x = rot_x(-angle, 0.0, 1.0);
		double ny_y = rot_y(-angle, 0.0, 0.0);
		
		double x0 = rot_x(-angle, -sprite.width / 2.0, -sprite.height / 2.0) + sprite.width / 2.0;
		double y0 = rot_y(-angle, -sprite.width / 2.0, -sprite.height / 2.0) + sprite.height / 2.0;
		
		
		return returnSprite;
	}
	
	private static double rot_x(double angle, double x, double y){
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		return x * cos + y * -sin;
	}
	
	private static double rot_y(double angle, double x, double y){
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		return x * sin + y * cos;
	}
}
