package fr.l3info;

import javafx.scene.canvas.GraphicsContext;

public class RectangleDrawerState0 implements DrawerState {

	@Override
	public void mousePressed(DrawerContext context, double x, double y) {
		context.currentState = new RectangleDrawerState1(x, y);

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
