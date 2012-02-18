package ipeer.jumper.entity;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.engine.KeyboardListener;
import ipeer.jumper.util.Colour;

import java.awt.Graphics;

public class Player extends Entity {

	public Player() {
		name = "Player";
	}
	
	public void render() {
		Graphics g = Engine.g;
		g.setColor(Colour.PINK);
		g.drawRect(getX(), getY(), width, height);
	}
	
	public void tick() {
		KeyboardListener input = Engine.input;
		isOnGround = level.isPlayerOnSolidBlock(this) && level.playerIsInBlock(this);
		if (input.left.down)
			move(x-=2, getY());
		else if (input.right.down) 
			move(x+=2, getY());
		if (!isOnGround) {
			if (level.checkTerrainCollision(this)) {
				isOnGround = true;
				return;
			}
			super.move(getX(), (int)(getY() + 1));
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
		setX(x);
		setY(y);
	}
	
	public boolean isOnGround() {
		return isOnGround;
	}

	//public int x, y;
	public boolean isOnGround = false;
	public static int height = 10;
	public static int width = 10;
	public static int getHeight() {
		return height;
	}
	
}
