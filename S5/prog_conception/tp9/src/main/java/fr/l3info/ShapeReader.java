package fr.l3info;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ShapeReader {
	private Map<String, ShapeSerializer<? extends Shape>> serializers;

	public ShapeReader() {

		CircleSerializer cs = new CircleSerializer();
		RectangleSerializer rs = new RectangleSerializer();

		serializers = new HashMap<>();
		serializers.put(cs.code(), cs);
		serializers.put(rs.code(), rs);
	}

	public List<Shape> read(File file) throws IOException {
		List<Shape> shapes = new ArrayList<>();

		Scanner sc = new Scanner(file);

		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			String code = line.split(" ")[0];
			shapes.add(serializers.get(code).unserialize(line));
		}

		sc.close();

		return shapes;
	}
}
