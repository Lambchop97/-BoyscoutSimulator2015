package world;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import display.GameDisplay;
import display.Screen;

public class Region {
	
	private Chunk[][] chunks;
	public int regionX, regionY;
	private List<Chunk> chunksToRender;
	private List<Chunk> chunksToRemove;
	
	public boolean loaded;
	
	public static final int CHUNKS_IN_REGION = 8;
	
	public Region(int x, int y){
		chunks = new Chunk[CHUNKS_IN_REGION][CHUNKS_IN_REGION];
		regionX = x;
		regionY = y;
		for(int i = 0; i < chunks.length; i++){
			for(int k = 0; k < chunks[i].length; k++){
				chunks[i][k] = new Chunk(i + regionX * CHUNKS_IN_REGION, k + regionY * CHUNKS_IN_REGION, this);
			}
		}
		chunksToRender = new ArrayList<Chunk>();
		chunksToRemove = new ArrayList<Chunk>();
		loaded = false;
	}
	
	public void render(Screen screen){
		for(Chunk c: chunksToRender){
			c.render(screen);
		}
	}
	
	public void update(){
		for(Chunk[] ca: chunks){
			for(Chunk c: ca){
				if(c.onScreen()){
					add(c);
				} else {
					chunksToRemove.add(c);
				}
			}
		}
		for(Chunk c: chunksToRemove){
			chunksToRender.remove(c);
		}
		chunksToRemove.clear();
		
	}
	
	public boolean onScreen() {
		int x = (int) GameDisplay.camera.offset.x;
		int y = (int) GameDisplay.camera.offset.y;
		Rectangle r1 = new Rectangle(x, y, GameDisplay.MAX_WIDTH, GameDisplay.MAX_HEIGHT);
		Rectangle r2 = new Rectangle(regionX * CHUNKS_IN_REGION * Chunk.TILES_IN_CHUNK * Tile.SIZE, regionY * CHUNKS_IN_REGION * Chunk.TILES_IN_CHUNK * Tile.SIZE, CHUNKS_IN_REGION * Chunk.TILES_IN_CHUNK * Tile.SIZE, CHUNKS_IN_REGION * Chunk.TILES_IN_CHUNK * Tile.SIZE);
		return r1.intersects(r2);
	}
	
	private void add(Chunk chunk){
		for(Chunk c: chunksToRender){
			if(c.equals(chunk)) return;
		}
		chunksToRender.add(chunk);
	}
}
