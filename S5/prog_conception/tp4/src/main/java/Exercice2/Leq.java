package Exercice2;

/**
 * L'entier est inférieur ou égal à une valeur.
 *
 * @author Loïc Escales
 *
 */
public class Leq implements Predicate {
	public int value;

	/**
	 * Contruit Leq.
	 *
	 * @param value La valeur qui doit être suppérieur à l'entier.
	 */
	public Leq(int value) {
		this.value = value;
	}

	@Override
	public boolean test(int i) {
		return i <= value;
	}

}
