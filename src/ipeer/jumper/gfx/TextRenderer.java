package ipeer.jumper.gfx;

import ipeer.jumper.engine.Engine;

import java.awt.Color;
import java.awt.Graphics;

public class TextRenderer {

	public TextRenderer() {	}
	
	public void drawText(String t, int x, int y, Color c) {
		Graphics g = Engine.g;
		g.setColor(c);
		g.drawString(t, x, y);
	}
	
}
