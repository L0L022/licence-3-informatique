package Exercice1;

import java.awt.Graphics;

public class MarseillePainter implements Painter {

	private Graphics graphics;

	/**
	 * Construit MarseillePainter.
	 */
	public MarseillePainter(Graphics graphics) {
		this.graphics = graphics;
	}

	@Override
	public void drawRectangle(int x, int y, int width, int height) {
		graphics.drawRect(x, y, width, height);

	}

	@Override
	public void drawCircle(int x, int y, int radius) {
		int diameter = radius * 2;
		graphics.drawOval(x - radius, y - radius, diameter, diameter);
	}

}
