package Exercice2;

/**
 * Représente une pile d'objets.
 */
public class Stack {

	/**
	 * Les objets empilés.
	 */
	private Vector elements;

	/**
	 * Construit une pile vide d'objets.
	 */
	public Stack() {
		elements = new Vector();
	}

	/**
	 * Empile value.
	 *
	 * @param value Valeur qui est empilée.
	 */
	public void push(Object value) {
		elements.add(value);
	}

	/**
	 * Retourne l'objet qui est en haut de la pile.
	 *
	 * @return L'objet en haut de la pile.
	 */
	public Object peek() {
		return elements.get(elements.size() - 1);
	}

	/**
	 * Dépile l'objet en haut de la pile et le renvoie.
	 *
	 * @return L'objet dépilé.
	 */
	public Object pop() {
		Object last = peek();
		elements.resize(elements.size() - 1);
		return last;
	}

	/**
	 * Retourne le nombre d'objets dans la pile.
	 *
	 * @return Le nombre d'objets dans la pile.
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Indique si la pile est vide.
	 *
	 * @return Vrai si la pile est vide sinon faux.
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}
}
