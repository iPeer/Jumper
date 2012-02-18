package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;

import java.awt.Graphics2D;

public class GuiConfirmQuit extends Gui {

	@SuppressWarnings("unchecked")
	public GuiConfirmQuit(Gui gui, Engine engine) {
		this.parent = gui;
		this.engine = engine;
		title = "Confirm Exit";
		controls.add(new GuiButton(0, (Engine.width / 2) - 100, (Engine.height / 2) + 20, 100, 30, "Yes"));
		controls.add(new GuiButton(1, (Engine.width / 2) + 2, (Engine.height / 2) + 20, 100, 30, "No"));
	}
	
	public void render() {
		drawBackground();
		g = Engine.g;
		drawStringWithShadow(title, -1, 40);
		drawStringWithShadow("Are you sure you want to quit Jumper? You will be missed!", -1, Engine.height / 2, g.getFont().getSize());
		super.render();
	}
	
	public void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			Engine.isRunning = false;
			System.exit(0);
		}
		else {
			engine.setGUI(parent);
		}
	}
	
	public boolean pausesGame() {
		return true;
	}
	
	private Engine engine;
	public Gui parent;
	private Graphics2D g;
	
}
