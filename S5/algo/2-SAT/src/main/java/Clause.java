
/**
 * Une clause composée de deux littéraux.
 *
 * @author Loïc Escales
 *
 */
public class Clause {
	private Literal left, right;

	/**
	 * Construit une clause.
	 *
	 * @param left  Le littéral gauche.
	 * @param right Le littéral droit.
	 */
	public Clause(Literal left, Literal right) {
		assert left != null;
		assert right != null;

		this.left = left;
		this.right = right;
	}

	public Literal left() {
		return left;
	}

	public Literal right() {
		return right;
	}

	@Override
	public String toString() {
		return left.toString() + " ∨ " + right.toString();
	}
}
