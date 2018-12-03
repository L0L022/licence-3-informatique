package fr.l3info.tp7;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class DrawerContext {

	Drawer drawer;
	DrawerState currentState;

	public DrawerContext(Drawer drawer) {
		this.drawer = drawer;
		currentState = new NullDrawerState();
	}

	void mousePressed(MouseEvent event) {
		double x = event.getX();
		double y = event.getY();

		// drawer.getGraphicsContext2D().strokeRect(x, y, 2 * x, 2 * y);
		currentState.mousePressed(this, x, y);
	}

	void mouseReleased(MouseEvent event) {
		double x = event.getX();
		double y = event.getY();

		currentState.mouseReleased(this, x, y);
	}

	void mouseMoved(MouseEvent event) {
		double x = event.getX();
		double y = event.getY();

		currentState.mouseMoved(this, x, y);
	}

	void keyPressed(KeyEvent event) {
		switch (event.getText()) {
		case "c":
			currentState = new CircleDrawerState0();
			break;
		case "r":
			currentState = new RectangleDrawerState0();
			break;
		case "m":
			currentState = new MoveState0();
			break;
		}
	}

	void repaint() {
		drawer.repaint();
		currentState.paint(drawer.getGraphicsContext2D());
	}
}
