package userInput;


import display.GameDisplay;

public class BSInputHandler {

	public BSKeyboard keyboard;
	public BSMouse mouse;
	
	public static boolean mobSelect = false;
	public BSInputHandler(GameDisplay frame){
		keyboard = new BSKeyboard(frame);
		mouse = new BSMouse(frame);
	}
}
