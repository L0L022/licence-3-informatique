package Exercice1.formula;

/**
 * Le minimum d'un ensemble de formules.
 *
 * @author Loïc Escales
 *
 */
public class Minimum implements Formula {

	private Formula[] formulas;

	/**
	 * Construit Minimum.
	 *
	 * @param formulas La liste des formules en entrée.
	 */
	public Minimum(Formula[] formulas) {
		this.formulas = formulas;
	}

	@Override
	public String asString() {
		StringBuilder sb = new StringBuilder("min(");
		for (int i = 0; i < formulas.length; ++i) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(formulas[i].asString());
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public double asValue() {
		double value = formulas[0].asValue();
		for (int i = 1; i < formulas.length; ++i) {
			value = Math.min(value, formulas[i].asValue());
		}
		return value;
	}

}
