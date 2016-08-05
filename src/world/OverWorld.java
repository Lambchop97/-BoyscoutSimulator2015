package world;

import java.util.ArrayList;
import java.util.List;

import display.GameDisplay;
import display.Screen;
import world.generator.PerlinNoise;

public class OverWorld {

	private List<Region> knownRegions;
	private List<Region> loadedRegions;
	private List<Region> regionsToUnload;
	
	public OverWorld(){
		PerlinNoise.seed(2135432);
		knownRegions = new ArrayList<Region>();
		loadedRegions = new ArrayList<Region>();
		regionsToUnload = new ArrayList<Region>();
		
		int x = (int) GameDisplay.camera.offset.x / Tile.SIZE / Chunk.TILES_IN_CHUNK / Region.CHUNKS_IN_REGION;
		int y = (int) GameDisplay.camera.offset.y / Tile.SIZE / Chunk.TILES_IN_CHUNK / Region.CHUNKS_IN_REGION;
		
		loadRegion(x, y);
	}
	
	public void render(Screen screen){
		for(Region r: loadedRegions){
			r.render(screen);
		}
	}
	
	private void loadRegion(int x, int y){
		for(Region r: loadedRegions){
			if(r.regionX == x && r.regionY == y){
				return;
			}
		}
		for(Region r: knownRegions){
			if(r.regionX == x && r.regionY == y){
				loadedRegions.add(r);
				r.loaded = true;
				return;
			}
		}
		
		createRegion(x, y);
		loadRegion(x, y);
	}
	
	public void createRegion(int x, int y){
		Region r = new Region(x, y);
		r.loaded = true;
		knownRegions.add(r);
	}
	
	public void update(){
		int x = (int) GameDisplay.camera.offset.x;
		int y = (int) GameDisplay.camera.offset.y;
		
		int regionX1 = (x - 768) / Tile.SIZE / Chunk.TILES_IN_CHUNK / Region.CHUNKS_IN_REGION;
		int regionX2 = (x + 768 + GameDisplay.MAX_WIDTH) / Tile.SIZE / Chunk.TILES_IN_CHUNK / Region.CHUNKS_IN_REGION;
		int regionY1 = (y - 768) / Tile.SIZE / Chunk.TILES_IN_CHUNK / Region.CHUNKS_IN_REGION;
		int regionY2 = (y + 768 + GameDisplay.MAX_HEIGHT) / Tile.SIZE / Chunk.TILES_IN_CHUNK / Region.CHUNKS_IN_REGION;
		
		if(x - 768 < 0){
			regionX1--;
		}
		if(y - 768 < 0){
			regionY1--;
		}
		if((x + 768 + GameDisplay.MAX_WIDTH) < 0){
			regionX2--;
		}
		if((y + 768 + GameDisplay.MAX_HEIGHT) < 0){
			regionY2--;
		}
		loadRegion(regionX1, regionY1);
		loadRegion(regionX2, regionY1);
		loadRegion(regionX1, regionY2);
		loadRegion(regionX2, regionY2);

		for(Region r: loadedRegions){
			if(!r.onScreen()){
				regionsToUnload.add(r);
			} else {
				//r.tick();
			}
		}
		for(Region r: regionsToUnload){
			loadedRegions.remove(r);
		}
		regionsToUnload.clear();
		
		for(Region r: loadedRegions){
			r.update();
		}
	}
}
