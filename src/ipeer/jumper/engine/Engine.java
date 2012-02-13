package ipeer.jumper.engine;

import ipeer.jumper.gfx.TextRenderer;
import ipeer.jumper.util.Colour;
import ipeer.jumper.util.Debug;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Engine extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public boolean isRunning = false;
	
	public Engine() {
		
		// Do initial set up here for stuff that should be set BEFORE the game runs.
		
	}
	
	// start() & stop() are compatibility for Applets.
	
	public void start() {
		isRunning = true;
		(new Thread(this)).start();
	
	}
	
	public void stop() {
		isRunning = false;
	}
	
	// So we can run it as an application should we need to do.
	public static void main(String[] args0) {
		Debug.p("Command line options given: "+args0.length);
		if (args0.length > 0) {
			for (int i = 0; i < args0.length; i++) {
				if (args0[i].equals("-debug")) {
					Debug.p("Setting debug to active.");
					debugActive = true;
				}
			}
		}
		Engine main = new Engine();
		main.setMaximumSize(new Dimension(width, height));
		main.setPreferredSize(new Dimension(width, height));
		JFrame frame = new JFrame(title); // Change Game Engine to your game's name!
		frame.setDefaultCloseOperation(3);
		frame.setLayout(new BorderLayout());
		frame.add(main, "Center");
		
		frame.pack();
		frame.setResizable(true); // change to true if you want users to be able to resize the window.
		frame.setVisible(true);
		main.start();
	}
	
	public void init() {
		
		// Put stuff here that should be done while the game is starting.
		
	}
	
	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double toprocess = 0.0;
		/*
		 * 
		 * This will limit the number of renders per second.
		 * 
		 * Dividing by 60, will limit the game to 60 renders/sec
		 * 30 will limit it to 30, 100 to 100 and so on.
		 * 
		 */
		double nsPerTick = 1000000000 / 60.0;
		int frames = 0;
		int ticks = 0;
		long lastTick = System.currentTimeMillis();
		init(); // Run the init set above.
		while (isRunning) { // will loop while the game is running.
			long now = System.nanoTime();
			toprocess += (double)(now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender;
			for (shouldRender = true; toprocess >= 1.0; shouldRender = true) {
				ticks++; // count this tick
				tick(); // run the tick.
				toprocess--; // remove 1 from the toprocess queue.
			}
			if (shouldRender) {
				frames++;
				render();
			}
			if (System.currentTimeMillis() - lastTick > 1000) {
				lastTick = System.currentTimeMillis();
				// Output the tick & FPS counts (debug).
				System.out.println(frames+ " fps, "+ticks+ " ticks");
				lastframes = frames;
				lastticks = ticks;
				frames = ticks = 0; // reset them both to 0.
			}
			if (!hasFocus()) {
				try {
					Thread.sleep(1000/30);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void tick() {
		// Do stuff that should be only ran so often here.
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2); // This (the 2) can be changed if needed.
			requestFocus();
			return;
		}
		g = bs.getDrawGraphics();
		
		// This will render the game screen black
		g.setColor(Colour.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		// Do your rendering here. (using g)
		if (debugActive)
			renderDebug();
		g.dispose();
		bs.show();
		
	}
	
	private void renderDebug() {
		String fps = lastframes+" fps, "+lastticks+" ticks";
		Color c = Colour.WHITE;
		if (lastframes < 30 || lastticks < 60) {
			c = Colour.RED;
		}
		textRenderer.drawText(fps, 1, height - 2, c);
	}
	
	private int lastframes, lastticks;
	private static boolean debugActive = false;
	private static int height = 480;
	private static int width = 854;
	private static String title = "Jumper";
	public static Graphics g;
	private TextRenderer textRenderer = new TextRenderer();

}
