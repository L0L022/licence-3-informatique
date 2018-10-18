package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Un dictionnaire composé d'une liste de mots.
 *
 * @author Loïc Escales
 *
 */
public class Dictionary {

	private Set<String> words;
	private FuzzySearch triSearch;
	private FuzzySearch levenSearch;

	/**
	 * Construit un dictionnaire composé de la liste de mots passée en paramètre.
	 *
	 * @param words Les mots qui composent le dictionnaire.
	 */
	public Dictionary(Set<String> words) {
		this.words = words;
		triSearch = new TrigramFuzzySearch();
		levenSearch = new LevenshteinFuzzySearch();
	}

	/**
	 * Construit un dictionnaire composé de la liste de mots stockée dans le
	 * fichier. Le fichier doit contenir un mot par ligne.
	 *
	 * @param path Le chemin vers le fichier contenant la liste de mots.
	 * @return Le dictionnaire construit à partir du fichier.
	 * @throws FileNotFoundException
	 */
	public static Dictionary fromFile(String path) throws FileNotFoundException {
		Scanner s = new Scanner(new BufferedReader(new FileReader(new File(path))));

		Set<String> words = new HashSet<>();

		while (s.hasNextLine()) {
			words.add(s.nextLine());
		}

		s.close();

		return new Dictionary(words);
	}

	/**
	 * Indique si le mot passé en paramètre est présent dans le dictionnaire.
	 *
	 * @param word
	 * @return Vrai si le mot est dans le dictionnaire sinon faux.
	 */
	public boolean contains(String word) {
		return words.contains(word);
	}

	/**
	 * Renvoie un ensemble de mots similaires à celui passé en paramètre.
	 *
	 * @param word
	 * @param nbWords Le nombre de mots à retourner.
	 * @return Un ensemble de mots similaire à celui passé en paramètre.
	 */
	public List<String> similarWords(String word, int nbWords) {
		return levenSearch.search(word, new HashSet<>(triSearch.search(word, words, 100)), nbWords);
	}

}
