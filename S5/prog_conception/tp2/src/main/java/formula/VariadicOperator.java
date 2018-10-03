
package formula;

public abstract class VariadicOperator implements Formula {
	Formula[] formulas;
	
	VariadicOperator(Formula[] formulas) {
		this.formulas = formulas;
	}
	
	protected abstract String symbol();
	protected abstract double initialValue();
	protected abstract double cumulativeValue(double accumulator, double value);
	
	@Override
	public String asString() {
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < formulas.length; ++i) {
			if (i != 0) {
				sb.append(symbol());
			}
			sb.append(formulas[i].asString());
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public double asValue() {
		double value = initialValue();
		for (int i = 0; i < formulas.length; ++i) {
			value = cumulativeValue(value, formulas[i].asValue());
		}
		return value;
	}
}
