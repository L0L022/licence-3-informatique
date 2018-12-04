package fr.l3info.tp7;

import javafx.scene.canvas.GraphicsContext;

public class CircleDrawerState1 implements DrawerState {

	private Circle circle;

	public CircleDrawerState1(double origin_x, double origin_y) {
		circle = new Circle(origin_x, origin_y, 0);
	}

	@Override
	public void mousePressed(DrawerContext context, double x, double y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(DrawerContext context, double x, double y) {
		circle.radius = Math.sqrt(Math.pow(circle.x - x, 2) + Math.pow(circle.y - y, 2));
		context.drawer.add(circle);
		context.currentState = new CircleDrawerState0();
		context.repaint();
	}

	@Override
	public void mouseMoved(DrawerContext context, double x, double y) {
		circle.radius = Math.sqrt(Math.pow(circle.x - x, 2) + Math.pow(circle.y - y, 2));
		context.repaint();
	}

	@Override
	public void paint(GraphicsContext context) {
		circle.paint(context);
	}

}
