package pkg;

import java.awt.event.MouseEvent;

public interface InputControl{
    public void onMouseClick(double x, double y, MouseEvent e);
	public void onMousePress(double x, double y, MouseEvent e);
	public void onMouseRelease(double x, double y, MouseEvent e);
	public void onMouseEnter(double x, double y, MouseEvent e);
	public void onMouseExit(double x, double y, MouseEvent e);
	
	public void onMouseDrag(double x, double y, MouseEvent e);
	public void onMouseMove(double x, double y, MouseEvent e);
}