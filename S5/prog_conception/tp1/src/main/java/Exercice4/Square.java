package Exercice4;

/**
 * Le carré d'une formule.
 *
 * @author Loïc Escales
 *
 */
public class Square implements Formula {

	private Formula formula;

	/**
	 * Construit Square.
	 *
	 * @param formula La formule en entrée.
	 */
	public Square(Formula formula) {
		this.formula = formula;
	}

	@Override
	public String asString() {
		return "(" + formula.asString() + ")²";
	}

	@Override
	public double asValue() {
		return Math.pow(formula.asValue(), 2);
	}

}
