package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;
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
		g.setColor(Colour.GUIBUTTON);
		if (!enabled)
			g.setColor(Colour.GUIBUTTONDISABLED);
		g.fillRect(xPos, yPos, width, height);
		g.setColor(Colour.WHITE);
		g.drawRect(xPos, yPos, width, height);
		int i = g.getFontMetrics().stringWidth(text);
		g.drawString(text, (xPos + (width - i) / 2), yPos + ((10 + height) / 2));
	}
	
	public void tick() { super.tick(); }
	
	public void mouseReleased(int x, int y) { }
	public void mouseDragged(Engine engine, int x, int y) { }
	
	public boolean mousePressed(Engine engine, int x, int y) {
		return enabled && x > xPos && x < (xPos + width) && y < (yPos + height) && y > yPos;
	}
	
	private int xPos, yPos, width, height;
	public String text;
	public boolean enabled;
	public int id;
	
}
