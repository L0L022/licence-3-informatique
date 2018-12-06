package fr.l3info;

public interface ShapeVisitor<T> {
	T visit(Circle circle);

	T visit(Rectangle rectangle);
}
