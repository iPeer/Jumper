package ipeer.jumper.level;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.entity.Entity;
import ipeer.jumper.entity.Player;
import ipeer.jumper.level.blocks.AirBlock;
import ipeer.jumper.level.blocks.Block;
import ipeer.jumper.level.blocks.LavaBlock;
import ipeer.jumper.level.blocks.TestBlock;
import ipeer.jumper.util.Debug;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class Level {

	@SuppressWarnings("unused")
	private Engine engine;
	public Level (/*int id, String file, int width, int height*/) {
		TestBlock = new TestBlock();
		entities = new ArrayList<Entity>();
	}
	
	public void init(Engine engine, int width, int height, int[] pixels) {
		this.engine = engine;
		player = engine.player;
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
		if (col == 010000) {
			xSpawn = x;
			ySpawn = y;
			block.col = 0x0a0a0a;
		}
			
	}
	
	protected Block getBlock(int x, int y, int col) {
		if (col == 0x000000) {
			return new TestBlock();
		}
		if (col == 0xFF0000) {
			return new LavaBlock();
		}
		return new AirBlock();
	}

	public static Level loadLevel(Engine engine, String name) {
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
			level.init(engine, wi, hi, pixels);
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
			Debug.p("Unable to load level!");
			e.printStackTrace();
			Engine.stopDueToError(e);
		}
		return level;
	}
	
	public void addEntity(Entity e) {
		entities.add(e);
		e.level = this;
		e.tick();
	}

	public static void clear() {
		LevelCache.clear();
	}
	
	public void render() {
		// Temporary
		g = Engine.g;
		int a = Engine.BlockSize;
		for (int y = 0; y < h; y+=a) {
			for (int x = 0; x < w; x+=a) {
				Block b = getBlock(x, y);
				if (!b.name.equals("Air Block")) {
					g.setColor(new Color(b.col));
					if (!Arrays.asList("Lava Block", "Water Block").contains(b.name))
						g.fillRect(x, y, a-1, a-1);
					else
						g.fillRect(x, y, a, a);	
				}
			}
		}
	}
	
	public int w, h;
	@SuppressWarnings("unused")
	private int id;
	public int xSpawn, ySpawn;
	public String f, name;
	public int[] theLevel;
	private static Map<String, Level> LevelCache = new HashMap<String, Level>();
	Player player;
	Block TestBlock;
	public ArrayList<Entity> entities;
	public Block[] blocks;
	private Graphics g = Engine.g;

}
