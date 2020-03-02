// GridBack.java

import pkg.*;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class GridBack {

	private int totalMines;
	private boolean firstClick = true;
	private boolean gameOver = false;
	// values in grid
	// 0 to 8 for mines surrounding it
	// -1 if bomb
	private int[][] grid;
	private boolean[][] revealed;
	private boolean[][] flagged;
	private GridFront gf;

	public GridBack (int w, int h, int m)
	{
		totalMines = m;
		grid = new int[h][w];
		revealed = new boolean[h][w];
		flagged = new boolean[h][w];
		gf = new GridFront(w, h);
		gf.draw();
	}

	public void generate (int x, int y)
	{
		generateMines(x, y);
		fillInTiles(x, y);
		reveal(x, y);
	}

	private void generateMines (int x, int y)
	{
		for (int i = 0; i < totalMines; i++) {
			int tempX = (int)(Math.random() * grid[0].length);
			int tempY = (int)(Math.random() * grid.length);

			if (tempX >= x - 1 && tempX <= x + 1 && tempY >= y - 1 && tempY <= y + 1) {
				i--;
				continue;
			} else if (grid[tempY][tempX] == -1){
				i--;
				continue;
			}

			grid[tempY][tempX] = -1;
		}
	}

	// loops through each tile, skipping if
	// a mine, and summing all surrounding
	// mines
	private void fillInTiles (int x, int y)
	{
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == -1) continue;
				int bombCount = 0;
				for (int i3 = i - 1; i3 <= i + 1; i3++) {
					if (i3 < 0 || i3 >= grid.length) continue;
					for (int j3 = j - 1; j3 <= j + 1; j3++) {
						if (j3 < 0 || j3 >= grid[0].length) continue;
						// if (i3 == i && j3 == j) continue;
						if (grid[i3][j3] == -1) bombCount++;
					}
				}
				grid[i][j] = bombCount;
			}
		}
	}

	public void reveal (int x, int y)
	{
		// assures that
		if (gameOver) return;

		// checks if not revealed and within bounds
		if (
			(x < 0 || y < 0 || x >= grid[0].length || y >= grid.length) ||
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

	public void revealAllBombs ()
	{
		gameOver = true;
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (grid[i][j] == -1) {
					revealed[i][j] = true;
					gf.updateTile(j, i, "tileDown");
					gf.updateTile(j, i, "mine");
				}
			}
		}
	}

	public void flag (int x, int y)
	{
		if (gameOver) return;

		if (!revealed[y][x]) {
			flagged[y][x] = !flagged[y][x];
			gf.updateTile(x, y, flagged[y][x] ? "flag" : "blankTile");
		}
	}

	public void handleClickAt (double x, double y, MouseEvent e)
	{
		double t = 16 * gf.getTileWidth();
		if (firstClick) {
			generate((int)(x / t), (int)(y / t));
			firstClick = false;
		} else {
			if (SwingUtilities.isLeftMouseButton(e))
				reveal((int)(x / t), (int)(y / t));
			else if (SwingUtilities.isRightMouseButton(e))
				flag((int)(x / t), (int)(y / t));
		}
	}

	public void print ()
	{
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				if (revealed[i][j]) {
					if (grid[i][j] == -1) {
						System.out.print("x ");
					} else if (grid[i][j] == 0) {
						System.out.print("  ");
					} else {
						System.out.print(grid[i][j] + " ");
					}
				} else {
					System.out.print(". ");
				}
			}
			System.out.println();
		}
	}
}
