package userInput;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class Control {
	private int keyCode;
	private String name;
	private boolean pressed;
	public static List<Control> controls = new ArrayList<Control>();
	
	public Control(int code, String name){
		keyCode = code;
		this.name = name;
		pressed = false;
		controls.add(this);
	}
	
	public static void init(){
		new Control(KeyEvent.VK_W, "Up");
		new Control(KeyEvent.VK_S, "Down");
		new Control(KeyEvent.VK_A, "Left");
		new Control(KeyEvent.VK_D, "Right");
	}
	
	public static void update(BSInputHandler input){
		for(Control c: controls){
			if(input.keyboard.isKeyPressed(c.keyCode)){
				c.pressed = true;
			} else {
				c.pressed = false;
			}
		}
	}
	
	public boolean isPressed(){
		return pressed;
	}
	
	public String getName(){
		return name;
	}
	
	public static Control getControlFromName(String name){
		Control cont = null;
		for(Control c: controls){
			if(c.getName().matches(name)){
				cont = c;
				break;
			}
		}
		if(cont == null) try {
			throw new NonExistantControlException("Control " + name + " doesn't exist");
		} catch (NonExistantControlException e) {
			e.printStackTrace();
		}
		return cont; 
	}
	
	private static class NonExistantControlException extends Exception{

		/**
		 * swag 
		 */
		private static final long serialVersionUID = 5888957739045965193L;

		public NonExistantControlException(String message) {
			super(message);
		}
		
	}
}
