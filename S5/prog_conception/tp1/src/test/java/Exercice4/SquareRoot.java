package Exercice4;

/**
 * La racine carré de la formule.
 * 
 * @author loic
 *
 */
public class SquareRoot implements Formula {

	Formula formula;
	
	SquareRoot(Formula formula) {
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
