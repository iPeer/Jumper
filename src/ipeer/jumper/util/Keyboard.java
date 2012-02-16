package ipeer.jumper.util;

import java.awt.Component;
import java.awt.event.KeyEvent;

public class Keyboard extends KeyEvent {

	private static final long serialVersionUID = 7353230295804072700L;

	public Keyboard(Component source, int id, long when, int modifiers,
			int keyCode, char keyChar) {
		super(source, id, when, modifiers, keyCode, keyChar);
		// TODO Auto-generated constructor stub
	}

	public Keyboard(Component source, int id, long when, int modifiers,
			int keyCode, char keyChar, int keyLocation) {
		super(source, id, when, modifiers, keyCode, keyChar, keyLocation);
		// TODO Auto-generated constructor stub
	}

}
