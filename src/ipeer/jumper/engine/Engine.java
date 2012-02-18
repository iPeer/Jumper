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
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;

import javax.swing.JFrame;

public class Engine extends Canvas implements Runnable {

	private static final long serialVersionUID = -3003647165121358744L;
	public static boolean isRunning = false;

	public Engine() {

		// Do initial set up here for stuff that should be set BEFORE the game
		// runs.

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
		Debug.p("Command line options given: " + args0.length);
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
		// main.setPreferredSize(new Dimension(width, height));
		engine.setSize(width - 10, height - 10);
		// frame.setBounds(0, 0, width, height);
		frame.setDefaultCloseOperation(3);
		frame.setLayout(new BorderLayout());
		frame.add(engine, "Center");
		frame.addWindowListener(new iWindowListener());
		// engine.addKeyListener(new KeyboardListener(engine));
		frame.pack();
		frame.setResizable(false); // change to true if you want users to be
		// able to resize the window.
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		engine.start();
	}

	public void init() {
		input = new KeyboardListener(engine);
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
		 * Dividing by 60, will limit the game to 60 renders/sec 30 will limit
		 * it to 30, 100 to 100 and so on.
		 */
		double nsPerTick = 1000000000 / 60.0;
		int frames = 0;
		int ticks = 0;
		long lastTick = System.currentTimeMillis();
		init(); // Run the init set above.
		while (isRunning) { // will loop while the game is running.
			try {
				long now = System.nanoTime();
				toprocess += (double) (now - lastTime) / nsPerTick;
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
					// System.out.println(frames+ " fps, "+ticks+ " ticks");
					lastframes = frames;
					lastticks = ticks;
					frames = ticks = 0; // reset them both to 0.
				}
			} catch (OutOfMemoryError e) {
				Debug.p("***** OUT OF MEMORY! *****");
			}
			if (!hasFocus()) {
				isPaused = true;
				try {
					Thread.sleep(1000 / 29);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}
	}

	public void startGame() {
		try {
			levelLoading = true;
			Level.clear();
			player = new Player();
			level = Level.loadLevel(this, "Test");
			player.x = level.xSpawn;
			player.y = level.ySpawn;
			player.move(level.xSpawn, level.ySpawn);
			Debug.p(level.ySpawn + " | " + player.y);
			level.addEntity(player);
		} catch (OutOfMemoryError e) {
			Debug.p("***** OUT OF MEMORY *****");
		}
	}

	public void tick() {
		if (input.quit.down) {
			isRunning = false;
			System.exit(0);
		}
		if (input.reload.down)
			startGame();
		if (input.debug.down && System.currentTimeMillis() - lastPress > 150) {
			debugActive = !debugActive;
			lastPress = System.currentTimeMillis();
		}
		if (!isPaused) {
			input.tick();
			for (int i = 0; i < level.entities.size(); i++) {
				Entity e = level.entities.get(i);
				e.tick();
			}
		}
		else {
			pauseflash--;
		}
	}

	public void render() {

		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2); // This (the 2) can be changed if needed.
			requestFocus();
			return;
		}
		Graphics g1 = bs.getDrawGraphics();
		g = (Graphics2D) g1;

		// This will render the game screen black
		g.setColor(Colour.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (!levelLoading) {
			level.render();
		} else {
			g.setColor(Colour.RED);
			String loadingString = "Loading level..";
			Font f = g.getFont();
			g.setFont(new Font(g.getFont().getName(), Font.PLAIN, 32));
			int l = g.getFontMetrics().stringWidth(loadingString);
			g.drawString(loadingString, (width - l) / 2, height / 2);
			g.setFont(f);
		}
		// Do your rendering here. (using g)
		if (debugActive)
			renderDebug();
		for (int i = 0; i < level.entities.size(); i++) {
			Entity e = level.entities.get(i);
			e.render();
		}
		if (isPaused) {
			Font f = g.getFont();
			g.setFont(new Font(g.getFont().getFontName(), Font.BOLD, 32));
			if (pauseflash > 30)
				g.setColor(Colour.YELLOW);
			else
				g.setColor(Colour.RED);
			if (pauseflash < 0)
				pauseflash = 60;
			String p = "PAUSED!";
			int p1 = g.getFontMetrics().stringWidth(p);
			g.drawString(p, (width - p1) / 2, height / 2);
			g.setFont(f);
		}
		g.dispose();
		bs.show();

	}

	private void renderDebug() {
		MemoryUsage m = ManagementFactory.getMemoryMXBean()
		.getHeapMemoryUsage();
		long ma = m.getMax() / (1024 * 1024);
		long mb = m.getUsed() / (1024 * 1024);
		String fps = lastframes + " fps, " + lastticks + " ticks. BlockSize: "
		+ BlockSize;
		Color c = Colour.WHITE;
		if (lastframes < 30 || lastticks < 60) {
			c = Colour.RED;
		}
		FontMetrics fm = g.getFontMetrics();
		int i1 = fm.stringWidth(fps);
		textRenderer.drawText(fps, width - (i1 + 2), 12, c);
		String mem = mb + "MB/" + ma + "MB";
		int i2 = fm.stringWidth(mem);
		textRenderer.drawText(mb + "MB/" + ma + "MB", width - (i2 + 2), 24,
				Colour.WHITE);
		g.setColor(Colour.YELLOW);
		g.drawRect(0, 0, width - 1, height - 1);

		g.setColor(Colour.WHITE);
		g.drawString("Entities", 2, 12);
		for (int i = 0; i < level.entities.size(); i++) {
			Entity e = level.entities.get(i);
			g.drawString("\"" + e.name + "\" - " + e.getX() + ", " + e.getY()
					+ ", " + e.isOnGround(), 2, 12 * (i + 3));
		}

	}

	public static void stopDueToError(Exception e) {
		Debug.p("Unable to continue due to critical error: " + e.getMessage());
		e.printStackTrace();
		isRunning = false;
	}

	private int lastframes, lastticks;
	public static boolean debugActive = false;
	public static int height = 480, width = 854;
	private static final String title = "Jumper";
	public static Graphics2D g;
	private TextRenderer textRenderer = new TextRenderer();
	private static JFrame frame;
	private Level level;
	private static Engine engine;
	public Player player;
	public static int BlockSize = 3;
	public static boolean levelLoading = false;
	public static KeyboardListener input;
	long lastPress = 0;
	public boolean isPaused = false;
	private int pauseflash = 60;

}
