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

		int width = 13;
		int height = 13;

		gb = new GridBack(width, height, 30);
		// gb.print();
		// gb.generate((int)(Math.random() * width), (int)(Math.random() * height));
		// System.out.println();
		// gb.print();

		// p = new Picture(100, 100);
		// p.grow(2, 2);
		// p.grow(2, 2);
		// p.load("icon.png");
		// Canvas.pause(500);
		// p.draw();
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
