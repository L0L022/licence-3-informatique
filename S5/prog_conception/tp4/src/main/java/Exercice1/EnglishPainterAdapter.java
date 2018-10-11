package Exercice1;

import java.awt.Graphics;

public class EnglishPainterAdapter implements Painter {

	private EnglishPainter painter;

	public EnglishPainterAdapter(Graphics graphics) {
		painter = new EnglishPainter(graphics);
	}

	@Override
	public void drawRectangle(int x, int y, int width, int height) {
		painter.drawRectangle(new Point(x, y), new Point(x + width, y + height));
	}

	@Override
	public void drawCircle(int x, int y, int radius) {
		painter.drawCircle(new Point(x, y), radius);
	}

}
