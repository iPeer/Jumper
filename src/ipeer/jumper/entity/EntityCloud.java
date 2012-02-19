package ipeer.jumper.entity;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.util.Colour;
import ipeer.jumper.util.Debug;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.util.Random;

public class EntityCloud extends Entity {

	public EntityCloud(int x, int y) {
		name = "Cloud";
		height = new Random().nextInt(20) + 5;
		width = height + new Random().nextInt(10) + 5;
		this.x = x;
		this.y = y;
		dx = new Random().nextInt(2000);
		/*movement = new Random().nextInt(10) + 1;
		Debug.p("Set movement to "+movement+" and dx to "+dx);*/
		movement = 1;
		transparency = new Random().nextFloat();
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
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency));
		g.fillRoundRect(getX(), getY(), width, height, 2, 3);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

	}
	
	public void setHorizontal(double dx) {
		this.dx = dx;
	}

	//private int movement;
	private Random rand = new Random();
	private float transparency;
	//private double dx;


}
