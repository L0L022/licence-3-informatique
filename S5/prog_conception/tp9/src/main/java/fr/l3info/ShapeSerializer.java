package fr.l3info;

public interface ShapeSerializer<S extends Shape> {
	String code();

	String serialize(S shape);

	S unserialize(String s);
}
