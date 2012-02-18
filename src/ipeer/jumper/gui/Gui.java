package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.util.Colour;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

@SuppressWarnings({"rawtypes", "unused"})
public class Gui {

	public Gui() {
		controls = new ArrayList();
		title = "Some GUI";
		g = Engine.g;
	}
	
	public void drawBackground() {
		Graphics2D g2d = Engine.g;
		g2d.setColor(Colour.BLACK);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
		g2d.fillRect(0, 0, Engine.width, Engine.height);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
	}
	
	private Graphics g;

	public void render() {
		for (int c = 0; c < controls.size(); c++) {
			GuiButton button = (GuiButton)controls.get(c);
			button.render();
		}
	}
	
	public void tick() { 
		for (int c = 0; c < controls.size(); c++) {
			GuiButton button = (GuiButton)controls.get(c);
			button.tick();
		}
	}
	
	public void actionPerformed(GuiButton button) { }

	public boolean pausesGame() {
		return false;
	}
	
	public void drawStringWithShadow(String s, int x, int y) {
		drawStringWithShadow(s, x, y, 14);
	}
	
	public void drawStringWithShadow(String s, int x, int y, int h) {
		Graphics2D g = Engine.g;
		
		Font f = g.getFont();
		g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, h));
		if (x == -1) {
			x = (Engine.width - g.getFontMetrics().stringWidth(s)) / 2;
		}
		g.setColor(Colour.BLACK);
		g.drawString(s, x, y);
		g.setColor(Colour.WHITE);
		g.drawString(s, x-3, y-3);
		g.setFont(f);
	}
	
	protected Engine engine;
	public String title;
	public ArrayList controls;
	
}
