package Exercice4;

public class FormulaMain {

	public static void main(String[] args) {
		Variable variable = new Variable("variable", 0);
		Formula formula = new Square(variable);
		generatePoints(formula, variable, -5, 10.0, 1.5);
	}

	private static void generatePoints(Formula formula, Variable variable, double startValue, double endValue,
			double step) {
		for (double value = startValue; value <= endValue; value += step) {
			variable.set(value);
			System.out.println(value + " " + formula.asValue());
		}
	}
}
