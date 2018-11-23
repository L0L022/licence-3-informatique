package fr.l3info.tp8;

/**
 * La racine carrée d'une formule.
 *
 * @author Loïc Escales
 *
 */
public class SquareRoot implements Formula {

	public Formula formula;

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

	@Override
	public <R> R accept(FormulaVisitor<R> visitor) {
		return visitor.visit(this);
	}

}
