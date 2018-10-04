package Exercice1.formula;

/**
 * Une formule puissance x.
 *
 * @author Loïc Escales
 *
 */
public class Power implements Formula {

	private Formula formula;
	private double exponent;

	/**
	 * Construit Power.
	 *
	 * @param formula La formule en entrée.
	 * @param exponent L'exposant de la puissance.
	 */
	public Power(Formula formula, double exponent) {
		this.formula = formula;
		this.exponent = exponent;
	}

	@Override
	public String asString() {
		return "(" + formula.asString() + ")^" + exponent;
	}

	@Override
	public double asValue() {
		return Math.pow(formula.asValue(), exponent);
	}

}
