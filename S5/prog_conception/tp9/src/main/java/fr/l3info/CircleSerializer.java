package fr.l3info;

public class CircleSerializer implements ShapeSerializer<Circle> {

	@Override
	public String code() {
		return "Circle";
	}

	@Override
	public String serialize(Circle shape) {
		return "Circle " + shape.x + " " + shape.y + " " + shape.radius;
	}

	@Override
	public Circle unserialize(String s) {
		String[] info = s.split(" ");
		return new Circle(Double.parseDouble(info[1]), Double.parseDouble(info[2]), Double.parseDouble(info[3]));
	}

}
