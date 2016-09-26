package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import entitycomponent.AnimationComponent;
import graphics.Animation;
import userInput.BSInputHandler;
import utility.Vector2f;

public class TestEntity extends Mob{
	
	private Random random = new Random();
	private int xdir = -1;
	
	public TestEntity(Vector2f position) {
		super(position);
		//"0,11>0,12>0,13" "2,11>2,12>2,13"
		List<Animation> anis = new ArrayList<Animation>();
		anis.add(Animation.createAnimation("0,11>0,13>0,12>0,13", "ani2", 150));
		anis.add(Animation.createAnimation("2,11>2,13>2,12>2,13", "ani1", 150));
		AnimationComponent anicomp = new AnimationComponent(anis);
		addComponent(anicomp);
	}
	
	public void update(BSInputHandler input){
		if(random.nextInt(100) == 90){
			//xdir *= -1;
			if(xdir > 0){
				((AnimationComponent) getComponent("AnimationComponent")).setAnimation("ani1");
			} else {
				((AnimationComponent) getComponent("AnimationComponent")).setAnimation("ani2");
			}
		}
		position.add(new Vector2f(xdir, 0));
		super.update(input);
	}
}
