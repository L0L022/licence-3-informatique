package Exercice2;

import javafx.scene.canvas.GraphicsContext;

public class BorderDecorator extends Decorator {

	private double radius;

	public BorderDecorator(Shape decoratedShape, double radius) {
		super(decoratedShape);
		this.radius = radius;
	}

	@Override
	protected void drawDecoration(GraphicsContext context) {
		double d = radius * 2.0;
		for (int i = 0; i < pointsCount(); ++i) {
			context.fillOval(point(i).getX() - radius, point(i).getY() - radius, d, d);
		}
	}

}
