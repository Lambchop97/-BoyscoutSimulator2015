package utility;

public class Vector2f {

	public float x, y;
	
	public Vector2f(){
		x = 0;
		y = 0;
	}
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2f(Vector2f vector){
		x = vector.x;
		y = vector.y;
	}
	
	public void add(Vector2f vector){
		x += vector.x;
		y += vector.y;
	}
	
	public void add(float scalar){
		x += scalar;
		y += scalar;
	}
	
	public void subtract(Vector2f vector){
		x -= vector.x;
		y -= vector.y;
	}
	
	public void subtract(float scalar){
		x -= scalar;
		y -= scalar;
	}
	
	public void multiply(float scalar){
		x *= scalar;
		y *= scalar;
	}
	
	public void scale(Vector2f scale){
		x *= scale.x;
		y *= scale.y;
	}
	
	public Vector2f normal(){
		float length = (float) Math.sqrt(this.x * this.x + this.y * this.y);
		float normalX = this.x / length;
		float normalY = this.y / length;
		return new Vector2f(normalX, normalY);
	}
	
	public float dot(Vector2f vector){
		
		return normal().x * vector.normal().x + normal().y * vector.normal().y;
	}
	
	
}
