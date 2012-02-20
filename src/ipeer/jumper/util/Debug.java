package ipeer.jumper.util;

import java.io.InputStream;
import java.net.URL;

public class Debug {

	public static void p(String s) {
		System.out.println(s);
	}
	
	public static void p(float f) {
		System.out.println(f);
	}

	public static void p(int i) {
		System.out.println(i);
	}

	public static void p(InputStream in) {
		System.out.println(in);
	}

	@SuppressWarnings("rawtypes")
	public static void p(Class c) {
		System.out.println(c);
	}

	public static void p(URL url) {
		System.out.println(url);
	}

	public static void p(boolean b) {
		System.out.println(b);
	}
	
}
