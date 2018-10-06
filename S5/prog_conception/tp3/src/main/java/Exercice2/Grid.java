package Exercice2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Une grille d'éléments.
 *
 * @author Loïc Escales
 *
 * @param <T>
 */
class Grid<T> implements Iterable<T> {

	private class GridIterator implements Iterator<T> {

		private int nextLine, nextColumn;

		public GridIterator() {
			nextLine = 0;
			nextColumn = 0;
		}

		@Override
		public boolean hasNext() {
			return nextLine < nbLines();
		}

		@Override
		public T next() {
			if (nextLine > nbLines()) {
				throw new NoSuchElementException();
			}

			T element = get(nextLine, nextColumn);

			++nextColumn;

			if (nextColumn >= nbColumns()) {
				nextColumn = 0;
				++nextLine;
			}

			return element;
		}

	}

	private T[][] elements;

	/**
	 * Contruit Grid.
	 *
	 * @param elements Une grille d'éléments.
	 */
	public Grid(T[][] elements) {
		assert elements != null;
		assert elements.length > 0;
		int line_length = elements[0].length;
		for (T[] line : elements) {
			assert line.length == line_length;
		}

		this.elements = elements;
	}

	/**
	 * Renvoie le nombre de lignes.
	 *
	 * @return Le nombre de lignes.
	 */
	public int nbLines() {
		return elements.length;
	}

	/**
	 * Renvoie le nombre de colonnes.
	 *
	 * @return Le nombre de colonnes.
	 */
	public int nbColumns() {
		return elements[0].length;
	}

	/**
	 * Renvoie un élément.
	 *
	 * @param line   La ligne de l'élément.
	 * @param column La colonne de l'élément.
	 * @return L'élément.
	 */
	public T get(int line, int column) {
		return elements[line][column];
	}

	@Override
	public Iterator<T> iterator() {
		return new GridIterator();
	}
}