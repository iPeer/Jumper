package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.level.Level;

import java.awt.Graphics2D;

public class GuiPauseScreen extends Gui {

	@SuppressWarnings("unchecked")
	public GuiPauseScreen(Engine engine) {
		this.engine = engine;
		g = Engine.g;
		title = "Game Menu";
		controls.add(new GuiButton(3,(Engine.width - 200) /2, (Engine.height / 2) - 32, "Restart Level"));
		controls.add(new GuiButton(0,(Engine.width - 200) /2, (Engine.height / 2) + 2, "Back to Game"));
		controls.add(new GuiButton(2,(Engine.width - 200) /2, (Engine.height / 2) + 36, "Back to Title"));
		controls.add(new GuiButton(1,(Engine.width - 200) /2, (Engine.height / 2) + 70, "Quit Game"));
	}
	
	public void render() {
		drawBackground();
		g = Engine.g;
		drawStringWithShadow(title, -1, 40);
		super.render();
	}
	
	public void tick() {
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
		if (button.id == 2) {
			engine.loadMenuScreen();
		}
		if (button.id == 3) {
			engine.restartCurrentlevel();
		}
	}
	
	private Engine engine;
	private Graphics2D g;
	
}
