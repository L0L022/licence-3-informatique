package Exercice2;

/**
 * L'entier est impair.
 * 
 * @author Loïc Escales
 *
 */
public class Odd implements Predicate {

	@Override
	public boolean test(int i) {
		return i % 2 == 0;
	}

}
