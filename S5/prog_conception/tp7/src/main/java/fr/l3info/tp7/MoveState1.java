package fr.l3info.tp7;

import javafx.scene.canvas.GraphicsContext;

public class MoveState1 implements DrawerState {

	private Shape shape;
	private double last_x, last_y;

	public MoveState1(Shape shape, double last_x, double last_y) {
		this.shape = shape;
		this.last_x = last_x;
		this.last_y = last_y;
	}

	@Override
	public void mousePressed(DrawerContext context, double x, double y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(DrawerContext context, double x, double y) {
		context.currentState = new MoveState0();
	}

	@Override
	public void mouseMoved(DrawerContext context, double x, double y) {
		shape.translate(x - last_x, y - last_y);
		last_x = x;
		last_y = y;
		context.repaint();
	}

	@Override
	public void paint(GraphicsContext context) {
		// TODO Auto-generated method stub

	}

}
