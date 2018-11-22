package fr.l3info.tp8;

public class EvaluatorVisitor implements FormulaVisitor<Double> {
	@Override
	public Double visit(Sum sum) {
		double result = 0.0;
		for (int i = 0; i < sum.formulas.length; ++i) {
			result += sum.formulas[i].accept(this);
		}
		return result;
	}

	@Override
	public Double visit(Product product) {
		double result = 1.0;
		for (int i = 0; i < product.formulas.length; ++i) {
			result *= product.formulas[i].accept(this);
		}
		return result;
	}

	@Override
	public Double visit(Variable variable) {
		return variable.asValue();
	}

	@Override
	public Double visit(SquareRoot squareRoot) {
		return Math.sqrt(squareRoot.formula.accept(this));
	}
}
