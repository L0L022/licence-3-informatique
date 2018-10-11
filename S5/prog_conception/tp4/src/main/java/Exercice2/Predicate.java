package Exercice2;

/**
 * Un prédicat sur un entier.
 *
 * @author Loïc Escales
 *
 */
public interface Predicate {
	/**
	 * Indique si un entier test un prédicat.
	 *
	 * @param i L'entier utilisé pour tester le prédicat.
	 * @return Vrai si le prédicat est vrai sinon faux.
	 */
	boolean test(int i);
}
