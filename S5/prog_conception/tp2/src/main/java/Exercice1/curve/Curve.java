package Exercice1.curve;

import java.io.IOException;
import java.io.Writer;

public class Curve {
	private Function function;
	private double startValue, endValue, step;

	public Curve(Function function, double startValue, double endValue, double step) {
		this.function = function;
		this.startValue = startValue;
		this.endValue = endValue;
		this.step = step;
	}

	public void writePoints(Writer writer) throws IOException {
		for (double i = startValue; i < endValue; i += step) {
			writer.write(i + " " + function.calculate(i) + "\n");
		}
	}
}
