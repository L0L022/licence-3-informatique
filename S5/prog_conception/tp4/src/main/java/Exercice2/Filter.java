package Exercice2;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtre une liste d'entiers suivant un prédicat.
 *
 * @author Loïc Escales
 *
 */
public class Filter {
	private Predicate predicate;

	/**
	 * Construit Filter.
	 *
	 * @param predicate Le prédicat qui sert à filtrer.
	 */
	public Filter(Predicate predicate) {
		this.predicate = predicate;
	}

	/**
	 * Renvoie une liste d'entiers filtrée suivant le prédicat. Seuls les éléments
	 * validant le prédicat sont gardés.
	 *
	 * @param list La liste à filtrer.
	 * @return La liste filtrée.
	 */
	List<Integer> apply(List<Integer> list) {
		List<Integer> filtered_list = new ArrayList<>();

		for (int element : list) {
			if (predicate.test(element)) {
				filtered_list.add(element);
			}
		}

		return filtered_list;
	}

	/**
	 * Renvoie la liste d'entiers filtrée suivant le prédicat.
	 *
	 * @param predicate Le prédicat qui sert à filtrer.
	 * @param list      La liste à trier.
	 * @return La liste d'entier filtrée.
	 */
	static public List<Integer> filter(Predicate predicate, List<Integer> list) {
		return (new Filter(predicate)).apply(list);
	}

}
