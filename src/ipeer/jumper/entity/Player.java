package ipeer.jumper.entity;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.util.Colour;

import java.awt.Graphics;

public class Player extends Entity {

	public Player() {
		name = "Player";
	}
	
	public void render() {
		Graphics g = Engine.g;
		g.setColor(Colour.PINK);
		g.drawRect(getX(), getY(), 10, 10);
	}
	
	public void tick() {
		if (!isOnGround) {
			super.move(getX(), getY() + 1);
			if (this.y > Engine.height);
				this.y = 0;
		}
	}
	
	public void setX(int x) {
		super.setX(x);
	}
	
	public void setY(int y) {
		super.setY(y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void move(int x, int y) {
		super.move(x, y);
	}

	//public int x, y;
	public boolean isOnGround = false;
	
}
