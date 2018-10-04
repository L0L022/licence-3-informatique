package Exercice1.curve;

import Exercice1.formula.Formula;
import Exercice1.formula.Variable;

public class Function {
	private Formula formula;
	private Variable variable;

	public Function(Formula formula, Variable variable) {
		this.formula = formula;
		this.variable = variable;
	}

	public double calculate(double variable) {
		this.variable.set(variable);
		return formula.asValue();
	}
}
