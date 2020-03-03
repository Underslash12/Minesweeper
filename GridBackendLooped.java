// GridBackendLooped.java

import pkg.*;
import java.util.Arrays;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class GridBackendLooped extends GridBack implements InputKeyControl {

	private boolean spaceIsPressed = false;

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
		// assures that
		if (gameOver) return;

		// x += x < 0 ? width : x >= width ? -width : 0;
		// y += y < 0 ? height : y >= height ? -height : 0;
		x = (x + width) % width;
		y = (y + height) % height;
		// checks if not revealed and within bounds
		if (
			// (x < 0 || y < 0 || x >= width || y >= height) ||
			(revealed[y][x] || flagged[y][x])) {

			return;
		}

		if (grid[y][x] == -1) {
			revealAllBombs();
			gf.updateTile(x, y, "mineExploded");
		} else if (grid[y][x] == 0) {
			revealed[y][x] = true;
			gf.updateTile(x, y, "tileDown");
			// gf.updateTile(x, y, "" + grid[y][x]);
			for (int i = y - 1; i < y + 2; i++) {
				for (int j = x - 1; j < x + 2; j++) {
					if (i == y && j == x) continue;
					reveal(j, i);
				}
			}
		} else {
			revealed[y][x] = true;
			gf.updateTile(x, y, "tileDown");
			gf.updateTile(x, y, "" + grid[y][x]);
		}
	}

	// private void shift (int x, int y)
	// {
	//
	// }

	@Override
	public void onMousePress(double x, double y, MouseEvent e)
	{
		if (!spaceIsPressed) {
			handleClickAt(x, y, e);
		} else {
			gf.setPreviousMousePosition(x, y);
		}
	}

	@Override
	public void onMouseDrag(double x, double y, MouseEvent e)
	{
		if (!spaceIsPressed) {
			if (SwingUtilities.isLeftMouseButton(e))
				handleClickAt(x, y, e);
		} else {
			// s
			int[] vect = gf.willMove(x, y);
			// if (tx.intValue() != 0) shiftRight(tx.intValue());
			// if (ty.intValue() != 0) shiftDown(ty.intValue());
			// for (int[] a : grid) {
			// 	System.out.println(Arrays.toString(a));
			// }
			// System.out.println();
			// for (Integer[] a : MineUtilities.intMatrixToInteger(grid)) {
			// 	System.out.println(Arrays.toString(a));
			// }
			// System.out.println();
			Integer[][] tempI = MineUtilities.intMatrixToInteger(grid);
			Boolean[][] tempA = MineUtilities.booleanMatrixToInteger(revealed);
			Boolean[][] tempB = MineUtilities.booleanMatrixToInteger(flagged);
			MineUtilities.shiftMatrix(tempI, vect[0], vect[1]);
			MineUtilities.shiftMatrix(tempA, vect[0], vect[1]);
			MineUtilities.shiftMatrix(tempB, vect[0], vect[1]);
			MineUtilities.setIntFromInt(grid, tempI);
			MineUtilities.setBoolFromBool(revealed, tempA);
			MineUtilities.setBoolFromBool(flagged, tempB);
			// for (int[] a : grid) {
			// 	System.out.println(Arrays.toString(a));
			// }
			// System.out.println();
			// for (Integer[] a : MineUtilities.intMatrixToInteger(grid)) {
			// 	System.out.println(Arrays.toString(a));
			// }
			// System.out.println();
			// MineUtilities.shiftMatrix(MineUtilities.booleanMatrixToInteger(revealed), vect[0], vect[1]);
			// MineUtilities.shiftMatrix(MineUtilities.booleanMatrixToInteger(flagged), vect[0], vect[1]);
			gf.shift(vect[0], vect[1]);
		}
	}

	public void keyPress(String es)
	{
		if (es.equals(" ")) {
			spaceIsPressed = true;
		}
	}

	public void keyRelease(String es)
	{
		if (es.equals(" ")) {
			spaceIsPressed = false;
		}
	}
}
