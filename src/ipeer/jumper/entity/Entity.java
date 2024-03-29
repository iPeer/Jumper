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
	
	public void setX(double x) {
		this.doubleX = x;
	}
	
	public void setY(double y) {
		this.doubleY = y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public double getDX() {
		return this.doubleX;
	}
	
	public double getDY() {
		return this.doubleY;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public boolean isOnGround() {
		return this.isOnGround;
	}
	
	public void move(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public void jump() {
		jumping = true;
		jumppeak = getY() + jumpheight;
	}
	
	public boolean isJumping() {
		return jumping;
	}
	
	
	public boolean isDead;
	public int health = 20;
	public Level level;
	public String name;
	public int x, y;
	private boolean isOnGround;
	public int width, height;
	public double doubleX, doubleY;
	public int movement;
	public double dx;
	public int jumpheight = 10;
	public boolean jumping = false;
	public int jumppeak;
	public long lastjumptime = 0;

	
	
}
