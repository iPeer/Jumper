package ipeer.jumper.engine;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class iWindowListener implements WindowListener {

	public iWindowListener() {
	}

	@Override
	public void windowActivated(WindowEvent arg0) {


	}

	@Override
	public void windowClosed(WindowEvent arg0) {


	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		Engine.isRunning = false;
		System.exit(0);

	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {


	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {


	}

	@Override
	public void windowIconified(WindowEvent arg0) {


	}

	@Override
	public void windowOpened(WindowEvent arg0) {


	}

}
