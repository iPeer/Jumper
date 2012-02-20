package ipeer.jumper.level;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.entity.Entity;
import ipeer.jumper.entity.Player;
import ipeer.jumper.error.IncorrectLevelSizeError;
import ipeer.jumper.error.LevelIsNullError;
import ipeer.jumper.gui.GuiLevelError;
import ipeer.jumper.gui.GuiLevelLoading;
import ipeer.jumper.level.blocks.AirBlock;
import ipeer.jumper.level.blocks.Block;
import ipeer.jumper.level.blocks.GrassBlock;
import ipeer.jumper.level.blocks.LavaBlock;
import ipeer.jumper.level.blocks.SpawnBlock;
import ipeer.jumper.level.blocks.StoneBlock;
import ipeer.jumper.level.blocks.SolidBlock;
import ipeer.jumper.util.Colour;
import ipeer.jumper.util.Debug;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

@SuppressWarnings({"static-access", "unused"})
public class Level {

	public Level (/*int id, String file, int width, int height*/) {
		entities = new ArrayList<Entity>();
	}

	public void init(Engine engine, int width, int height, int[] pixels, String name) {
		this.engine = engine;
		player = engine.player;
		this.levelname = name;
		//this.player = player;
		w = width;
		h = height;
		blocks = new Block[w * h];
		for (int y = 0; y < h; y++) {
			for (int x = 0; x  < w; x++) {
				int col = pixels[x + y * w] & 0xffffff;
				int id = 255 - (pixels[x + y * w] >> 24 & 0xff);
				Block block = getBlock(x, y, col);
				block.id = id;
				blocks[x + y * w] = block;
				block.level = this;
				block.x = x;
				block.y = y;
			}
		}

		for (int y = 0; y < h; y++) {
			for (int x = 0; x < w; x++) {
				int col = pixels[x + y * w] & 0xFFFFFF;
				colourBlock(x, y, blocks[x + y * w], col);
			}
		}
		try {
			this.image = createImageForLevel(/*pixels,*/ w, h, name);
		}
		catch (IncorrectLevelSizeError e) {
			engine.setGUI(new GuiLevelError(1, engine, true));
		}

	}

	private BufferedImage createImageForLevel(/*int[] pixels,*/ int w, int h, String name) throws IncorrectLevelSizeError {
		BufferedImage i = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB); // 2
		int a1 = i.getWidth();
		int b1 = i.getHeight();
		if (a1+b1 != 1334)
			throw new IncorrectLevelSizeError();
		g = i.createGraphics();
		if (Arrays.asList("test", "menu", "menu2").contains(name.toLowerCase())) {
			g.setPaint(new GradientPaint(0, 0, new Colour(0x9FE4FF), 0, Engine.height, Colour.CYAN));
			g.fillRect(0, 0, Engine.width, Engine.height);
		}
		int a = Engine.BlockSize;
		for (int y = 0; y < h; y+=a) {
			for (int x = 0; x < w; x+=a) {
				Block b = getBlock(x, y);
				if (!b.name.equals("Air Block")) {
					g.setColor(new Color(b.col));
					if (!Arrays.asList("Lava Block", "Water Block").contains(b.name)) { 
						g.fillRect(x, y, a-1, a-1);
					}
					else
						g.fillRect(x, y, a, a);
				}
			}
		}
		engine.setGUI(engine.gui.parent);
		return i;
	}

	public Block getBlock(int x, int y) {
		if (x < 0 || y < 0 || y > h || x > w) {
			return new AirBlock();
		}
		return blocks[x + y * w];
	}

	protected void colourBlock(int x, int y, Block block, int col) {
		//		if (col == 0x000000)
		//			block.col = 0xFFFFFF;
		if (col == 0x65B6AE) {
			xSpawn = x;
			ySpawn = (y - 10) - Player.getHeight();
			Debug.p("Spawn is at: ("+xSpawn+", "+ySpawn+")");
		}
		if (col == 0x4CFF00) {
			block.col = new Colour(76, new Random().nextInt(40)+215, 0).getRGB();
		}
		if (col == 0x404040) {
			block.col = new Colour(new Random().nextInt(10) + 40, 40, 40).getRGB();
		}

	}

	protected Block getBlock(int x, int y, int col) {
		if (col == 0x000000) {
			return new SolidBlock();
		}
		if (col == 0xFF0000) {
			return new LavaBlock();
		}
		if (col == 0x65B6AE) {
			return new SpawnBlock();
		}
		if (col == 0x4CFF00) {
			return new GrassBlock();
		}
		if (col == 0x404040) {
			return new StoneBlock();
		}
		return new AirBlock();
	}

	public static Level loadLevel(Engine engine, String name) throws LevelIsNullError {
		engine.setGUI(new GuiLevelLoading(engine, engine.gui));
		Engine engine2 = engine;
		InputStream in;
		Level level;
		try {
			Debug.p("Attempting to load Level: "+name);
			Debug.p(Level.class);
			in = Level.class.getResourceAsStream("img/"+name.toLowerCase()+".png");
			Debug.p(in);
			BufferedImage i = ImageIO.read(in); 
			//BufferedImage i = ImageUtil.resizeImage(i1, i1.getWidth(null) * 2, i1.getHeight(null) * 2);
			int wi = i.getWidth();
			int hi = i.getHeight();
			int[] pixels = new int[wi * hi];
			i.getRGB(0, 0, wi, hi, pixels, 0, wi);
			level = getLevelClassForName(name);
			if (level == null) {
				throw new LevelIsNullError();
			}
			level.init(engine, wi, hi, pixels, name);
			//level.createStill(engine, wi, hi, pixels);
			LevelCache.put(name, level);
			return level;

		}
		catch (Exception e) {
			Debug.p("Unable to load level!");
			e.printStackTrace();
			Engine.stopDueToError(e);
		}
		return null;

	}


	private static Level getLevelClassForName(String name) {
		Level level = null;
		try {
			level = (Level)Class.forName("ipeer.jumper.level."+name+"Level").newInstance();
		}
		catch (Exception e) {
			Engine.engine.setGUI(new GuiLevelError(3, Engine.engine, false));
			Debug.p("Unable to load level!");
			e.printStackTrace();
		}
		return level;
	}

	public void addEntity(Entity e) {
		entities.add(e);
		e.level = this;
		e.tick();
	}

	public static void clear() {
		blocks = new Block[0];
		LevelCache.clear();
		if (entities != null && !entities.isEmpty())
			entities.clear();
	}

	public boolean checkTerrainCollision(Player p) {
		return getBlock(p.x + p.width, p.y).isSolid || getBlock(p.x, p.y + p.height + 2).isSolid || getBlock(p.x, p.y).isSolid || getBlock(p.x + p.width, p.y + p.height).isSolid;
	}

	public boolean isPlayerOnSolidBlock(Player p) { // This is gay
		return getBlock(p.getX(), p.getY() + p.height).isSolid || getBlock(p.getX() + (p.height), p.getY() + ((p.height / 2 - 2) + p.width)).isSolid;
	}

	public boolean checkSidewaysCollision(Player p, int dir) {
		if (dir == 0) {
			for (int i = p.getY();i < p.getY()+p.height; i++) {
				if (getBlock(p.getX() - 2, i).isSolid)
					return false;
			}
		}
		else {
			for (int i1 = p.getX() + p.width;i1 < p.getY()+p.height; i1++) {
				if (getBlock(p.getX() + (p.height / 2 - 1) + (p.width), i1).isSolid)
					return false;
			}
		}
		return true;
	}

	public void render() {
		BufferedImage i = image;
		//		int w1 = i.getWidth();
		//		int h1 = i.getHeight();
		//		if (!(Integer.toString(w1)+Integer.toString(h1)).equals("854480")) {
		//			//Level.clear();
		//			engine.setGUI(new GuiLevelError(1, engine, true));
		//			return;
		//		}
		g = Engine.g;
		g.drawImage(i, 0, 0, null);
	}

	public void tick() { }

	public int w, h;
	private int id;
	public int xSpawn, ySpawn;
	public String f, name;
	public int[] theLevel;
	private static Map<String, Level> LevelCache = new HashMap<String, Level>();
	Player player;
	Block TestBlock;
	public static ArrayList<Entity> entities;
	public static Block[] blocks;
	private Graphics2D g;
	private Engine engine;
	private BufferedImage image;
	public String levelname;

}
