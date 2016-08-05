package userInput;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import display.GameDisplay;

public class BSKeyboard implements KeyListener{

	public List<Integer> currentPressedKeys;
	private boolean checking;
	
	
	public BSKeyboard(GameDisplay frame){
		frame.addKeyListener(this);
		checking = false;
		currentPressedKeys = new ArrayList<Integer>();
	}

	public void keyPressed(KeyEvent event) {
		while(checking){
			
		}
		if(!isKeyPressed(event.getKeyCode())){
			currentPressedKeys.add(event.getKeyCode());
		}
	}

	public void keyReleased(KeyEvent event) {
		while(checking){
			
		}
		if(isKeyPressed(event.getKeyCode())){
			currentPressedKeys.remove(new Integer(event.getKeyCode()));
		}
	}

	public void keyTyped(KeyEvent event) {
		
	}
	
	public synchronized boolean isKeyPressed(int keyCode){
		checking = true;
		boolean pressed = false;
		for(int code: currentPressedKeys){
			if(code == keyCode) pressed = true;
		}
		checking = false;
		return pressed;
	}
}
