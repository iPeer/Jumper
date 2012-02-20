package ipeer.jumper.entity;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.engine.KeyboardListener;
import ipeer.jumper.util.Colour;
import ipeer.jumper.util.Debug;

import java.awt.Graphics;

public class Player extends Entity {

	public Player() {
		name = "Player";
		jumpheight = 60;
		jumping = false;
	}

	public void render() {
		Graphics g = Engine.g;
		g.setColor(Colour.PINK);
		g.drawRect(getX(), getY(), width, height);
		if (Engine.debugActive) {
			g.setColor(Colour.YELLOW);
			g.drawLine(getX() + width, 0, getX() + width, Engine.height);
			g.drawLine(0, getY() + height, Engine.width, getY() + height);
		}
	}

	public void tick() {
		KeyboardListener input = Engine.input;
		isOnGround = level.isPlayerOnSolidBlock(this);
		if (jumping) {
			if (getY() > jumppeak) {
				int y = getY();
				setY(y-=5);
				if (getY() <= jumppeak) {
					jumping = false;
					lastjumptime = System.currentTimeMillis();
				}
			}
		}
		if (!isOnGround && !jumping)
			move(getX(), getY() + (input.jump.down ? 1 : 3));
		if (input.left.down && level.checkSidewaysCollision(this, 0))
			move(x-=2, getY());
		else if (input.right.down && level.checkSidewaysCollision(this, 1)) 
			move(x+=2, getY());
		if (getY() + height > Engine.height)
			move(level.xSpawn, level.ySpawn);
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

	public void jump() {
		if (System.currentTimeMillis() - lastjumptime > 400) {
			int y = getY();
			jumppeak = y - jumpheight;
			jumping = true;
		}
	}

	public void move(int x, int y) {
		super.move(x, y);
		setX(x);
		setY(y);
	}

	public boolean isOnGround() {
		return isOnGround;
	}

	public static int getHeight() {
		return height;
	}


	//public int x, y;
	public boolean isOnGround = false;
	public static int height = 10;
	public static int width = 10;

}
