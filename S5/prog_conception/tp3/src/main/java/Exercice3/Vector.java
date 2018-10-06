package Exercice3;

import java.util.Arrays;
import java.util.Iterator;

/**
 * La classe Vector implémente un tableau d'éléments de taille dynamique. Les
 * éléments du vecteur sont stockés dans un tableau. La taille de ce tableau est
 * au minimum doublée à chaque fois qu'il est nécessaire de le faire grossir.
 */
public class Vector<T> implements Iterable<T> {

	private class VectorIterator implements Iterator<T> {

		private int index;

		public VectorIterator() {
			index = 0;
		}

		@Override
		public boolean hasNext() {
			return index < size();
		}

		@Override
		public T next() {
			return get(index++);
		}

	}

	/**
	 * Tableau permettant de stocker les éléments du vecteur. Seuls les size
	 * premiers éléments font partie du vecteur. La taille de ce tableau est égale à
	 * la capacité du vecteur, c'est-à-dire, au nombre d'éléments maximum que le
	 * vecteur peut contenir sans avoir besoin d'allouer un nouveau tableau.
	 */
	private Object[] elements;

	/**
	 * Nombre d'éléments présents dans le vecteur.
	 */
	private int size;

	/**
	 * Construit un vecteur de capacité initiale initialCapacity.
	 *
	 * @param initialCapacity Capacité initiale du vecteur
	 */
	public Vector(int initialCapacity) {
		elements = new Object[initialCapacity];
		size = 0;
	}

	/**
	 * Construit un vecteur avec une capacité initiale de 10.
	 */
	public Vector() {
		this(10);
	}

	/**
	 * Augmente la capacité du vecteur si nécessaire de façon à permettre le
	 * stockage d'au moins <code>minCapacity</code> éléments. S'il est nécessaire
	 * d'augmenter la capacité du vecteur, elle est au minimum doublée.
	 *
	 * @param minCapacity Capacité minimale à assurer.
	 */
	public void ensureCapacity(int minCapacity) {
		if (minCapacity < 0) {
			return;
		}
		int oldCapacity = elements.length;
		if (oldCapacity >= minCapacity) {
			return;
		}

		int newCapacity = Math.max(oldCapacity * 2, minCapacity);
		elements = Arrays.copyOf(elements, newCapacity);
	}

	/**
	 * Change la taille du vecteur. Si la nouvelle taille est plus petite que
	 * l'ancienne alors le vecteur est réduit. Si la nouvelle taille est plus grande
	 * alors les nouveaux éléments sont initialisés à null.
	 *
	 * @param newSize La nouvelle taille du vecteur.
	 * @throws ArrayIndexOutOfBoundsException Si la nouvelle taille est négative.
	 */
	public void resize(int newSize) {
		if (newSize < 0) {
			throw new ArrayIndexOutOfBoundsException();
		}

		ensureCapacity(newSize);
		if (newSize > size) {
			Arrays.fill(elements, size, newSize, null);
		}
		size = newSize;
	}

	/**
	 * Retourne la capacité du vecteur.
	 *
	 * @return Capacité du vecteur.
	 */
	public int capacity() {
		return elements.length;
	}

	/**
	 * Retourne le nombre d'éléments dans le vecteur.
	 *
	 * @return Le nombre d'éléments dans le vecteur.
	 */
	public int size() {
		return size;
	}

	/**
	 * Indique si le vecteur n'a pas d'éléments.
	 *
	 * @return Vrai si le vecteur n'a pas d'éléments sinon faux.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Ajoute un élément à la fin du vecteur.
	 *
	 * @param element L'élément qui est ajouté.
	 */
	public void add(T element) {
		ensureCapacity(size + 1);
		elements[size] = element;
		size += 1;
	}

	/**
	 * Ajoute les éléments à la fin du vecteur.
	 *
	 * @param elements Les éléments à ajouter.
	 */
	public void addAll(Vector<T> elements) {
		ensureCapacity(size() + elements.size());
		for (T element : elements) {
			add(element);
		}
	}

	/**
	 * Place l'élément element à l'indice index dans le vecteur.
	 *
	 * @param index   L'indice de l'emplacement de l'élément.
	 * @param element L'élément à placer.
	 * @throws IndexOutOfBoundsException Si l'indice n'est pas dans cet intervalle :
	 *                                   [0; size[.
	 */
	public void set(int index, T element) {
		if (!validIndex(index)) {
			throw new IndexOutOfBoundsException("Index " + index + " is out of range [0, " + (size - 1) + "].");
		}
		elements[index] = element;
	}

	/**
	 * Renvoie l'élément stocké à l'indice index donné.
	 *
	 * @param index L'indice de l'emplacement de l'élément
	 * @return L'élément à l'emplacement index.
	 * @throws IndexOutOfBoundsException Si l'indice n'est pas dans cet intervalle :
	 *                                   [0; size[.
	 */
	@SuppressWarnings("unchecked")
	public T get(int index) {
		if (!validIndex(index)) {
			throw new IndexOutOfBoundsException("Index " + index + " is out of range [0, " + (size - 1) + "].");
		}
		return (T) elements[index];
	}

	/**
	 * Indique si l'indice index est valide ou non. L'indice est valide s'il est
	 * dans cet intervalle : [0; size[.
	 *
	 * @param index L'indice à tester
	 * @return Vrai si l'indice est valide sinon faux.
	 */
	private boolean validIndex(int index) {
		return 0 <= index && index < size();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0; i < size(); ++i) {
			if (i > 0) {
				sb.append(", ");
			}
			sb.append(get(i).toString());
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public Iterator<T> iterator() {
		return new VectorIterator();
	}
}
