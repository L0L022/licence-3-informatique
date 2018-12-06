package fr.l3info;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle implements Shape {
	public double x, y, width, height;

	public Rectangle(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	@Override
	public void paint(GraphicsContext graphicsContext) {
		graphicsContext.fillRect(x, y, width, height);
	}

	@Override
	public boolean contains(double x, double y) {
		return this.x <= x && this.y <= y && x <= this.x + width && y <= this.y + height;
	}

	@Override
	public void translate(double dx, double dy) {
		x += dx;
		y += dy;
	}

	@Override
	public <T> T accept(ShapeVisitor<T> visitor) {
		return visitor.visit(this);
	}

}
