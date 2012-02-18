package ipeer.jumper.level.blocks;

import ipeer.jumper.entity.Entity;
import ipeer.jumper.level.Level;

public class Block {

	public Block() {
		isSolid = false;
		name = "";
	}
	
	public boolean isSolid(Entity entity) {
		return isSolid;
	}
	
	public void render() {
		
	}
	
	public void tick() {
		
	}
	
	public void logic() {
		
	}

	public boolean isSolid;
	public int id, x, y, col;
	public Level level;
	public String name;
	
}

