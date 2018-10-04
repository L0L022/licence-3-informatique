package Exercice1.formula;

/**
 * La racine carrée d'une formule.
 *
 * @author Loïc Escales
 *
 */
public class SquareRoot implements Formula {

	private Formula formula;

	/**
	 * Construit SquareRoot.
	 *
	 * @param formula La formule en entrée.
	 */
	public SquareRoot(Formula formula) {
		this.formula = formula;
	}

	@Override
	public String asString() {
		return "√(" + formula.asString() + ")";
	}

	@Override
	public double asValue() {
		return Math.sqrt(formula.asValue());
	}

}
