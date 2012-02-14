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
		
	}
	
	public void tick() {
		
	}
	
	public void logic() {
		
	}
	
	public void move() {
		
	}
	
	public boolean isDead;
	public int health = 20;
	public Level level;
	public String name;
	
	
}
