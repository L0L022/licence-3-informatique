package fr.l3info.tp7;

import javafx.scene.canvas.GraphicsContext;

public class MoveState0 implements DrawerState {

	@Override
	public void mousePressed(DrawerContext context, double x, double y) {
		Shape s = context.drawer.shapeContaining(x, y);
		if (s == null) {
			return;
		}
		context.currentState = new MoveState1(s, x, y);

	}

	@Override
	public void mouseReleased(DrawerContext context, double x, double y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(DrawerContext context, double x, double y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(GraphicsContext context) {
		// TODO Auto-generated method stub

	}

}
