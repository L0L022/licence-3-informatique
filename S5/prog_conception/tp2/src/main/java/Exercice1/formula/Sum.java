package Exercice1.formula;

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
	public Sum(Formula[] formulas) {
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
