package Exercice1;

import java.awt.Graphics;

public class EnglishPainter {
	private Graphics graphics;

	public EnglishPainter(Graphics graphics) {
		this.graphics = graphics;
	}

	void drawRectangle(Point p1, Point p2) {
		graphics.drawRect(p1.x, p1.y, p2.x - p1.x, p2.y - p1.y);
	}

	void drawCircle(Point c, int r) {
		graphics.drawOval(c.x - r, c.y - r, r * 2, r * 2);
	}
}
