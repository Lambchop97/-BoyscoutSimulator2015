package world.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import display.GameDisplay;
import thread.ThreadManager;
import world.Chunk;
import world.Tile;

/**
 * A class that creates a chunks worth of procedurally generated land based on perlin noise.
 * Carried out in its own thread.
 * @author Josh
 */
public class Generator implements Runnable{

	/**
	 * An ArrayList of the requested chunks to be generated. 
	 */
	private List<Chunk> chunks;
	
	/**
	 * Default constructor for Generator. Initializes the thread and chunks.
	 */
	public Generator(){
		chunks = new ArrayList<Chunk>();
		ThreadManager.terrainGeneration = new Thread(this);
		ThreadManager.terrainGeneration.start();
	}
	
	/**
	 * Inherited from Runnable. Has a time based loop for checking for chunk generation requests.
	 */
	public void run(){
		int cps = 20; // Checks per second.
		double nsPerCheck = 1000000000.0d / cps; // Nano-seconds between each check.
		double then = System.nanoTime(); // Nano-second value of the time of the last check.
		double delta = 0; // A double value of how close the next check is, greater then 1 means that a check must happen.
		
		//Sleep for 1 second, why? I don't know.
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		// A loop that keeps checking if there are generation requests as long as the game is running.
		while(GameDisplay.instance().isRunning()){
			double now = System.nanoTime(); // Current nano-time.
			delta += (now - then) / nsPerCheck; // Calculates and adds to the delta.
			
			// If delta is greater then 1, check for requests.
			if(delta > 1){  
				// If there are requests, carry them out.
				if(chunks.size() > 0){
					// Loop through each request this check.
					for(int i = 0; i < chunks.size(); i++){
						Tile[][] chunk = generateChunk(chunks.get(i).chunkX, chunks.get(i).chunkY); // Generate a the data of the specified chunk.
						chunks.get(i).setTiles(chunk); // Set the tile data of the specified chunk to the newly generated data.
					}
					chunks.clear(); // Resets the chunks so we don't generate them next check.
				}
				delta = 0; // Set the delta to zero to await the next check.
				
				// Sleep for 10 milliseconds so we don't spend a lot of resources on calculating the delta.
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Add a request for a new chunk (chunk) to be generated.
	 * @param chunk The chunk to be generated
	 */
	public synchronized void requestChunk(Chunk chunk){
		chunks.add(chunk);
	}
	
	/**
	 * 
	 * @param x The xCoord of the chunk to generate
	 * @param y The yCoord of the chunk to generate
	 * @return tiles The tile data of the chunk
	 */
	public static Tile[][] generateChunk(int x, int y){
		Tile[][] tiles = new Tile[Chunk.TILES_IN_CHUNK][Chunk.TILES_IN_CHUNK]; // The tile data for the chunk.
		
		// Loop through the rows of tiles in the chunk.
		for(int i = 0; i < Chunk.TILES_IN_CHUNK; i++){ // i is the yCoord of the tile we are generating.
			
			// Loop through the columns of tiles in the chunk.
			for(int k = 0; k < Chunk.TILES_IN_CHUNK; k++){ // k is the xCoord of the tile we are generating
				
				// Generate 3 numbers from 3 separate perlin noises, one to represent if the tile is land, one the tiles rainfall, and the last the tiles temperature. 
				float land = PerlinNoise.generatePerlinNoise1(x * Chunk.TILES_IN_CHUNK + i, y * Chunk.TILES_IN_CHUNK + k, 8);
				float temperature = PerlinNoise.generatePerlinNoise2(x * Chunk.TILES_IN_CHUNK + i, y * Chunk.TILES_IN_CHUNK + k, 8);
				float rainfall = PerlinNoise.generatePerlinNoise3(x * Chunk.TILES_IN_CHUNK + i, y * Chunk.TILES_IN_CHUNK + k, 8);
				
				// Set the tile data to the proper id
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
		// Smooth the generated tile data.
		tiles = smoothBiomes(tiles, x * Chunk.TILES_IN_CHUNK, y * Chunk.TILES_IN_CHUNK);
		return tiles;
	}

	/**
	 * Takes the tile data of the current chunk and smoothes out any "odd" tile generations
	 * @param tiles The chunks current tile data that we will smooth out
	 * @param x The xCoord of the first tile in the chunk
	 * @param y The yCoord of the first tile in the chunk
	 * @return newTiles The smoothed version of the chunks tile data
	 */
	private static Tile[][] smoothBiomes(Tile[][] tiles, int x, int y) {
		Tile[][] newTiles = new Tile[tiles.length][tiles[0].length]; // A 2d array that keeps track of which tiles change and don't change .
		int[] fxf = new int[Tile.tiles.length]; // An array that keeps track of how many of each king of tile is within a 5x5 grid of the target tile.
		int[] txt = new int[Tile.tiles.length]; // An array that keeps track of how many of each kind of tile is within a 3x3 grid of the target tile.
		int i = 0; // xCoord of the tile we are checking.
		int k = 0; // yCoord of the tile we are checking.
		
		// Loop through every row in the 2d tile array tiles.
		for(Tile[] a: tiles){
			
			// Loop through every column in the current row, a, in the tile array tiles.
			for(Tile t: a){
				// No idea why this is here.
				t.getClass();
				
				// Loop through the 5x5 grid surrounding the target tile at (i,k) in the chunk.
				for(int n = i - 2; n < i + 3; n++){ // n is the xCoord of the tile in the 5x5 grid we are looking at.
					for(int m = k - 2; m < k + 3; m++){ // m is the yCoord of the tile in the 5x5 grid we are looking at.
						
						// Skip the tile we are checking.
						if(i == n && k == m) continue;
						
						// Check if the tile (n,m) is outside of the chunk.
						if(n < 0 || n > Chunk.TILES_IN_CHUNK - 1 || m < 0 || m > Chunk.TILES_IN_CHUNK - 1){
							// If the tile (n,m) is outside of the chunk, we need to figure out what the tile is.
							//TODO: Make a way to check if the tile is in an already loaded chunk and get its id from there rather then generating the tile then throwing it away.
							//Generates the tile id using the same algorithm as generateChunk().
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
							
							// Check if the tile is in the 5x5 and the 3x3 grids, and add 1 to the id of the tile in the correct tracking array.
							if(Math.abs(n - x - i) == 2 || Math.abs(m - y - k) == 2){
								fxf[tile.id]++;								
							} else if(Math.abs(n - x - i) != 0 || Math.abs(m - y - k) != 0){
								fxf[tile.id]++;	
								txt[tile.id]++;
							}
						} else {
							// If the tile (n,m) is inside the chunk, we can just look it up in tiles.
							Tile tile = tiles[n][m];
							// Check if the tile is in the 5x5 and the 3x3 grids, and add 1 to the id of the tile in the correct tracking array.
							//TODO: possible "optimization" here with repeated code.
							if(Math.abs(m - x - i) == 2 || Math.abs(n - y - k) == 2){
								fxf[tile.id]++;								
							} else if(Math.abs(m - x - i) != 0 || Math.abs(n - y - k) != 0){
								fxf[tile.id]++;	
								txt[tile.id]++;
							}
						}
					}
				}
				// Find what the tile with the most within the 5x5 and 3x3 grids.
				boolean done = false; // Used so we don't overwrite the new tile with the old tile .
				
				// Loop through each tile id in the 5x5 array.
				for(int n = 0; n < fxf.length; n++){ // n is the id that we are looking at.
					
					// If the nth id has 17 or more tiles in the 5x5 grid, and 5 or more in the 3x3 grid,.
					// or if the tile already there has less then for in the 5x5 grid, switch it to the nth id.
					if((fxf[n] >= 17 && txt[n] >= 5 || fxf[tiles[i][k].id] < 4) && !Tile.tiles[n].equals(tiles[i][k])){
						// Set the old tile to the new tile.
						newTiles[i][k] = Tile.tiles[n];
						
						// If the old tile only has 4 other similar id tiles in the 5x5 grid, switch it to the tile with the most presence in the 5x5 grid.
						if(fxf[tiles[i][k].id] < 4){
							int id = 0; // id keeps track of the highest found tile so far.
							int max = 0; // max is the highest number of tiles in the 5x5 grid found so far.
							
							// Loop through all the tiles to see which one has the most presence.
							for(int m = 0; m < fxf.length; m++){ // m keeps track of which tile id we are checking.
								
								// If the number of tiles of the current tile id that we are checking is greater then max  .
								if(max < fxf[m]){
									max = fxf[m]; // Set max to the new higher number.
									id = m; // Set id to the id of the new highest tile.
								}
							}
							
							// Set the tile to the tile with the highest number of tiles in the 5x5 grid.
							newTiles[i][k] = Tile.tiles[id];
						}
						done = true; // Set done to true.
						break;
					} 
				}
				// If no new tile was made, keep the old tile.
				if(!done){
					newTiles[i][k] = tiles[i][k]; // Insert the old tile.
				}
				k++; // Iterate the yCoord of the tile we are checking.
				
				// Reset the 5x5 grid and 3x3 grid trackers.
				Arrays.fill(fxf, 0);
				Arrays.fill(txt, 0);
			}
			i++; // Iterate the xCoord of the tile we are checking.
			k = 0; // Reset the yCoord.
		}
		return newTiles;
	}
}

//   https://www.youtube.com/watch?v=CyDIGBvcbew
