package fr.l3info.tp8;

public class XMLTextVisitor implements FormulaVisitor<String> {
	@Override
	public String visit(Sum sum) {
		return visit(sum, "sum");
	}

	@Override
	public String visit(Product product) {
		return visit(product, "product");
	}

	@Override
	public String visit(Variable variable) {
		return "<var>" + variable.asString() + "</var>\n";
	}

	private String visit(VariadicOperator operator, String tag) {
		StringBuilder sb = new StringBuilder("<" + tag + ">\n");
		for (Formula formula : operator.formulas) {
			sb.append(formula.accept(this));
		}
		sb.append("</" + tag + ">\n");
		return sb.toString();
	}

	@Override
	public String visit(SquareRoot squareRoot) {
		return "<sqrt>\n" + squareRoot.formula.accept(this) + "</sqrt>\n";
	}
}
