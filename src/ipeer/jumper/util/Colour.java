package ipeer.jumper.util;

import java.awt.Color;
import java.awt.color.ColorSpace;

public class Colour extends Color {

	private static final long serialVersionUID = -1378083411733496296L;

	public Colour(int rgb) {
		super(rgb);
		
	}

	public Colour(int rgba, boolean hasalpha) {
		super(rgba, hasalpha);
		
	}

	public Colour(int r, int g, int b) {
		super(r, g, b);
		
	}

	public Colour(float r, float g, float b) {
		super(r, g, b);
		
	}

	public Colour(ColorSpace cspace, float[] components, float alpha) {
		super(cspace, components, alpha);
		
	}

	public Colour(int arg0, int arg1, int arg2, int arg3) {
		super(arg0, arg1, arg2, arg3);
		
	}

	public Colour(float r, float g, float b, float a) {
		super(r, g, b, a);
		
	}
	
	public static Colour GUIBUTTONDISABLED = new Colour(808080);
	public static Colour GUIBUTTON = new Colour(808080);

}
