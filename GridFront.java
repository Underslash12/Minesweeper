// GridFront.java

import pkg.*;
import java.util.Hashtable;
import java.awt.image.BufferedImage;
import java.awt.Dimension;

public class GridFront {

	// private Hashtable<String, String> images;
	protected Hashtable<String, BufferedImage> images;
	// for tileUp and tileDown
	protected Picture[][] layer1;
	// for everything else
	protected Picture[][] layer2;

	protected double tileWidth;

	public GridFront (int w, int h)
	{
		layer1 = new Picture[h][w];
		layer2 = new Picture[h][w];
		// System.out.println("a");
		setTileWidth();
		// System.out.println("b");
		createHashTable();
		createTiles();
	}

	public void createHashTable ()
	{
		// images = new Hashtable<String, String>();
		images = new Hashtable<String, BufferedImage>();
		images.put("tileUp", new Picture("sprites/tileUp.png").getImage());
		images.put("tileDown", new Picture("sprites/tileDown.png").getImage());
		images.put("blankTile", new Picture("sprites/blankTile.png").getImage());
		images.put("mine", new Picture("sprites/mine.png").getImage());
		images.put("mineExploded", new Picture("sprites/mineExploded.png").getImage());
		images.put("flag", new Picture("sprites/flag.png").getImage());
		images.put("1", new Picture("sprites/1.png").getImage());
		images.put("2", new Picture("sprites/2.png").getImage());
		images.put("3", new Picture("sprites/3.png").getImage());
		images.put("4", new Picture("sprites/4.png").getImage());
		images.put("5", new Picture("sprites/5.png").getImage());
		images.put("6", new Picture("sprites/6.png").getImage());
		images.put("7", new Picture("sprites/7.png").getImage());
		images.put("8", new Picture("sprites/8.png").getImage());
	}

	public void createTiles ()
	{
		Picture p = new Picture();
		p.setImage(images.get("blankTile"));
		p.grow(tileWidth, tileWidth);
		int s = p.getWidth();

		for (int i = 0; i < layer1.length; i++) {
			for (int j = 0; j < layer2.length; j++) {
				layer1[i][j] = new Picture();
				layer1[i][j].grow(tileWidth, tileWidth);
				layer1[i][j].translate(j * s, i * s);
				layer1[i][j].setImage(images.get("tileUp"));
				layer2[i][j] = new Picture();
				layer2[i][j].grow(tileWidth, tileWidth);
				layer2[i][j].translate(j * s, i * s);
				layer2[i][j].setImage(images.get("blankTile"));
			}
		}
	}

	public void updateTile (int x, int y, String val)
	{
		// System.out.println(x + " " + y + " " + val);
		if (val.equals("tileUp") || val.equals("tileDown")) {
			layer1[y][x].setImage(images.get(val));
		} else {
			layer2[y][x].setImage(images.get(val));
		}

		// if (draw) draw();
	}

	public void draw ()
	{
		for (int i = 0; i < layer1.length; i++) {
			for (int j = 0; j < layer2.length; j++) {
				layer1[i][j].draw();
				layer2[i][j].draw();
			}
		}
	}

	public void undraw ()
	{
		for (int i = 0; i < layer1.length; i++) {
			for (int j = 0; j < layer2.length; j++) {
				layer1[i][j].undraw();
				layer2[i][j].undraw();
			}
		}
	}

	public double getTileWidth ()
	{
		return tileWidth;
	}

	public void setTileWidth ()
	{
		Dimension d = Canvas.getInstance().getSize();
		double unitTiles = d.getWidth() / 16;
		tileWidth = unitTiles / layer1[0].length;
		// System.out.println(tileWidth + " " + unitTiles + " " + d.getWidth());
	}

	public void setPreviousMousePosition (double x, double y) {System.out.println("1");}
	public int[] willMove (double x, double y) {System.out.println("2"); return new int[]{0, 0};}
	public void shift (int x, int y) {System.out.println("3");}
}
