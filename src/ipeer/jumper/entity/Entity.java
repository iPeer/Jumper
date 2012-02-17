package ipeer.jumper.entity;

import ipeer.jumper.level.Level;

public class Entity {

	public Entity() {
		name = "";
	}
	
	public int getHeath() {
		return health;
	}
	
	public boolean isDead() {
		return !isDead;
	}
	
	public void render() {
		this.render();
	}
	
	public void tick() {
		this.tick();
	}
	
	public void logic() {
		
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move(int x, int y) {
		setX(x);
		setY(y);
	}
	
	
	public boolean isDead;
	public int health = 20;
	public Level level;
	public String name;
	public int x, y;
	
	
}
