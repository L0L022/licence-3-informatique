package Exercice2;

/**
 * Deux prédicats doivent être vrais.
 *
 * @author Loïc Escales
 *
 */
public class And implements Predicate {

	private Predicate p1, p2;

	/**
	 * Construit And.
	 *
	 * @param p1 Le premier prédicat.
	 * @param p2 Le second prédicat.
	 */
	public And(Predicate p1, Predicate p2) {
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public boolean test(int i) {
		return p1.test(i) && p2.test(i);
	}

}
