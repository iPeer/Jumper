package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.util.Colour;

import java.awt.Graphics2D;

public class GuiMainMenu extends Gui {

	@SuppressWarnings("unchecked")
	public GuiMainMenu(Engine engine) {

		title = "Main Menu";
		this.engine = engine;
		controls.add(new GuiButton(0,(Engine.width - 200) /2, (Engine.height / 2) - 32, "Start Game"));
		controls.add(new GuiButton(1,(Engine.width - 200) /2, (Engine.height / 2) + 2, "Quit"));
		
	}
	
	public void actionPerformed(GuiButton button) {
		if (button.id == 0) {
			engine.setGUI(null);
			engine.startGame();
		}
		else {
			engine.setGUI(new GuiConfirmQuit(this, engine));
		}
	}
	
	public void tick() {
	}
	
	public void render() {
		Graphics2D g = Engine.g;
		drawStringWithShadow(Engine.VERSION, 2, Engine.height, g.getFont().getSize());
		super.render();
	}
	
	public boolean pausesGame() {
		return false;
	}
	
	private Engine engine;

}
