// GridBackendLooped.java

import pkg.*;
import java.util.Arrays;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class GridBackendLooped extends GridBack {

	private boolean rightClickHeld = false;

	public GridBackendLooped (int w, int h, int m)
	{
		super(w, h, m);
		gf.undraw();
		// gf = new GridFront(w, h);
		gf = new GridFrontendLooped(w, h);
		gf.draw();
		KeyController kC = new KeyController(Canvas.getInstance(), this);
	}

	@Override
	protected void generateMines (int x, int y)
	{
		int[] xValues = new int[9];
		int[] yValues = new int[9];
		int c = 0;
		for (int i = y - 1; i <= y + 1; i++) {
			for (int j = x - 1; j <= x + 1; j++) {
				xValues[c] = (j + width) % width;
				yValues[c] = (i + height) % height;
				// System.out.println(xValues[c] + " " + yValues[c]);
				c++;
			}
		}

		check:
		for (int i = 0; i < totalMines; i++) {
			int tempX = (int)(Math.random() * width);
			int tempY = (int)(Math.random() * height);

			for (int k = 0; k < 9; k++) {
				// if (i == 0)
				// System.out.println(tempX + " " + tempY + " | " + xValues[k] + " " + yValues[k] +
				// " | " + tempX == xValues[k] + " " + tempY == yValues[k]);
				if (tempX == xValues[k] && tempY == yValues[k]) {
					i--;
					continue check;
				}
			}
			// System.out.println();

			if (grid[tempY][tempX] == -1){
				i--;
				continue;
			}

			grid[tempY][tempX] = -1;
		}
	}

	// loops through each tile, skipping if
	// a mine, and summing all surrounding
	// mines
	@Override
	protected void fillInTiles (int x, int y)
	{
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] == -1) continue;
				int bombCount = 0;
				for (int i3 = i - 1; i3 <= i + 1; i3++) {
					for (int j3 = j - 1; j3 <= j + 1; j3++) {
						if (grid[(i3 + height) % height][(j3 + width) % width] == -1) bombCount++;
					}
				}
				grid[i][j] = bombCount;
			}
		}
	}

	@Override
	public void reveal (int x, int y)
	{
		x = (x + width) % width;
		y = (y + height) % height;

		super.reveal(x, y);
	}

	@Override
	public boolean revealPreconditions (int x, int y)
	{
		if (gameOver) return false;
		if (revealed[y][x] || flagged[y][x]) {
			return false;
		}
		return true;
	}

	@Override
	public void onMousePress(double x, double y, MouseEvent e)
	{
		if (!rightClickHeld) {
			if (SwingUtilities.isRightMouseButton(e)) {
				rightClickHeld = true;
				gf.setPreviousMousePosition(x, y);
			}
			handleClickAt(x, y, e);
		}
	}

	@Override
	public void onMouseRelease(double x, double y, MouseEvent e)
	{
		if (rightClickHeld) {
			if (SwingUtilities.isRightMouseButton(e)) {
				rightClickHeld = false;
			}
		}
	}

	@Override
	public void onMouseDrag(double x, double y, MouseEvent e)
	{
		if (!rightClickHeld) {
			if (SwingUtilities.isLeftMouseButton(e))
				handleClickAt(x, y, e);
		} else {
			// does jank to shift grid, revealed, and flagged by a vector
			int[] vect = gf.willMove(x, y);
			Integer[][] tempI = MineUtilities.intMatrixToInteger(grid);
			Boolean[][] tempA = MineUtilities.booleanMatrixToInteger(revealed);
			Boolean[][] tempB = MineUtilities.booleanMatrixToInteger(flagged);
			MineUtilities.shiftMatrix(tempI, vect[0], vect[1]);
			MineUtilities.shiftMatrix(tempA, vect[0], vect[1]);
			MineUtilities.shiftMatrix(tempB, vect[0], vect[1]);
			MineUtilities.setIntFromInt(grid, tempI);
			MineUtilities.setBoolFromBool(revealed, tempA);
			MineUtilities.setBoolFromBool(flagged, tempB);
			gf.shift(vect[0], vect[1]);
		}
	}

	// @Override
	// public void keyPress(String es)
	// {
	// 	if (es.equals(" ")) {
	// 		spaceIsPressed = true;
	// 	}
	// }
	//
	// @Override
	// public void keyRelease(String es)
	// {
	// 	if (es.equals(" ")) {
	// 		spaceIsPressed = false;
	// 	}
	// }
}
