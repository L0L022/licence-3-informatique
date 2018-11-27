package Exercice2;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Point2D;

public abstract class AbstractShape implements Shape {

	private List<Point2D> points;

	public AbstractShape() {
		points = new ArrayList<Point2D>();
	}

	public void addPoint(Point2D point) {
		points.add(point);
	}

	@Override
	public Point2D point(int index) {
		return points.get(index);
	}

	@Override
	public int pointsCount() {
		return points.size();
	}
}
