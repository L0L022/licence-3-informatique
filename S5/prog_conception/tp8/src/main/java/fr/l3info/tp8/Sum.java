package fr.l3info.tp8;

/**
 * La somme de plusieurs formules.
 *
 * @author Loïc Escales
 *
 */
public class Sum extends VariadicOperator {

	/**
	 * Construit Sum.
	 *
	 * @param formulas La liste des formules à additionner.
	 */
	public Sum(Formula... formulas) {
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

	@Override
	public <R> R accept(FormulaVisitor<R> visitor) {
		return visitor.visit(this);
	}

}
