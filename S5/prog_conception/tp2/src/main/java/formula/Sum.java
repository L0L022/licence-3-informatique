package formula;

public class Sum extends VariadicOperator {

	Sum(Formula[] formulas) {
		super(formulas);
	}

	@Override
	protected String symbol() {
		return "+";
	}

	@Override
	protected double initialValue() {
		return 0;
	}

	@Override
	protected double cumulativeValue(double accumulator, double value) {
		return accumulator + value;
	}

}
