package fr.l3info;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ShapeWriter implements ShapeVisitor<String> {
	public void write(File file, List<Shape> shapes) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));

		for (Shape shape : shapes) {
			writer.write(shape.accept(this) + " \n");
		}

		writer.close();
	}

	@Override
	public String visit(Circle circle) {
		return (new CircleSerializer()).serialize(circle);
	}

	@Override
	public String visit(Rectangle rectangle) {
		return (new RectangleSerializer()).serialize(rectangle);
	}
}
