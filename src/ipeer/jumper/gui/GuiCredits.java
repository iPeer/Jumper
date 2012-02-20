package ipeer.jumper.gui;

import ipeer.jumper.engine.Engine;
import ipeer.jumper.util.Colour;
import ipeer.jumper.util.Debug;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GuiCredits extends Gui {

	public GuiCredits(Engine engine, Gui parent) {
		this.parent = parent;
		creditsposy = Engine.height + 5;
		this.engine = engine;
		this.g = Engine.g;
		String line;
		InputStream in = GuiCredits.class.getResourceAsStream("/credits.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		try {
			while ((line = br.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void tick() {
		shouldscroll--;
		if (shouldscroll == 0) {
			creditsposy--;
			shouldscroll = 4;
		}
	}
	
	public void render() {
		g = Engine.g;
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
		g.setColor(Colour.BLACK);
		g.fillRect(190, 0, (Engine.width - (190*2)), Engine.height);
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
		for (int i = 0; i < lines.size(); i++) {
			String a = lines.get(i);
			if (a.startsWith("&u")) {
				drawStringWithShadow(a.substring(2), -1, creditsposy + (i * 13), 13);
			}
			else if (a.equals("--")) {
				g.setColor(Colour.RED);	
				g.drawLine(200, creditsposy + (i * 13) - 8, Engine.width - 200, (creditsposy + (i * 13) - 8));
				g.setColor(Colour.WHITE);
			}
			else if (a.equals("Thanks for playing!")) {
				int i1 = creditsposy + (i * 13);
				int i2 = i1;
				if (i1 < (Engine.height /2))
					i2 = Engine.height / 2;
				drawStringWithShadow(a, -1, i2);
				drawStringWithShadow("Press ESC to return to menu!", -1, i2 + 16);
				if (i1 < -200)
					engine.setGUI(new GuiMainMenu(engine));
			}
			else {
				g.drawString(a, (Engine.width - g.getFontMetrics().stringWidth(a)) / 2, creditsposy + (i * 13));
			}
		}
	}

	private Engine engine;
	private Graphics2D g;
	private int creditsposy;
	private ArrayList<String> lines = new ArrayList<String>();
	private int shouldscroll = 4;
	
}
