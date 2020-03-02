// test.java

import pkg.*;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class test implements InputControl{

	static GridBack gb;
	static Picture p;
	// static int c = 0;

	public static void main(String args[])
	{
		MouseController mC = new MouseController(Canvas.getInstance(),new test());

		int width = 10;
		int height = 10;

		gb = new GridBackendLooped(width, height, (int)(width * height * 0.2));
	}

	public void onMousePress(double x, double y, MouseEvent e)
	{
		gb.handleClickAt(x, y, e);
	}
	public void onMouseRelease(double x, double y, MouseEvent e)
	{
		// p.load(c % 2 == 0 ? "sprites/tileUp.png" : "icon.png");
		// c = 1 - c;
	}
	public void onMouseDrag(double x, double y, MouseEvent e)
	{
		if (SwingUtilities.isLeftMouseButton(e))
			gb.handleClickAt(x, y, e);
	}
	public void onMouseMove(double x, double y, MouseEvent e){}
	public void onMouseEnter(double x, double y, MouseEvent e){}
	public void onMouseExit(double x, double y, MouseEvent e){}
	public void onMouseClick(double x, double y, MouseEvent e){}

}
