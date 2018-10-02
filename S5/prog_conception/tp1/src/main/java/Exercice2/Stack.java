package Exercice2;

import Exercice1.Vector;

/**
 * Représente une pile d'entiers.
 */
public class Stack {

	/**
	 * Les entiers empilés.
	 */
	private Vector elements;

	/**
	 * Construit une pile vide d'entier.
	 */
	public Stack() {
		elements = new Vector();
	}

	/**
	 * Empile value.
	 *
	 * @param value Valeur qui est empilée.
	 */
	public void push(int value) {
		elements.add(value);
	}

	/**
	 * Retourne l'entier qui est en haut de la pile.
	 *
	 * @return L'entier en haut de la pile.
	 */
	public int peek() {
		return elements.get(elements.size() - 1);
	}

	/**
	 * Dépile l'entier en haut de la pile et le renvoie.
	 *
	 * @return L'entier dépilé.
	 */
	public int pop() {
		int last = peek();
		elements.resize(elements.size() - 1);
		return last;
	}

	/**
	 * Retourne le nombre d'entiers dans la pile.
	 *
	 * @return Le nombre d'entiers dans la pile.
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
