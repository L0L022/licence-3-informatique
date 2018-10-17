package dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

/**
 * Un dictionnaire composé d'une liste de mots.
 *
 * @author Loïc Escales
 *
 */
public class Dictionary {

	private Set<String> words;
	private Map<String, LinkedList<String>> trigramsToWords;

	/**
	 * Construit un dictionnaire composé de la liste de mots passée en paramètre.
	 *
	 * @param words Les mots qui composent le dictionnaire.
	 */
	public Dictionary(Set<String> words) {
		this.words = words;
		trigramsToWords = new HashMap<>();

		for (String word : words) {
			word = "<" + word + ">";
			for (String trigram : trigramsFromStr(word)) {
				LinkedList<String> associatedWords = trigramsToWords.get(trigram);
				if (associatedWords == null) {
					associatedWords = new LinkedList<>();
					trigramsToWords.put(trigram, associatedWords);
				}
				associatedWords.add(word);
			}
		}
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
		return similarEditDistance(word, similarTrigrams(word, 100), nbWords);
	}

	private List<String> similarTrigrams(String word, int nbWords) {
		HashMap<String, Integer> nbTrigramsPerWord = new HashMap<>();

		for (String trigram : trigramsFromStr(word)) {
			List<String> words = trigramsToWords.get(trigram);
			if (words == null) {
				continue;
			}
			for (String w : words) {
				Integer nbTrigrams = nbTrigramsPerWord.get(w);
				nbTrigramsPerWord.put(w, nbTrigrams == null ? 1 : nbTrigrams + 1);
			}
		}

		nbWords = Math.min(nbWords, nbTrigramsPerWord.size());
		List<String> words = new Vector<>(nbWords);

		for (int i = 0; i < nbWords; ++i) {
			int max = 0;
			String max_word = null;
			for (Map.Entry<String, Integer> sc : nbTrigramsPerWord.entrySet()) {
				if (sc.getValue() > max) {
					max = sc.getValue();
					max_word = sc.getKey();
				}
			}
			words.add(max_word.substring(1, max_word.length() - 1));
			nbTrigramsPerWord.remove(max_word);
		}

		return words;
	}

	private List<String> similarEditDistance(String word, List<String> words, int nbWords) {
		return null;
	}

	private List<String> trigramsFromStr(String word) {
		assert word.length() > 2;

		int nb_trigrams = word.length() - 2;
		List<String> trigrams = new Vector<>(nb_trigrams);

		for (int i = 0; i < nb_trigrams; ++i) {
			trigrams.add(word.substring(i, i + 3));
		}

		return trigrams;
	}
}
