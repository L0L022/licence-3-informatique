import java.util.Objects;

/**
 * Un littéral identifié par son label et son signe.
 *
 * @author Loïc Escales
 *
 */
public class Literal {
	private String label;
	private boolean is_negative;

	/**
	 * Construit un littéral.
	 *
	 * @param label       Le label du littéral.
	 * @param is_negative Si le signe du littéral est négatif.
	 */
	public Literal(String label, boolean is_negative) {
		assert label != null;

		this.label = label;
		this.is_negative = is_negative;
	}

	/**
	 * Construit un littéral positif.
	 *
	 * @param label Le label du littéral.
	 */
	public Literal(String label) {
		this(label, false);
	}

	/**
	 * Renvoie l'opposé du littéral.
	 *
	 * @return L'opposé du littéral.
	 */
	public Literal not() {
		return new Literal(label, !is_negative);
	}

	/**
	 * Renvoie le littéral s'il est positif ou son opposé s'il est négatif.
	 *
	 * @return Le littéral s'il est positif ou son opposé s'il est négatif.
	 */
	public Literal abs() {
		if (is_negative) {
			return not();
		} else {
			return this;
		}
	}

	/**
	 * Indique si le littéral est négatif.
	 *
	 * @return Vrai si le littéral est négatif sinon faux.
	 */
	public boolean isNegative() {
		return is_negative;
	}

	@Override
	public String toString() {
		if (is_negative) {
			return "¬" + label;
		} else {
			return label;
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Literal)) {
			return false;
		}

		Literal lit = (Literal) o;

		return is_negative == lit.is_negative && label.equals(lit.label);
	}

	@Override
	public int hashCode() {
		return Objects.hash(is_negative, label);
	}
}
