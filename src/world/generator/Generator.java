package world.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import display.GameDisplay;
import thread.ThreadManager;
import world.Chunk;
import world.Tile;

public class Generator implements Runnable{

	private List<Chunk> chunks;
	
	public Generator(){
		chunks = new ArrayList<Chunk>();
		ThreadManager.terrainGeneration = new Thread(this);
		ThreadManager.terrainGeneration.start();
	}
	
	public void run(){
		int cps = 20;
		double nsPerCheck = 1000000000.0d / cps;
		double then = System.nanoTime();
		double delta = 0;
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		while(GameDisplay.instance().isRunning()){
			double now = System.nanoTime();
			delta += (now - then) / nsPerCheck;
			if(delta > 1){
				if(chunks.size() > 0){
					for(int i = 0; i < chunks.size(); i++){
						Tile[][] chunk = generateChunk(chunks.get(i).chunkX, chunks.get(i).chunkY);
						chunks.get(i).setTiles(chunk);
					}
					chunks.clear();
				}
				delta = 0;
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized void requestChunk(Chunk chunk){
		chunks.add(chunk);
	}
	
	public static Tile[][] generateChunk(int x, int y){
		Tile[][] tiles = new Tile[Chunk.TILES_IN_CHUNK][Chunk.TILES_IN_CHUNK];
		for(int i = 0; i < Chunk.TILES_IN_CHUNK; i++){
			for(int k = 0; k < Chunk.TILES_IN_CHUNK; k++){
				float land = PerlinNoise.generatePerlinNoise1(x * Chunk.TILES_IN_CHUNK + i, y * Chunk.TILES_IN_CHUNK + k, 8);
				float temperature = PerlinNoise.generatePerlinNoise2(x * Chunk.TILES_IN_CHUNK + i, y * Chunk.TILES_IN_CHUNK + k, 8);
				float rainfall = PerlinNoise.generatePerlinNoise3(x * Chunk.TILES_IN_CHUNK + i, y * Chunk.TILES_IN_CHUNK + k, 8);
				if(land >= .5f){
					if(temperature < .3f){
						if(rainfall < .5){
							tiles[i][k] = Tile.tiles[3];
						} else {
							tiles[i][k] = Tile.tiles[4];
						}
					} else if(temperature < .7f){
						if(rainfall < .3f){
							tiles[i][k] = Tile.tiles[0];
						} else if(rainfall < .5f){
							tiles[i][k] = Tile.tiles[9];
						} else if(rainfall < .7f){
							tiles[i][k] = Tile.tiles[6];
						} else {
							tiles[i][k] = Tile.tiles[7];
						}
					} else {
						if(rainfall < .3f){
							tiles[i][k] = Tile.tiles[1];
						} else if(rainfall < .5f){
							tiles[i][k] = Tile.tiles[5];
						} else {
							tiles[i][k] = Tile.tiles[8];
						}
					}
				} else {
					tiles[i][k] = Tile.tiles[2];
				}
			}
		}
		tiles = smoothBiomes(tiles, x * Chunk.TILES_IN_CHUNK, y * Chunk.TILES_IN_CHUNK);
		return tiles;
	}

	private static Tile[][] smoothBiomes(Tile[][] tiles, int x, int y) {
		Tile[][] newTiles = new Tile[tiles.length][tiles[0].length];
		int[] fxf = new int[Tile.tiles.length];
		int[] txt = new int[Tile.tiles.length];
		int i = 0; //xCoord of the tile we are checking
		int k = 0; //yCoord of the tile we are checking
		for(Tile[] a: tiles){
			for(Tile t: a){
				t.getClass();
				for(int n = i - 2; n < i + 3; n++){ // n is the xCoord of the tile in the 5x5 grid we are looking at
					for(int m = k - 2; m < k + 3; m++){ // m is the yCoord of the tile in the 5x5 grid we are looking at
						if(i == n && k == m) continue;
						if(n < 0 || n > Chunk.TILES_IN_CHUNK - 1 || m < 0 || m > Chunk.TILES_IN_CHUNK - 1){
							float land = PerlinNoise.generatePerlinNoise1(x + n, y + m, 8);
							float temperature = PerlinNoise.generatePerlinNoise2(x + n, y + m, 8);
							float rainfall = PerlinNoise.generatePerlinNoise3(x + n, y + m, 8);
							Tile tile = null;
							if(land >= .5f){
								if(temperature < .3f){
									if(rainfall < .5){
										tile = Tile.tiles[3];
									} else {
										tile = Tile.tiles[4];
									}
								} else if(temperature < .7f){
									if(rainfall < .3f){
										tile = Tile.tiles[0];
									} else if(rainfall < .5f){
										tile = Tile.tiles[9];
									} else if(rainfall < .7f){
										tile = Tile.tiles[6];
									} else {
										tile = Tile.tiles[7];
									}
								} else {
									if(rainfall < .3f){
										tile = Tile.tiles[1];
									} else if(rainfall < .5f){
										tile = Tile.tiles[5];
									} else {
										tile = Tile.tiles[8];
									}
								}
							} else {
								tile = Tile.tiles[2];
							}
							if(Math.abs(n - x - i) == 2 || Math.abs(m - y - k) == 2){
								fxf[tile.id]++;								
							} else if(Math.abs(n - x - i) != 0 || Math.abs(m - y - k) != 0){
								fxf[tile.id]++;	
								txt[tile.id]++;
							}
						} else {
							Tile tile = tiles[n][m];
							if(Math.abs(m - x - i) == 2 || Math.abs(n - y - k) == 2){
								fxf[tile.id]++;								
							} else if(Math.abs(m - x - i) != 0 || Math.abs(n - y - k) != 0){
								fxf[tile.id]++;	
								txt[tile.id]++;
							}
						}
					}
				}
				boolean done = false;
				for(int n = 0; n < fxf.length; n++){
					if((fxf[n] >= 17 && txt[n] >= 5 || fxf[tiles[i][k].id] < 4) && !Tile.tiles[n].equals(tiles[i][k])){
						newTiles[i][k] = Tile.tiles[n];
						if(fxf[tiles[i][k].id] < 4){
							int id = 0;
							int max = 0;
							for(int m = 0; m < fxf.length; m++){
								if(max < fxf[m]){
									max = fxf[m];
									id = m;
								}
							}
							newTiles[i][k] = Tile.tiles[id];
						}
						done = true;
						break;
					} 
				}
				if(!done){
					newTiles[i][k] = tiles[i][k];
				}
				k++;
				Arrays.fill(fxf, 0);
				Arrays.fill(txt, 0);
			}
			i++;
			k = 0;
		}
		return newTiles;
	}
}
