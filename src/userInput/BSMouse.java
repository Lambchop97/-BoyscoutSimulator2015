package userInput;


import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import display.GameDisplay;

public class BSMouse implements MouseListener, MouseWheelListener, MouseMotionListener{

	public boolean leftButton = false;
	public boolean rightButton = false;
	public int scrollPosition = 0;
	public int x = 0, y = 0;
	
	public BSMouse(GameDisplay frame){
		frame.addMouseListener(this);
		frame.addMouseWheelListener(this);
		frame.addMouseMotionListener(this);
	}
	
	public void mouseWheelMoved(MouseWheelEvent event) {
		scrollPosition -= event.getWheelRotation();
	}

	public void mouseClicked(MouseEvent event) {
		
	}

	public void mouseEntered(MouseEvent event) {
		
	}

	public void mouseExited(MouseEvent event) {
		
	}

	public void mousePressed(MouseEvent event) {
		if(event.getButton() == 1){
			leftButton = true;
		}
		if(event.getButton() == 3){
			rightButton = true;
		}
	}

	public void mouseReleased(MouseEvent event) {
		if(event.getButton() == 1){
			leftButton = false;		}
		if(event.getButton() == 3){
			rightButton = false;		}
	}

	public void mouseDragged(MouseEvent event) {
		mouseMoved(event);
	}

	public void mouseMoved(MouseEvent event) {
		x = event.getX();
		y = event.getY();
	}

}
