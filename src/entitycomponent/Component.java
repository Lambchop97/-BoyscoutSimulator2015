package entitycomponent;

import display.Screen;
import utility.Vector2f;

public interface Component {
	enum COMPONENT_TYPE{
		ai_component,
		animation_component,
		collision_component,
		effect_component,
		partical_component,
		sound_component
	};
	void update(Object o);
	void render(Screen screen, Vector2f position);
	String getName();
	COMPONENT_TYPE getComponentType();
}
