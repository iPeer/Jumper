package ipeer.jumper.engine;

import ipeer.jumper.util.Debug;
import ipeer.jumper.util.Keyboard;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;


public class KeyboardListener implements KeyListener {

	public KeyboardListener(Engine e) {
		this.engine = e;
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		int key = arg0.getKeyCode();
		if (Arrays.asList(61, 107).contains(key)) {
			Engine.BlockSize++;
		}
		if (Arrays.asList(45, 109).contains(key) && Engine.BlockSize > 1) {
			Engine.BlockSize--;
		}
		if (Arrays.asList(27, Keyboard.VK_Q).contains(key)) {
			Engine.isRunning = false;
			System.exit(0);
		}
		if (key == 82) {
			engine.startGame();
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	private Engine engine;
	

}
