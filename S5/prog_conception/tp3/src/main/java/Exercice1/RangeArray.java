package Exercice1;

import java.util.Iterator;

/**
 * Tableau d'éléments dont les indices des cases sont compris dans cet
 * intervalle : [min, max].
 *
 * @author Loïc Escales
 *
 * @param <T>
 */
class RangeArray<T> implements Iterable<T> {

	private class RangeArrayIterator implements Iterator<T> {
		private int nextIndex;

		public RangeArrayIterator() {
			nextIndex = indexMin - 1;
		}

		@Override
		public T next() {
			return get(++nextIndex);
		}

		@Override
		public boolean hasNext() {
			return nextIndex < indexMax;
		}
	}

	private int indexMin, indexMax;
	private Object[] elements;

	/**
	 * Construit RangeArray.
	 *
	 * @param indexMin Le premier indice du tableau.
	 * @param indexMax Le dernier indice du tableau.
	 */
	public RangeArray(int indexMin, int indexMax) {
		assert indexMin <= indexMax;
		this.indexMin = indexMin;
		this.indexMax = indexMax;
		this.elements = new Object[size()];
	}

	/**
	 * Renvoie l'indice minimal.
	 *
	 * @return L'indice minimal.
	 */
	public int getIndexMin() {
		return indexMin;
	}

	/**
	 * Renvoie l'indice maximal.
	 *
	 * @return L'indice maximal.
	 */
	public int getIndexMax() {
		return indexMax;
	}

	/**
	 * Renvoie le nombre d'éléments contenus dans le tableau.
	 *
	 * @return Le nombre d'éléments contenus dans le tableau.
	 */
	public int size() {
		return indexMax - indexMin + 1;
	}

	/**
	 * Place un élément dans le tableau. Si l'indice n'est pas valide rien n'est
	 * fait.
	 *
	 * @param index   L'indice de la case dans le tableau.
	 * @param element L'élément à placer.
	 */
	public void set(int index, T element) {
		if (!validIndex(index)) {
			return;
		}
		elements[realIndex(index)] = element;
	}

	/**
	 * Renvoie un élément stocké dans le tableau. Si l'indice n'est pas valide null
	 * est renvoyé.
	 *
	 * @param index L'indice de la case de l'élément.
	 * @return L'élément dans le tableau.
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if (!validIndex(index)) {
			return null;
		}
		return (T) elements[realIndex(index)];
	}

	private boolean validIndex(int index) {
		return indexMin <= index && index <= indexMax;
	}

	private int realIndex(int index) {
		return index - indexMin;
	}

	@Override
	public Iterator<T> iterator() {
		return new RangeArrayIterator();
	}

}