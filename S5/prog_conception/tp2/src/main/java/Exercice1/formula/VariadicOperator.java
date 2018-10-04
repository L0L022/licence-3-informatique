
package Exercice1.formula;

/**
 * Une opération sur plusieurs formules.
 *
 * @author Loïc Escales
 *
 */
public abstract class VariadicOperator implements Formula {
	private Formula[] formulas;

	/**
	 * Construit VariadicOperator.
	 *
	 * @param formulas La liste des formules sur lesquelles appliquer l'opération.
	 */
	public VariadicOperator(Formula[] formulas) {
		this.formulas = formulas;
	}

	/**
	 * Renvoie le symbole de l'opération. Il est utilisé pour la représentation
	 * textuelle de la formule.
	 *
	 * @return Le symbole de l'opération.
	 */
	protected abstract String symbol();

	/**
	 * Renvoie l'élément neutre de l'opération.
	 *
	 * @return Élément neutre de l'opération.
	 */
	protected abstract double initialValue();

	/**
	 * Renvoie le résultat de l'opération sur deux éléments. Il est utilisé pour
	 * l'évaluation du résultat de la formule.
	 *
	 * @param accumulator Le premier élément.
	 * @param value Le second élément.
	 * @return Le résultat de l'opération.
	 */
	protected abstract double cumulativeValue(double accumulator, double value);

	@Override
	public String asString() {
		StringBuilder sb = new StringBuilder("(");
		for (int i = 0; i < formulas.length; ++i) {
			if (i != 0) {
				sb.append(symbol());
			}
			sb.append(formulas[i].asString());
		}
		sb.append(")");
		return sb.toString();
	}

	@Override
	public double asValue() {
		double value = initialValue();
		for (int i = 0; i < formulas.length; ++i) {
			value = cumulativeValue(value, formulas[i].asValue());
		}
		return value;
	}
}
