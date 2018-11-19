package Exercice2;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Decorator implements Shape {

	private Shape decoratedShape;

	public Decorator(Shape decoratedShape) {
		this.decoratedShape = decoratedShape;
	}

	@Override
	public Point2D point(int index) {
		return decoratedShape.point(index);
	}

	@Override
	public int pointsCount() {
		return decoratedShape.pointsCount();
	}

	@Override
	public void draw(GraphicsContext context) {
		decoratedShape.draw(context);
		context.setFill(Color.BLACK);
		drawDecoration(context);
	}

	protected abstract void drawDecoration(GraphicsContext context);

}
