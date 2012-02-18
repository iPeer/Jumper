package ipeer.jumper.entity;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.util.Colour;

import java.awt.Graphics2D;
import java.util.Random;

public class EntityCloud extends Entity {

	public EntityCloud(int x, int y) {
		name = "Cloud";
		height = new Random().nextInt(5) + 5;
		width = new Random().nextInt(10) + 5;
		this.x = x;
		this.y = y;
		this.movement = /*new Random().nextInt(1) + 1*/1;
	}

	public void tick() {
		//if (rand.nextInt(5) == 0) {
			int i = getX() - movement;
			if (i < (0 - width))
				i = Engine.width + 1;
			setX(i);
		//}
	}

	public void render() {
		Graphics2D g = Engine.g;
		g.setColor(Colour.WHITE);
		g.fillRect(getX(), getY(), width, height);

	}

	private int movement;
	private Random rand = new Random();


}
