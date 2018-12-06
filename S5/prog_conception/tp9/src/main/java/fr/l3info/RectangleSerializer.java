package fr.l3info;

public class RectangleSerializer implements ShapeSerializer<Rectangle> {

	@Override
	public String code() {
		return "Rectangle";
	}

	@Override
	public String serialize(Rectangle shape) {
		return "Rectangle " + shape.x + " " + shape.y + " " + shape.width + " " + shape.height;
	}

	@Override
	public Rectangle unserialize(String s) {
		String[] info = s.split(" ");
		return new Rectangle(Double.parseDouble(info[1]), Double.parseDouble(info[2]), Double.parseDouble(info[3]),
				Double.parseDouble(info[4]));
	}

}
