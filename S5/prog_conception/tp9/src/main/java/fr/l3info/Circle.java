package fr.l3info;

import javafx.scene.canvas.GraphicsContext;

public class Circle implements Shape {

	public double x, y, radius;

	public Circle(double x, double y, double radius) {
		this.x = x;
		this.y = y;
		this.radius = radius;
	}

	@Override
	public void paint(GraphicsContext graphicsContext) {
		graphicsContext.fillOval(x - radius, y - radius, radius * 2.0, radius * 2.0);
	}

	@Override
	public boolean contains(double x, double y) {
		return Math.sqrt(Math.pow(this.x - x, 2) + Math.pow(this.y - y, 2)) <= radius;
	}

	@Override
	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}

}
