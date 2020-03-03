// GridFront.java

import pkg.*;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

public class GridFrontendLooped extends GridFront {

	private double prevX, prevY;

	public GridFrontendLooped (int w, int h)
	{
		super(w, h);
	}

	@Override
	public void setPreviousMousePosition (double x, double y)
	{
		prevX = x;
		prevY = y;
	}

	@Override
	public int[] willMove (double x, double y)
	{
		double t = 16 * getTileWidth();
		int xChange = (int)(x / t) - (int)(prevX / t);
		int yChange = (int)(y / t) - (int)(prevY / t);
		setPreviousMousePosition(x, y);
		return new int[]{xChange, yChange};
	}

	@Override
	public void shift (int x, int y)
	{
		// Point[][] a1, a2;
		// for (int i = 0; i < layer1.length; i++) {
		// 	for (int ij = 0; j < layer1[0].length; j++) {
		// 		a1
		// 	}
		// }
		MineUtilities.shiftMatrix(layer1, x , y);
		MineUtilities.shiftMatrix(layer2, x , y);
		int s = (int)(16 * tileWidth);
		for (int i = 0; i < layer1.length; i++) {
			for (int j = 0; j < layer1[0].length; j++) {
				layer1[i][j].translate(s * j - layer1[i][j].getX(), s * i - layer1[i][j].getY());
				layer2[i][j].translate(s * j - layer2[i][j].getX(), s * i - layer2[i][j].getY());
			}
		}
	}
}
