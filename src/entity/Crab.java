package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entitycomponent.AnimationComponent;
import graphics.Animation;
import userInput.BSInputHandler;
import utility.Vector2f;

public class Crab extends Mob{
	
	private Random random = new Random();
	private int xdir = 1;
	
	public Crab(Vector2f position) {
		super(position);
		List<Animation> anis = new ArrayList<Animation>();
		anis.add(Animation.createAnimation("6,5>7,5>8,5", "WalkRight", 200));
		anis.add(Animation.createAnimation("6,5>8,5>7,5", "WalkLeft", 200));
		AnimationComponent anicomp = new AnimationComponent(anis);
		addComponent(anicomp);
	}
	
	public void update(BSInputHandler input){
		if(random.nextInt(100) == 90){
			xdir *= -1;
			if(xdir > 0){
				((AnimationComponent) getComponent("AnimationComponent")).setAnimation("WalkRight");
			} else {
				((AnimationComponent) getComponent("AnimationComponent")).setAnimation("WalkLeft");
			}
		}
		position.add(new Vector2f(xdir, 0));
		super.update(input);
	}

}
