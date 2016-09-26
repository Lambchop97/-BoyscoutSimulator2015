package world.generator;

import java.util.Random;

public class PerlinNoise{
	
	//Seeds for generating the data for the specified tiles, There are 5 so there can be 5 separate noises
	private static int seed1 = 15731;
	private static int seed2 = 1234537;
	private static int seed3 = 532183;
	private static int seed4 = 2451359;
	private static int seed5 = 7526443;
	
	// Five random number generators that use seeds, the 5 seeds from above
	private static float terrainRandom1(int x){
		x = (x << 13) ^ x;
		return (1.0f - ((x * (x * x * seed1 + 789221) + 1376312589) & 0x7FFFFFFF) / 2147483647.0f);
	}
	
	private static float terrainRandom2(int x){
		x = (x << 13) ^ x;
		return (1.0f - ((x * (x * x * seed2 + 789221) + 1376312589) & 0x7FFFFFFF) / 2147483647.0f);
	}
	
	private static float terrainRandom3(int x){
		x = (x << 13) ^ x;
		return (1.0f - ((x * (x * x * seed3 + 789221) + 1376312589) & 0x7FFFFFFF) / 2147483647.0f);
	}
	
	private static float terrainRandom4(int x){
		x = (x << 13) ^ x;
		return (1.0f - ((x * (x * x * seed4 + 789221) + 1376312589) & 0x7FFFFFFF) / 2147483647.0f);
	}
	
	private static float terrainRandom5(int x){
		x = (x << 13) ^ x;
		return (1.0f - ((x * (x * x * seed5 + 789221) + 1376312589) & 0x7FFFFFFF) / 2147483647.0f);
	}
	
	
	private static float interpolate(float a, float b, float x){
		float ft = x * 3.1415926535f; // ft is the blend value times PI
		float f = (1.0f - (float) Math.cos(ft)) * .5f; // f is a value between .5 and 1.0 
		
		return (a * (1 - f) + b * f);
	}
	
	private static float generateSmoothNoise1(int x, int y, int octave){
		float smoothNoise = 0.0f; 
		int period = 1 << octave; 
		float frequency = 1.0f / period;
		
		int sample0 = (x / period) * period;
		int sample1 = (sample0 + period);
		float blend0 = (x - sample0) * frequency;
		
		int sample2 = (y / period) * period;
		int sample3 = (sample2 + period);
		float blend1 = (y - sample2) * frequency;
		
		float topBlend = interpolate(terrainRandom1(sample0 * 50 + sample2), terrainRandom1(sample1 * 50 + sample2), blend0);
		float bottomBlend = interpolate(terrainRandom1(sample0 * 50 + sample3), terrainRandom1(sample1 * 50 + sample3), blend0);
		
		smoothNoise = interpolate(topBlend, bottomBlend, blend1);
		
		return smoothNoise;
	}
	
	private static float generateSmoothNoise2(int x, int y, int octave){
		float smoothNoise = 0.0f;
		int period = 1 << octave;
		float frequency = 1.0f / period;
		
		int sample0 = (x / period) * period;
		int sample1 = (sample0 + period);
		float blend0 = (x - sample0) * frequency;
		
		int sample2 = (y / period) * period;
		int sample3 = (sample2 + period);
		float blend1 = (y - sample2) * frequency;
		
		float topBlend = interpolate(terrainRandom2(sample0 * 50 + sample2), terrainRandom2(sample1 * 50 + sample2), blend0);
		float bottomBlend = interpolate(terrainRandom2(sample0 * 50 + sample3), terrainRandom2(sample1 * 50 + sample3), blend0);
		
		smoothNoise = interpolate(topBlend, bottomBlend, blend1);
		
		return smoothNoise;
	}
	
	private static float generateSmoothNoise3(int x, int y, int octave){
		float smoothNoise = 0.0f;
		int period = 1 << octave;
		float frequency = 1.0f / period;
		
		int sample0 = (x / period) * period;
		int sample1 = (sample0 + period);
		float blend0 = (x - sample0) * frequency;
		
		int sample2 = (y / period) * period;
		int sample3 = (sample2 + period);
		float blend1 = (y - sample2) * frequency;
		
		float topBlend = interpolate(terrainRandom3(sample0 * 50 + sample2), terrainRandom3(sample1 * 50 + sample2), blend0);
		float bottomBlend = interpolate(terrainRandom3(sample0 * 50 + sample3), terrainRandom3(sample1 * 50 + sample3), blend0);
		
		smoothNoise = interpolate(topBlend, bottomBlend, blend1);
		
		return smoothNoise;
	}
	
	private static float generateSmoothNoise4(int x, int y, int octave){
		float smoothNoise = 0.0f;
		int period = 1 << octave;
		float frequency = 1.0f / period;
		
		int sample0 = (x / period) * period;
		int sample1 = (sample0 + period);
		float blend0 = (x - sample0) * frequency;
		
		int sample2 = (y / period) * period;
		int sample3 = (sample2 + period);
		float blend1 = (y - sample2) * frequency;
		
		float topBlend = interpolate(terrainRandom4(sample0 * 50 + sample2), terrainRandom4(sample1 * 50 + sample2), blend0);
		float bottomBlend = interpolate(terrainRandom4(sample0 * 50 + sample3), terrainRandom4(sample1 * 50 + sample3), blend0);
		
		smoothNoise = interpolate(topBlend, bottomBlend, blend1);
		
		return smoothNoise;
	}
	
	private static float generateSmoothNoise5(int x, int y, int octave){
		float smoothNoise = 0.0f;
		int period = 1 << octave;
		float frequency = 1.0f / period;
		
		int sample0 = (x / period) * period;
		int sample1 = (sample0 + period);
		float blend0 = (x - sample0) * frequency;
		
		int sample2 = (y / period) * period;
		int sample3 = (sample2 + period);
		float blend1 = (y - sample2) * frequency;
		
		float topBlend = interpolate(terrainRandom5(sample0 * 50 + sample2), terrainRandom5(sample1 * 50 + sample2), blend0);
		float bottomBlend = interpolate(terrainRandom5(sample0 * 50 + sample3), terrainRandom5(sample1 * 50 + sample3), blend0);
		
		smoothNoise = interpolate(topBlend, bottomBlend, blend1);
		
		return smoothNoise;
	}
	
	private static float[][] generateSmoothNoise(float[][] whiteNoise, int octave){
		int width = whiteNoise.length;
		int height = whiteNoise[0].length;
		
		float[][] smoothNoise = new float[width][height];
		
		int period = 1 << octave;
		float frequency = 1.0f / period;
		
		for(int i = 0; i < width; i++){
			int sample0 = (i / period) * period;
			int sample1 = (sample0 + period) % width;
			float blend0 = (i - sample0) * frequency;
			
			for(int k = 0; k < height; k++){
				int sample2 = (k / period) * period;
				int sample3 = (sample2 + period) % height;
				float blend1 = (k - sample2) * frequency;		
				
				float topBlend = interpolate(whiteNoise[sample0][sample2], whiteNoise[sample1][sample2], blend0);
				float bottomBlend = interpolate(whiteNoise[sample0][sample3], whiteNoise[sample1][sample3], blend0);
				
				smoothNoise[i][k] = interpolate(topBlend, bottomBlend, blend1);
			}
		}
		return smoothNoise;
	}
	
	
	public static float generatePerlinNoise1(int x, int y, int octaves){
		x += Integer.MAX_VALUE/100000;
		y += Integer.MAX_VALUE/100000;
		float[] smoothNoise = new float[octaves];
		
		float persistance = 0.5f;
		
		for(int i = 0; i < octaves; i++){
			smoothNoise[i] = generateSmoothNoise1(x, y, i);
		}
		
		float perlinNoise = 0f;
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		
		for(int o = octaves - 1; o > 0; o--){
			amplitude *= persistance;
			totalAmplitude += amplitude;
			perlinNoise += smoothNoise[o] * amplitude;
		}
		
		return perlinNoise / totalAmplitude;
	}
	
	public static float generatePerlinNoise2(int x, int y, int octaves){
		x += Integer.MAX_VALUE/100000;
		y += Integer.MAX_VALUE/100000;
		float[] smoothNoise = new float[octaves];
		
		float persistance = 0.5f;
		
		for(int i = 0; i < octaves; i++){
			smoothNoise[i] = generateSmoothNoise2(x, y, i);
		}
		
		float perlinNoise = 0f;
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		
		for(int o = octaves - 1; o > 0; o--){
			amplitude *= persistance;
			totalAmplitude += amplitude;
			perlinNoise += smoothNoise[o] * amplitude;
		}
		
		return perlinNoise / totalAmplitude;
	}
	
	public static float generatePerlinNoise3(int x, int y, int octaves){
		x += Integer.MAX_VALUE/100000;
		y += Integer.MAX_VALUE/100000;
		float[] smoothNoise = new float[octaves];
		
		float persistance = 0.5f;
		
		for(int i = 0; i < octaves; i++){
			smoothNoise[i] = generateSmoothNoise3(x, y, i);
		}
		
		float perlinNoise = 0f;
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		
		for(int o = octaves - 1; o > 0; o--){
			amplitude *= persistance;
			totalAmplitude += amplitude;
			perlinNoise += smoothNoise[o] * amplitude;
		}
		
		return perlinNoise / totalAmplitude;
	}
	
	public static float generatePerlinNoise4(int x, int y, int octaves){
		x += Integer.MAX_VALUE/100000;
		y += Integer.MAX_VALUE/100000;
		float[] smoothNoise = new float[octaves];
		
		float persistance = 0.5f;
		
		for(int i = 0; i < octaves; i++){
			smoothNoise[i] = generateSmoothNoise4(x, y, i);
		}
		
		float perlinNoise = 0f;
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		
		for(int o = octaves - 1; o > 0; o--){
			amplitude *= persistance;
			totalAmplitude += amplitude;
			perlinNoise += smoothNoise[o] * amplitude;
		}
		
		return perlinNoise / totalAmplitude;
	}
	
	public static float generatePerlinNoise5(int x, int y, int octaves){
		x += Integer.MAX_VALUE/100000;
		y += Integer.MAX_VALUE/100000;
		float[] smoothNoise = new float[octaves];
		
		float persistance = 0.5f;
		
		for(int i = 0; i < octaves; i++){
			smoothNoise[i] = generateSmoothNoise5(x, y, i);
		}
		
		float perlinNoise = 0f;
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		
		for(int o = octaves - 1; o > 0; o--){
			amplitude *= persistance;
			totalAmplitude += amplitude;
			perlinNoise += smoothNoise[o] * amplitude;
		}
		
		return perlinNoise / totalAmplitude;
	}
	
	public static float[][] generatePerlinNoise(int width, int height, int octaves){
		float[][] whiteNoise = new float[width][height];
		for(int i = 0; i < whiteNoise.length; i++){
			for(int k = 0; k < whiteNoise[i].length; k++){
				float value = terrainRandom1(i * width + k);
				whiteNoise[i][k] = value;
			}
		}
		
		float[][][] smoothNoise = new float[octaves][][];
		
		float persistance = 0.5f;
		
		for(int i = 0; i < octaves; i++){
			smoothNoise[i] = generateSmoothNoise(whiteNoise, i);
		}
		
		float[][] perlinNoise = new float[width][height];
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;
		
		for(int o = octaves - 1; o > 0; o--){
			amplitude *= persistance;
			totalAmplitude += amplitude;
			
			for(int i = 0; i < width; i++){
				for(int k = 0; k < height; k++){
					perlinNoise[i][k] += smoothNoise[o][i][k] * amplitude;
				}
			}
		}
		
		for(int i = 0; i < width; i++){
			for(int k = 0; k < height; k++){
				perlinNoise[i][k] /= totalAmplitude;
			}
		}
		
		return perlinNoise;
	}
	
//	public Tile generateTile(int x, int y){
//		return null;
//	}
	
	public static void seed(long seed){
		Random r = new Random(seed);
		seed1 = r.nextInt();
		seed2 = r.nextInt();
		seed3 = r.nextInt();
		seed4 = r.nextInt();
		seed5 = r.nextInt();
		System.out.println(seed2 + ", " + seed3 + ", " + seed4 + ", " + seed5);
	}
}
