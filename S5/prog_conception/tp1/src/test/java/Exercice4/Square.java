package Exercice4;

/**
 * La formule puissance deux.
 * 
 * @author loic
 *
 */
public class Square implements Formula {

	Formula formula;
	
	Square(Formula formula) {
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
