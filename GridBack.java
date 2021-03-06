// GridBack.java

import pkg.*;
import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

public class GridBack implements InputControl, InputKeyControl {

	protected int totalMines;
	protected int width;
	protected int height;

	protected boolean firstClick = true;
	protected boolean gameOver = false;
	// values in grid
	// 0 to 8 for mines surrounding it
	// -1 if bomb
	protected int[][] grid;
	protected boolean[][] revealed;
	protected boolean[][] flagged;

	protected GridFront gf;

	public GridBack (int w, int h, int m)
	{
		MouseController mC = new MouseController(Canvas.getInstance(), this);
		totalMines = m;
		width = w;
		height = h;

		grid = new int[h][w];
		revealed = new boolean[h][w];
		flagged = new boolean[h][w];

		gf = new GridFront(w, h);
		// System.out.println("a");
		gf.draw();
		// System.out.println("b");
	}

	public void generate (int x, int y)
	{
		generateMines(x, y);
		fillInTiles(x, y);
		reveal(x, y);
	}

	protected void generateMines (int x, int y)
	{
		for (int i = 0; i < totalMines; i++) {
			int tempX = (int)(Math.random() * width);
			int tempY = (int)(Math.random() * height);

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
	protected void fillInTiles (int x, int y)
	{
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] == -1) continue;
				int bombCount = 0;
				for (int i3 = i - 1; i3 <= i + 1; i3++) {
					if (i3 < 0 || i3 >= height) continue;
					for (int j3 = j - 1; j3 <= j + 1; j3++) {
						if (j3 < 0 || j3 >= width) continue;
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
		if (!revealPreconditions(x, y)) return;

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

		checkIfWon();
	}

	public boolean revealPreconditions (int x, int y)
	{
		if (gameOver) return false;

		// checks if not revealed and within bounds
		if (
			(x < 0 || y < 0 || x >= width || y >= height) ||
			(revealed[y][x] || flagged[y][x])) {

			return false;
		}

		return true;
	}

	public void revealAllBombs ()
	{
		gameOver = true;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] == -1) {
					revealed[i][j] = true;
					gf.updateTile(j, i, "tileDown");
					gf.updateTile(j, i, "mine");
				}
			}
		}

		System.out.println("You hit a mine! Oops...");
	}

	public void checkIfWon ()
	{
		boolean won = true;

		outerLoop:
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (grid[i][j] >= 0 && !revealed[i][j]) {
					won = false;
					break outerLoop;
				}
			}
		}

		if (won) {
			gameOver = true;
			System.out.println("You won!");
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
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
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

	public void onMousePress(double x, double y, MouseEvent e)
	{
		handleClickAt(x, y, e);
	}
	public void onMouseRelease(double x, double y, MouseEvent e)
	{
		// p.load(c % 2 == 0 ? "sprites/tileUp.png" : "icon.png");
		// c = 1 - c;
	}

	public void onMouseDrag(double x, double y, MouseEvent e)
	{
		if (SwingUtilities.isLeftMouseButton(e))
			handleClickAt(x, y, e);
	}

	public void onMouseMove(double x, double y, MouseEvent e){}
	public void onMouseEnter(double x, double y, MouseEvent e){}
	public void onMouseExit(double x, double y, MouseEvent e){}
	public void onMouseClick(double x, double y, MouseEvent e){}
	public void keyPress(String es){}
	public void keyRelease(String es){}
}
