package ipeer.jumper.engine;

import ipeer.jumper.gui.Gui;
import ipeer.jumper.gui.GuiButton;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class iMouseMotionListener implements MouseMotionListener {

	public iMouseMotionListener(Engine engine) {
		this.engine = engine;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		int i = e.getX();
		int j = e.getY();
		Gui gui = engine.gui;
		if (gui != null) { 
			ArrayList controls = gui.controls;
			for (int l = 0; l < controls.size(); l++) {
				GuiButton button = (GuiButton)controls.get(l);
				if (i > button.xPos && i < (button.xPos + button.width) && j > button.yPos && j < (button.yPos + button.height)) {
					button.isMouseOver = true;
				}
				else {
					button.isMouseOver = false;
				}
			}
		}
	}
	
	private Engine engine;

}
