package fr.l3info.tp7;

import javafx.scene.canvas.GraphicsContext;

public class RectangleDrawerState1 implements DrawerState {

	private Rectangle rectangle;
	private double origin_x, origin_y;

	public RectangleDrawerState1(double origin_x, double origin_y) {
		rectangle = new Rectangle(origin_x, origin_y, 0, 0);
		this.origin_x = origin_x;
		this.origin_y = origin_y;
	}

	@Override
	public void mousePressed(DrawerContext context, double x, double y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(DrawerContext context, double x, double y) {
		rectangle.width = Math.abs(x - origin_x);
		rectangle.height = Math.abs(y - origin_y);
		rectangle.x = Math.min(origin_x, x);
		rectangle.y = Math.min(origin_y, y);

		context.drawer.add(rectangle);
		context.currentState = new RectangleDrawerState0();
		context.repaint();
	}

	@Override
	public void mouseMoved(DrawerContext context, double x, double y) {
		rectangle.width = Math.abs(x - origin_x);
		rectangle.height = Math.abs(y - origin_y);
		rectangle.x = Math.min(origin_x, x);
		rectangle.y = Math.min(origin_y, y);
		context.repaint();
	}

	@Override
	public void paint(GraphicsContext context) {
		rectangle.paint(context);
	}

}
