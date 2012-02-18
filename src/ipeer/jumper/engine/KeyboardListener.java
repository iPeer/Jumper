package ipeer.jumper.engine;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


public class KeyboardListener implements KeyListener {
	
	public class Key {

		public boolean down, pressed, pressed1;
		public int presses, multi;

		public Key() {
			keys.add(this);
		}

		public void toggle(boolean flag) {
			if (down != flag)
				down = flag;
			if (flag)
				presses++;
		}

		public void tick() {
			if (multi < presses) {
				multi++;
				pressed1 = true;
			} else {
				pressed1 = false;
			}
		}

	}

	public ArrayList<Key> keys;
	public Key left;
	public Key right;
	public Key up;
	public Key down;
	public Key jump;
	public Key debug;
	public Key rendering;
	public Key reload;
	public Key quit;
	public Key pause;

	public void tick() {
		for (int i = 0; i < keys.size(); i++) {
			((Key) keys.get(i)).tick();
		}
	}

	public void releaseAll() {
		for (int i = 0; i < keys.size(); i++) {
			((Key) keys.get(i)).down = false;
		}
	}

	public KeyboardListener(Engine e) {
		keys = new ArrayList<Key>();
		up = new Key();
		left = new Key();
		right = new Key();
		up = new Key();
		down = new Key();
		jump = new Key();
		debug = new Key();
		rendering = new Key();
		reload = new Key();
		quit = new Key();
		pause = new Key();
		e.addKeyListener(this);
	}
	@Override
	public void keyPressed(KeyEvent e) {
		toggle(e, true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		toggle(e, false);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	private void toggle(KeyEvent ke, boolean flag) {
		int k = ke.getKeyCode();
		if (k == KeyEvent.VK_W)
			up.toggle(flag);
		if (k == KeyEvent.VK_A)
			left.toggle(flag);
		if (k == KeyEvent.VK_D)
			right.toggle(flag);
		if (k == KeyEvent.VK_S)
			down.toggle(flag);
		if (k == KeyEvent.VK_R)
			reload.toggle(flag);
		if (k == KeyEvent.VK_UP)
			up.toggle(flag);
		if (k == KeyEvent.VK_RIGHT)
			right.toggle(flag);
		if (k == KeyEvent.VK_LEFT)
			left.toggle(flag);
		if (k == KeyEvent.VK_DOWN)
			down.toggle(flag);

		if (k == KeyEvent.VK_SPACE)
			jump.toggle(flag);
		if (k == KeyEvent.VK_J)
			jump.toggle(flag);

		if (k == KeyEvent.VK_F3)
			debug.toggle(flag);
		if (k == KeyEvent.VK_F4)
			rendering.toggle(flag);
		
		if (k == KeyEvent.VK_ESCAPE || k == KeyEvent.VK_Q || k == KeyEvent.VK_P)
			pause.toggle(flag);
	}	
}
