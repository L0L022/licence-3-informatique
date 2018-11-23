package fr.l3info.tp8;

public class PlainTextVisitor implements FormulaVisitor<String> {
	@Override
	public String visit(Sum sum) {
		return visit(sum, "+");
	}

	@Override
	public String visit(Product product) {
		return visit(product, "*");
	}

	@Override
	public String visit(Variable variable) {
		return variable.asString();
	}

	private String visit(VariadicOperator operator, String symbol) {
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < operator.formulas.length; ++i) {
			if (i > 0) {
				sb.append(symbol);
			}
			sb.append(operator.formulas[i].accept(this));
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public String visit(SquareRoot squareRoot) {
		return "√(" + squareRoot.formula.accept(this) + ")";
	}
}
