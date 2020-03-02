// GridBackendLooped.java

import pkg.*;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class GridBackendLooped extends GridBack {

	public GridBackendLooped (int w, int h, int m)
	{
		super(w, h, m);
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
			System.out.println();

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
					// if (i3 < 0 || i3 >= height) continue;
					for (int j3 = j - 1; j3 <= j + 1; j3++) {
						// if (j3 < 0 || j3 >= width) continue;
						// if (i3 == i && j3 == j) continue;
						// int tempX = j3, tempY = i3;
						// tempX += j3 < 0 ? width : j3 >= width ? -width : 0;
						// tempY += i3 < 0 ? height : i3 >= height ? -height : 0;
						// System.out.println(i3 % height + " " + j3 % width);
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



		// switch (grid[y][x]) {
		// 	case -1:
		// 		revealAllBombs();
		// 		break;
		// 	case 0:
		// 		revealed[y][x] = true;
		// 		gf.updateTile(x, y, "tileDown");
		// 		for (int i = y - 1; i < y + 2; i++) {
		// 			for (int j = x - 1; j < x + 2; j++) {
		// 				if (i == y && j == x) continue;
		// 				reveal(j, i);
		// 			}
		// 		}
		// 		break;
		// 	default:
		// 		revealed[y][x] = true;
		// 		gf.updateTile(x, y, "tileDown");
		// 		gf.updateTile(x, y, "" + grid[y][x]);
		// }

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

	// public void handleClickAt (double x, double y, MouseEvent e)
	// {
	// 	double t = 16 * gf.getTileWidth();
	// 	if (firstClick) {
	// 		generate((int)(x / t), (int)(y / t));
	// 		firstClick = false;
	// 	} else {
	// 		if (SwingUtilities.isLeftMouseButton(e))
	// 			reveal((int)(x / t), (int)(y / t));
	// 		else if (SwingUtilities.isRightMouseButton(e))
	// 			flag((int)(x / t), (int)(y / t));
	// 	}
	// }

	// public void print ()
	// {
	// 	for (int i = 0; i < height; i++) {
	// 		for (int j = 0; j < width; j++) {
	// 			if (revealed[i][j]) {
	// 				if (grid[i][j] == -1) {
	// 					System.out.print("x ");
	// 				} else if (grid[i][j] == 0) {
	// 					System.out.print("  ");
	// 				} else {
	// 					System.out.print(grid[i][j] + " ");
	// 				}
	// 			} else {
	// 				System.out.print(". ");
	// 			}
	// 		}
	// 		System.out.println();
	// 	}
	// }
}
