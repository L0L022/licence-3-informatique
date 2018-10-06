package Exercice3;

import Exercice2.Vector;

/**
 * Affiche sur la sortie standard les appels des méthodes.
 *
 * @author Loïc Escales
 *
 */
class VerboseVector extends Vector {

	/**
	 * Construit VerboseVector.
	 */
	public VerboseVector() {
		super();
	}

	@Override
	public void add(Object element) {
		System.out.println("add(" + element + ");");
		super.add(element);
	}

	@Override
	public void set(int index, Object element) {
		System.out.println("set(" + index + ", " + element + ");");
		super.set(index, element);
	}

	@Override
	public Object get(int index) {
		System.out.println("get(" + index + ");");
		return super.get(index);
	}
}