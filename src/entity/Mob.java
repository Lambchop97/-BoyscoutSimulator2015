package entity;

import java.awt.Rectangle;

import display.GameDisplay;
import userInput.BSInputHandler;
import utility.Vector2f;

public class Mob extends Entity{
	protected static Mob selectedMob = null; 
	protected boolean selected = false;
	public Mob(Vector2f position) {
		super(position);
	
	}
	
	public void update(BSInputHandler input){
		if(input.mouse.leftButton)
		if(updates % 6 == 0){
			if(input.mouse.leftButton && !selected){
				Rectangle mouse = new Rectangle(input.mouse.x / 3, input.mouse.y / 3, 1, 1);
				Rectangle ent = new Rectangle((int) ((position.x - GameDisplay.camera.offset.x) * ((float) GameDisplay.frame().getContentPane().getSize().width / (float) GameDisplay.MAX_WIDTH)), (int) ((position.y - GameDisplay.camera.offset.y) * ((float) GameDisplay.frame().getContentPane().getSize().height / (float) GameDisplay.MAX_HEIGHT)), (int) (32 * ((float) GameDisplay.frame().getContentPane().getSize().width / (float) GameDisplay.MAX_WIDTH)), (int) (32 * ((float) GameDisplay.frame().getContentPane().getSize().height / (float) GameDisplay.MAX_HEIGHT)));
				if( mouse.intersects(ent)){
					selected = true;
					if(selectedMob == null){
						selectedMob = this;
					} else {
						if(!selectedMob.equals(this)){
							selectedMob.selected = false;
							selectedMob = this;
						}
					}
					System.out.println("Clicked on: " + this.getClass().toString() + " with and ID of:" + id);				
				}
			}
			if(!input.mouse.leftButton && selected){
				selected = false;
			}			
		}
		super.update(input);
	}
}
