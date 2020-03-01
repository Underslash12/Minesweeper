package pkg;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseController{

    private Canvas c = null;
    private InputControl iC;

    public MouseController(Canvas c, InputControl iC){
        this.c = c;
        this.iC = iC;
        System.out.println("Mouse Started");
        handleClick();
    }

    private void handleClick(){
        this.c.frame.getContentPane().addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
				int x = e.getX();
                int y = e.getY();
                iC.onMouseClick(x, y, e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
				// System.out.println(e);
				int x = e.getX();
                int y = e.getY();
                iC.onMousePress(x, y, e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
				int x = e.getX();
                int y = e.getY();
                iC.onMouseRelease(x, y, e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
				int x = e.getX();
                int y = e.getY();
                iC.onMouseEnter(x, y, e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
				int x = e.getX();
                int y = e.getY();
                iC.onMouseExit(x, y, e);
            }
        });
		this.c.frame.getContentPane().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
				int x = e.getX();
                int y = e.getY();
				iC.onMouseDrag(x, y, e);
            }

			@Override
			public void mouseMoved(MouseEvent e) {
				int x = e.getX();
                int y = e.getY();
				iC.onMouseMove(x, y, e);
			}
        });

    }

}