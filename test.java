// test.java

import pkg.*;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class test {

	static GridBack gb;
	// static Picture p;
	// static int c = 0;

	public static void main(String args[])
	{
		int width = 6;
		int height = 6;

		gb = new GridBackendLooped(width, height, (int)(width * height * 0.18));
	}
}
