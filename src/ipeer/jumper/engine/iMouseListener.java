package ipeer.jumper.engine;

import ipeer.jumper.gui.Gui;
import ipeer.jumper.gui.GuiButton;
import ipeer.jumper.util.Debug;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class iMouseListener implements MouseListener {

	public iMouseListener(Engine engine) {
		this.engine = engine;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void mouseClicked(MouseEvent e) {
		int i = e.getX();
		int j = e.getY();
		Gui gui = engine.gui;
		if (gui != null) { 
			ArrayList controls = gui.controls;
			for (int l = 0; l < controls.size(); l++) {
				GuiButton button = (GuiButton)controls.get(l);
				if (button.mousePressed(engine, i, j))
					gui.actionPerformed(button);
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	private Engine engine;

}
