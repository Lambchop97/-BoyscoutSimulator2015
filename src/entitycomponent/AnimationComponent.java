package entitycomponent;

import java.util.List;

import debug.Logger;
import display.Screen;
import graphics.Animation;
import utility.Vector2f;

public class AnimationComponent implements Component{

	public List<Animation> animations;
	private Animation currentAnimation;
	
	public AnimationComponent(List<Animation> animations){
		this.animations = animations;
		currentAnimation = animations.get(0);
	}
	public void update(Object o) {
		update();
	}
	
	public boolean equals(Object o){
		if(o instanceof AnimationComponent) return true;
		return false;
	}

	public void update() {
		currentAnimation.update();
	}

	public void render(Screen screen, Vector2f position) {
		if(currentAnimation == null) return;
		currentAnimation.render(screen, position);
	}
	
	public Animation getAnimationFromName(String name){
		Animation ani = null;
		for(Animation a: animations){
			if(a.getName().matches(name)){
				ani = a;
			}
		}
		return ani;
	}
	
	public String getName(){
		return "AnimationComponent";
	}
	
	public void setAnimation(String name){
		Animation ani = getAnimationFromName(name);
		if(ani == null){
			Logger.log("Animation " + name + " doesn't exist");
			return;
		}
		currentAnimation = ani;
		currentAnimation.resetTimer();
	}
	
	public COMPONENT_TYPE getComponentType() {
		return COMPONENT_TYPE.animation_component;
	}
}
