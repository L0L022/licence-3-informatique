package Exercice1.curve;

import Exercice1.formula.Formula;
import Exercice1.formula.Variable;

/**
 * Une fonction qui peut être calculé.
 *
 * @author Loïc Escales
 *
 */
public class Function {
	private Formula formula;
	private Variable variable;

	/**
	 * Construit Function.
	 *
	 * @param formula  La formuler à calculer.
	 * @param variable La variable à changer lors du calcul.
	 */
	public Function(Formula formula, Variable variable) {
		this.formula = formula;
		this.variable = variable;
	}

	/**
	 * Retourne l'évaluation de la formule après avoir changé la variable.
	 *
	 * @param variable La valeur de la variable.
	 * @return Le résultat de la fonction.
	 */
	public double calculate(double variable) {
		this.variable.set(variable);
		return formula.asValue();
	}
}
