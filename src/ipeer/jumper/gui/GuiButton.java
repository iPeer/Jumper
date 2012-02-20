package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.sound.Sound;
import ipeer.jumper.util.Colour;

import java.awt.Graphics2D;

public class GuiButton extends Gui {

	public GuiButton(int id, int x, int y, String s) {
		this(id, x, y, 200, 30, s);
	}
	
	public GuiButton(int id, int x, int y, int w, int h, String s) {
		width = w;
		height = h;
		xPos = x;
		yPos = y;
		text = s;
		enabled = true;
		this.id = id;
	}
	
	public void render() {
		Graphics2D g = Engine.g;
		g.setColor(new Colour(808080));
		if (isMouseOver())
			g.setColor(new Colour(818080));
		if (!enabled)
			g.setColor(new Colour(808080));
		g.fill3DRect(xPos, yPos, width, height, enabled);
		g.setColor(Colour.WHITE);
		int i = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (xPos + (width - i) / 2), yPos + ((9 + height) / 2));
	}
	
	public void tick() { super.tick(); }
	
	
	public void mouseReleased(int x, int y) { }
	public void mouseDragged(Engine engine, int x, int y) { }
	
	public boolean isMouseOver() {
		return isMouseOver;
	}
	
	public boolean mousePressed(Engine engine, int x, int y) {
		boolean a = enabled && x > xPos && x < (xPos + width) && y < (yPos + height) && y > yPos;
		if (a)
			Sound.select.play();
		return a;
	}
	
	
	public int xPos, yPos, width, height;
	public String text;
	public boolean enabled;
	public int id;
	public boolean isMouseOver = false;
	
}
