package dictionary;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class TrigramFuzzySearch implements FuzzySearch {
	private Set<String> words;
	private Map<String, LinkedList<String>> trigramsToWords;

	public TrigramFuzzySearch() {
	}

	public TrigramFuzzySearch(Set<String> words) {
		buildTrigramsToWords(words);
	}

	@Override
	public List<String> search(String word, Set<String> words, int nbWords) {
		buildTrigramsToWords(words);

		HashMap<String, Integer> nbTrigramsPerWord = new HashMap<>();

		for (String trigram : trigramsFromStr(word)) {
			List<String> words_from_trig = trigramsToWords.get(trigram);
			if (words_from_trig == null) {
				continue;
			}
			for (String w : words_from_trig) {
				Integer nbTrigrams = nbTrigramsPerWord.get(w);
				nbTrigramsPerWord.put(w, nbTrigrams == null ? 1 : nbTrigrams + 1);
			}
		}

//		System.out.println(nbTrigramsPerWord);

		nbWords = Math.min(nbWords, nbTrigramsPerWord.size());
		List<String> words_result = new Vector<>(nbWords);

		for (int i = 0; i < nbWords; ++i) {
			int max = -1;
			String max_word = null;
			for (Map.Entry<String, Integer> sc : nbTrigramsPerWord.entrySet()) {
				if (sc.getValue() > max) {
					max = sc.getValue();
					max_word = sc.getKey();
				}
			}
			words_result.add(max_word.substring(1, max_word.length() - 1));
			nbTrigramsPerWord.remove(max_word);
		}

		return words_result;
	}

	private void buildTrigramsToWords(Set<String> words) {
		if (words.equals(this.words)) {
			return;
		}

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
