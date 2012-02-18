package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;

import java.awt.Graphics2D;

public class GuiConfirmQuit extends Gui {

	public GuiConfirmQuit(Gui gui, Engine engine) {
		this.parent = gui;
		this.engine = engine;
		title = "Confirm Exit";
	}
	
	public void render() {
		drawBackground();
		g = Engine.g;
		drawStringWithShadow(title, -1, 40);
		super.render();
	}
	
	public boolean pausesGame() {
		return true;
	}
	
	private Engine engine;
	public Gui parent;
	private Graphics2D g;
	
}
