package dictionary;

import java.util.List;
import java.util.Set;

/**
 * Recherche approximative d'un mot.
 *
 * @author Loïc Escales
 *
 */
public interface FuzzySearch {
	/**
	 * Renvoie un ensemble de mots similaires à celui passé en paramètre.
	 *
	 * @param word Le mot qui est recherché.
	 * @param words L'ensemble de mots dans lequel la recherche est effectuée.
	 * @param nbWords Le nombre de mots retournés.
	 * @return Un ensemble de mots similaire à celui passé en paramètre.
	 */
	List<String> search(String word, Set<String> words, int nbWords);
}
