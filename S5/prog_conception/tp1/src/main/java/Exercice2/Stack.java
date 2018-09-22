package Exercice2;

import Exercice1.Vector;

/**
 * Représente une pile d'entiers.
 * 
 * @author loic
 *
 */
public class Stack {
	
	/**
	 * Les entiers empilés
	 */
	private Vector elements;
	
	Stack() {
		elements = new Vector();
	}
	
	/**
	 * Empile value.
	 * 
	 * @param value Valeur empilée.
	 */
	void push(int value) {
		elements.add(value);
	}
	
	/**
	 * Retourne l'entier en haut de la pile.
	 * 
	 * @return entier en haut de la pile.
	 */
	int peek() {
		return elements.get(elements.size() - 1);
	}
	
	/**
	 * Dépile l'entier en haut de la pile et le retourne.
	 * 
	 * @return l'entier dépilé.
	 */
	int pop() {
		int last = peek();
		elements.resize(elements.size() - 1);
		return last;
	}
	
	/**
	 * Retourne le nombre d'entiers dans la pile.
	 * 
	 * @return le nombre d'entiers dans la pile.
	 */
	int size() {
		return elements.size();
	}
	
	/**
	 * Indique si la pile est vide.
	 * 
	 * @return vrai si la pile est vide sinon faux.
	 */
	boolean isEmpty() {
		return elements.isEmpty();
	}
}
