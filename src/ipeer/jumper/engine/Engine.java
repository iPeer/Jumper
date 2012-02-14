package ipeer.jumper.engine;

import ipeer.jumper.entity.Entity;
import ipeer.jumper.entity.Player;
import ipeer.jumper.gfx.TextRenderer;
import ipeer.jumper.level.Level;
import ipeer.jumper.util.Colour;
import ipeer.jumper.util.Debug;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Engine extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	public static boolean isRunning = false;

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
		debugActive = true;
		if (args0.length > 0) {
			for (int i = 0; i < args0.length; i++) {
				if (args0[i].equals("-debug")) {
					Debug.p("Setting debug to active.");
					debugActive = true;
				}
			}
		}
		engine = new Engine();
		frame = new JFrame(title); // Change Game Engine to your game's name!
		//main.setPreferredSize(new Dimension(width, height));
		engine.setSize(width-10, height-10);
		//		frame.setBounds(0, 0, width, height);
		frame.setDefaultCloseOperation(3);
		frame.setLayout(new BorderLayout());
		frame.add(engine, "Center");
		frame.addWindowListener(new iWindowListener());
		frame.pack();
		frame.setResizable(false); // change to true if you want users to be able to resize the window.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		engine.start();
	}

	public void init() {

		// Put stuff here that should be done while the game is starting.
		startGame();

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
					Thread.sleep(1000/31);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void startGame() {
		Level.clear();
		level = Level.loadLevel(this, "Test");
		player = new Player();
		player.x = level.xSpawn;
		player.y = level.ySpawn;
		level.addEntity(player);
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
		level.render();
		// Do your rendering here. (using g)
		if (debugActive)
			renderDebug();
		g.dispose();
		bs.show();

	}

	private void renderDebug() {
		String fps = lastframes+" fps, "+lastticks+" ticks, "+frame.getWidth()+", "+frame.getHeight()+", "+getWidth()+", "+getHeight();
		Color c = Colour.WHITE;
		if (lastframes < 30 || lastticks < 60) {
			c = Colour.RED;
		}
		int i1 = g.getFontMetrics().stringWidth(fps);
		textRenderer.drawText(fps, width - i1, 12, c);
		g.setColor(Colour.YELLOW);
		g.drawRect(0, 0, width-1, height-1);

		g.setColor(Colour.WHITE);
		g.drawString("Entities", 2, 12);
		for (int i = 0; i < level.entities.size(); i++) {
			Entity e = level.entities.get(i);
			g.drawString("\""+e.name+"\"", 2, 12*(i + 3));
		}

	}

	public static void stopDueToError(Exception e) {
		Debug.p("Unable to continue due to critical error: "+e.getMessage());
		e.printStackTrace();
		isRunning = false;
	}

	private int lastframes, lastticks;
	private static boolean debugActive = false;
	private static int height = 480;
	private static int width = 854;
	private static final String title = "Jumper";
	public static Graphics g;
	private TextRenderer textRenderer = new TextRenderer();
	private static JFrame frame;
	private Level level;
	private static Engine engine;
	public Player player;

}
