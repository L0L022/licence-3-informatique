package dictionary;

import java.util.Set;
import java.util.SortedSet;

/**
 * Un dictionnaire composé d'une liste de mots.
 * 
 * @author Loïc Escales
 *
 */
public class Dictionary {

	/**
	 * Construit un dictionnaire composé de la liste de mots passée en paramètre.
	 *
	 * @param words Les mots qui composent le dictionnaire.
	 */
	public Dictionary(Set<String> words) {

	}

	/**
	 * Construit un dictionnaire composé de la liste de mots stockée dans le
	 * fichier. Le fichier doit contenir un mot par ligne.
	 *
	 * @param path Le chemin vers le fichier contenant la liste de mots.
	 * @return Le dictionnaire construit à partir du fichier.
	 */
	public static Dictionary fromFile(String path) {
		return null;
	}

	/**
	 * Indique si le mot passé en paramètre est présent dans le dictionnaire.
	 *
	 * @param word
	 * @return Vrai si le mot est dans le dictionnaire sinon faux.
	 */
	public boolean contains(String word) {
		return false;
	}

	/**
	 * Renvoie un ensemble de mots similaires à celui passé en paramètre.
	 *
	 * @param word
	 * @param nbWords Le nombre de mots à retourner.
	 * @return Un ensemble de mots similaire à celui passé en paramètre.
	 */
	public SortedSet<String> similarWords(String word, int nbWords) {
		return null;
	}
}
