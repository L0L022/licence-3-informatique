package Exercice4;

/**
 * La formule formula puissance power.
 * 
 * @author loic
 *
 */
public class Power implements Formula {

	Formula formula;
	double power;
	
	Power(Formula formula, double power) {
		this.formula = formula;
		this.power = power;
	}
	
	@Override
	public String asString() {
		return "(" + formula.asString() + ")^" + power;
	}

	@Override
	public double asValue() {
		return Math.pow(formula.asValue(), power);
	}

}
