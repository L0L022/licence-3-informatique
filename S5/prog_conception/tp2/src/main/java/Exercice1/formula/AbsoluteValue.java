package Exercice1.formula;

/**
 * La valeur absolue d'une formule.
 *
 * @author Loïc Escales
 *
 */
public class AbsoluteValue implements Formula {

	private Formula formula;

	/**
	 * Construit AbsoluteValue.
	 *
	 * @param formula La formule en entrée.
	 */
	public AbsoluteValue(Formula formula) {
		this.formula = formula;
	}

	@Override
	public String asString() {
		return "|" + formula.asString() + "|";
	}

	@Override
	public double asValue() {
		return Math.abs(formula.asValue());
	}

}
