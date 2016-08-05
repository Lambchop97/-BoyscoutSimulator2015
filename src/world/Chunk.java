package world;

import java.awt.Rectangle;

import display.GameDisplay;
import display.Screen;
import graphics.Font;
import utility.Vector2f;

public class Chunk {
	private Tile[][] tiles;
	public int chunkX;
	public int chunkY;
	
	public Region regionIn;

	public static final int TILES_IN_CHUNK = 16;
	
	public Chunk(int x, int y, Region r){
		tiles = new Tile[TILES_IN_CHUNK][TILES_IN_CHUNK];
		chunkX = x;
		chunkY = y;
		regionIn = r;
		
		GameDisplay.generator.requestChunk(this);
	}
	
	public synchronized void render(Screen screen){
		for(int i = 0; i < tiles.length; i++){
			for(int k = 0; k < tiles[i].length; k++){
				if(tiles[i][k] == null) {
					continue;
				}
				tiles[i][k].render(screen, new Vector2f(chunkX * Tile.SIZE * TILES_IN_CHUNK + i * Tile.SIZE, chunkY * Tile.SIZE * TILES_IN_CHUNK + k * Tile.SIZE));
			}
		}
		Font.setColor(0xff000000);
		Font.draw(screen, chunkX + ", " + chunkY, chunkX * Tile.SIZE * TILES_IN_CHUNK, chunkY * Tile.SIZE * TILES_IN_CHUNK);
	}
	
	public synchronized void setTiles(Tile[][] tiles){
		this.tiles = tiles;
	}
	
	public boolean onScreen() {
		int x = (int) GameDisplay.camera.offset.x;
		int y = (int) GameDisplay.camera.offset.y;
		Rectangle r1 = new Rectangle(x, y, GameDisplay.MAX_WIDTH / GameDisplay.SCALE, GameDisplay.MAX_HEIGHT / GameDisplay.SCALE);
		Rectangle r2 = new Rectangle(chunkX * Chunk.TILES_IN_CHUNK * Tile.SIZE, chunkY * Chunk.TILES_IN_CHUNK * Tile.SIZE, Chunk.TILES_IN_CHUNK * Tile.SIZE, Chunk.TILES_IN_CHUNK * Tile.SIZE);
		return r1.intersects(r2);
	}
	
	public boolean equals(Object o){
		boolean val = false;
		if(o instanceof Chunk){
			Chunk c = (Chunk) o;
			if(c.chunkX == chunkX && c.chunkY == chunkY) val = true;
		} 
		return val;
	}
}


