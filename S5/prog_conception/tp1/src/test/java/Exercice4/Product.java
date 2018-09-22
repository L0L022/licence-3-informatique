package Exercice4;

/**
 * Le produit de plusieurs formules.
 * 
 * @author loic
 *
 */
public class Product implements Formula {

	Formula[] formulas;
	
	Product(Formula[] formulas) {
		this.formulas = formulas;
	}
	
	@Override
	public String asString() {
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < formulas.length; ++i) {
			if (i != 0) {
				sb.append("*");
			}
			sb.append(formulas[i].asString());
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public double asValue() {
		double value = 1.0;
		for (Formula formula : formulas) {
			value *= formula.asValue();
		}
		return value;
	}

}
