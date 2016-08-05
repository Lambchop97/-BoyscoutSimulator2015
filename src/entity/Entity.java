package entity;

import java.util.ArrayList;
import java.util.List;

import display.Screen;
import entitycomponent.Component;
import graphics.ArtManager;
import graphics.Sprite;
import userInput.BSInputHandler;
import utility.Vector2f;

public class Entity {

	protected Vector2f position;
	protected List<Component> components;
	protected int updates;
	protected Sprite sprite;
	protected int id;
	protected static int idCounter = 0;
	
	public Entity(Vector2f position){
		this.position = position;
		components = new ArrayList<Component>();
		updates = 0;
		this.sprite = new Sprite(ArtManager.terrainSpriteSheet, 32 * 4, 32 * 8, 32, 32);
		id = idCounter++;
	}
	
	public void render(Screen screen){
		for(Component c: components){
			c.render(screen, position);
		}
		if(!hasComponent("AnimationComponent")){
			screen.render(sprite, position);
		}
		//SpriteHelper.rotate(sprite, 1.23f);
	}
	
	public void tick(){
		for(Component c: components){
			c.update(this);
		}
	}
	
	public void update(BSInputHandler input){
		tick();
		updates++;
	}
	
	public Vector2f getPosition(){
		return position;
	}
	
	public List<Component> getComponents(){
		return components;
	}
	
	public int getUpdates(){
		return updates;
	}
	
	public void addComponent(Component comp){
		components.add(comp);
	}
	
	public boolean hasComponent(String name){
		boolean hasComp = false;
		for(Component c: components){
			if(c.getName().equals(name)) hasComp = true;
		}
		return hasComp;
	}
	
	public Component getComponent(String name){
		if(hasComponent(name)){
			for(Component c: components){
				if(c.getName().equals(name)) return c;
			}
		}
		return null;
	}
	
	public int getID(){
		return id;
	}
}
