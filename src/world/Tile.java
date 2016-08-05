package world;

import debug.Logger;
import display.Screen;
import graphics.ArtManager;
import graphics.Font;
import graphics.Sprite;
import utility.Vector2f;

public class Tile {
	public static final int SIZE = 32;
	
	public static final Tile[] tiles = new Tile[32];
	
	protected boolean solid;
	protected String name;
	
	protected Sprite[][] frames;
	
	public byte id;
	
	protected int currentFrame = 0, frameLength = 500;
	protected long frameTimer = System.currentTimeMillis();
	
	public Tile(int id, String name, boolean isSolid, String frames, int updateTime){
		this.id = (byte) id;
		this.name = name;
		this.solid = isSolid;
		String[] framelets = frames.split(">");
		this.frames = new Sprite[framelets.length][0];
		for(int i = 0; i < framelets.length; i++){
			String[] options = framelets[i].split("A");
			this.frames[i] = new Sprite[options.length];
			for(int k = 0; k < options.length; k++){
				String[] xyCoords = options[k].split(",");
				int frameX = Integer.parseInt(xyCoords[0]);
				int frameY = Integer.parseInt(xyCoords[1]);
				this.frames[i][k] = new Sprite(ArtManager.terrainSpriteSheet, SIZE * frameX, SIZE * frameY, SIZE, SIZE);				
			}
		}
		tiles[id] = this;
		frameLength = updateTime;
	}
	
	public static void init(){
		new Tile(0, "Grass", false, "0,0", 500);
		new Tile(1, "Sand", false, "0,2", 500);
		new Tile(2, "Water", false, "0,4>1,4>2,4>1,4", 250);
		new Tile(3, "Snow", false, "1,3", 500);
		new Tile(4, "Magma Stone", false, "2,0A3,0A4,0", 500);
		new Tile(5, "Stone Brick", false, "0,1", 500);
		new Tile(6, "Dark Stone Brick", false, "2,1", 500);
		new Tile(7, "Magma Brick", false, "4,1", 500);
		new Tile(8, "Path", false, "0,3", 500);
		new Tile(9, "Ice", false, "3,4", 500);
		Logger.log("Tile initialized");
	}
	
	public void render(Screen screen, Vector2f position){
		screen.render(frames[currentFrame][option((int) position.x + (int) position.y * 823)], position);
		if(frames[currentFrame].length > 1){
			Font.setColor(0xff00ff00);
			Font.draw(screen, "" + option((int) position.x + (int) position.y * 823), (int) (position.x) + 8, (int) (position.y) + 8);
		}
//		System.out.println((System.currentTimeMillis() + (offset((int) position.x / 32 + (int) position.y / 32 * 823)) % frameLength) - (frameTimer) + ", " + position.x + ", " + position.y);
		if((System.currentTimeMillis() + (offset((int) position.x + (int) position.y * 823)) % frameLength) - (frameTimer) > frameLength){
			if(currentFrame >= frames.length - 1){
				currentFrame = 0;
			} else {
				currentFrame++;
			}
			frameTimer += frameLength;
		}
	}
	
	public boolean isSolid(){
		return solid;
	}
	
	private static int offset(int x){
		x = (x << 13) ^ x;
		return ((x * (x * x * 1234537 + 789221) + 1376312589) & 0x7FFFFFFF);
	}
	
	private int option(int x){
		x = (x << 13) ^ x;
		return ((x * (x * x * 1234537 + 789221) + 1376312589) & 0x7FFFFFFF) / (2147483647 / frames[currentFrame].length);
	}
}
