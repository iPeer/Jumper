package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.level.Level;

public class GuiLevelError extends Gui {

	@SuppressWarnings("unchecked")
	public GuiLevelError(int i, Engine engine, boolean loadMenuLevel) {
		this.text = getTextForErrorNumber(i);
		this.engine = engine;
		if (loadMenuLevel)
			engine.loadMenuScreen(true);
		else {
			engine.setGUI(new GuiMainMenu(engine));
		}
		controls.add(new GuiButton(0, (Engine.width / 2) - 50, Engine.height / 2 + 20, 100, 30, "okay.jpg"));
	}
	
	private String getTextForErrorNumber(int i) {
		switch (i) {
			case 1:
				return "The level could not be loaded because the dimensions were too small or too big. Levels must be 854x480 pixels.";
			case 2:
				return "The level file(s) for the specified level were not found.";
			default:
				return "An unknown error occured";
		}
	}

	public void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			engine.setGUI(new GuiMainMenu(engine));
		}
	}
	
	public void render() {
		drawBackground();
		drawStringWithShadow(text, -1, (Engine.height / 2) - 20, Engine.g.getFont().getSize());
		super.render();
	}
	
	private String text;
	private Engine engine;

}
