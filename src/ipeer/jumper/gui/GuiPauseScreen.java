package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.util.Colour;
import ipeer.jumper.util.Debug;

import java.awt.Font;
import java.awt.Graphics2D;

public class GuiPauseScreen extends Gui {

	@SuppressWarnings("unchecked")
	public GuiPauseScreen(Engine engine) {
		this.engine = engine;
		g = Engine.g;
		title = "Game Menu";
		pausetime = 60;
		controls.add(new GuiButton(0,(Engine.width - 200) /2, (Engine.height / 2) - 32, "Back to Game"));
		controls.add(new GuiButton(1,(Engine.width - 200) /2, (Engine.height / 2) + 2, "Quit Game"));

	}
	
	public void render() {
		drawBackground();
		g = Engine.g;
		drawStringWithShadow(title, -1, 40);
		super.render();
	}
	
	public void tick() {
		pausetime--;
	}
	
	public boolean pausesGame() {
		return true;
	}

	public void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			engine.setGUI(null);
		}
		if (button.id == 1) {
			engine.setGUI(new GuiConfirmQuit(this, engine));
		}
	}
	
	@SuppressWarnings("unused")
	private Engine engine;
	private Graphics2D g;
	private int pausetime;
	
}
