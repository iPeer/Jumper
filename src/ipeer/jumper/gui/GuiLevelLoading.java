package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;

public class GuiLevelLoading extends Gui {

	public GuiLevelLoading(Engine engine, Gui parent) {
		this.engine = engine;
		this.parent = parent;
	}


	public void tick() { }
	
	public void render() {
		drawBackground();
		drawStringWithShadow("Loading level data...", -1, Engine.height / 2, 32);
		
	}
	
	public void actionPerformed(GuiButton button) { }

	
	private Engine engine;
	private Gui parent;
	
}
