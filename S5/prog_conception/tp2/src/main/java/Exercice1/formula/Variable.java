package Exercice1.formula;

/**
 * Une variable avec un label et une valeur. Le label est utilisé pour la
 * représentation textuelle. La valeur est utiliser pour l'évaluation du
 * résultat.
 *
 * @author Loïc Escales
 *
 */
public class Variable implements Formula {

	private String label;
	private double value;

	/**
	 * Construit Variable.
	 *
	 * @param label Le label de la variable.
	 * @param value La valeur de la variable.
	 */
	public Variable(String label, double value) {
		this.label = label;
		this.value = value;
	}

	/**
	 * Change la valeur.
	 *
	 * @param value La nouvelle valeur.
	 */
	public void set(double value) {
		this.value = value;
	}

	@Override
	public String asString() {
		return label;
	}

	@Override
	public double asValue() {
		return value;
	}

}
