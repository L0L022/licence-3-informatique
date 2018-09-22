package Exercice4;


/**
 * La valeur absolue de la formule.
 * 
 * @author loic
 *
 */
public class AbsoluteValue implements Formula {

	Formula formula;
	
	AbsoluteValue(Formula formula) {
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
