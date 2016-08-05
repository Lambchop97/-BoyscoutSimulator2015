package graphics;

import debug.Logger;

public class ArtManager {
	public static SpriteSheet terrainSpriteSheet;
	public static SpriteSheet fontSpriteSheet;
	public static SpriteSheet symbolsSpriteSheet;
	public static SpriteSheet scrollbarSpriteSheet;
	public static SpriteSheet bar6515SpriteSheet;
	public static SpriteSheet bar9815SpriteSheet;	
	public static SpriteSheet basePlayerGUISpriteSheet;
	public static SpriteSheet responseBoxSpriteSheet;
	
	public static void init(){
		ArtManager.terrainSpriteSheet = new SpriteSheet("/Sprite_Sheet.png");
		ArtManager.fontSpriteSheet = new SpriteSheet("/Font.png");
		ArtManager.symbolsSpriteSheet = new SpriteSheet("/Symbols.png");
		ArtManager.scrollbarSpriteSheet = new SpriteSheet("/Scrollbar_Slider.png");
		ArtManager.bar6515SpriteSheet = new SpriteSheet("/65x15_Bar.png");
		ArtManager.bar9815SpriteSheet = new SpriteSheet("/98x15_Bar.png");
		ArtManager.basePlayerGUISpriteSheet = new SpriteSheet("/Base_GUI_Player.png");
		ArtManager.responseBoxSpriteSheet = new SpriteSheet("/ResponseBox.png");
		Logger.log("Art initialized");
	}
	
	public static void setTerrainSpriteSheet(SpriteSheet terrainSpriteSheet){
		ArtManager.terrainSpriteSheet = terrainSpriteSheet;
	}
	
	public static void setFontSpriteSheet(SpriteSheet terrainSpriteSheet){
		ArtManager.fontSpriteSheet = terrainSpriteSheet;
	}
	
	public static void setSymbolsSpriteSheet(SpriteSheet terrainSpriteSheet){
		ArtManager.symbolsSpriteSheet = terrainSpriteSheet;
	}
	
}
