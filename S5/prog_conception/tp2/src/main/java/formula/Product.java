package formula;

public class Product extends VariadicOperator {

	Product(Formula[] formulas) {
		super(formulas);
	}

	@Override
	protected String symbol() {
		return "*";
	}

	@Override
	protected double initialValue() {
		return 1;
	}

	@Override
	protected double cumulativeValue(double accumulator, double value) {
		return accumulator * value;
	}

}
