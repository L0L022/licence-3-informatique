package dictionary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TrigramFuzzySearch implements FuzzySearch {
	private Set<String> words;
	private Map<String, Collection<String>> trigramsToWords;

	public TrigramFuzzySearch() {
	}

	public TrigramFuzzySearch(Set<String> words) {
		buildTrigramsToWords(words);
	}

	@Override
	public List<String> search(String word, Set<String> words, int nbWords) {
		buildTrigramsToWords(words);

		Map<String, MutableInteger> nbTrigramsPerWord = new HashMap<>();
		int maxNbOfTrigInCommon = 0;

		for (String trigram : trigramsFromStr(word)) {
			Collection<String> words_from_trig = trigramsToWords.get(trigram);
			if (words_from_trig == null) {
				continue;
			}
			for (String w : words_from_trig) {
				MutableInteger nbTrigrams = nbTrigramsPerWord.get(w);

				if (nbTrigrams == null) {
					nbTrigrams = new MutableInteger(0);
					nbTrigramsPerWord.put(w, nbTrigrams);
				}

				nbTrigrams.set(nbTrigrams.get() + 1);
				maxNbOfTrigInCommon = Math.max(maxNbOfTrigInCommon, nbTrigrams.get());
			}
		}

		nbWords = Math.min(nbWords, nbTrigramsPerWord.size());

		List<List<String>> wordT = new ArrayList<>(maxNbOfTrigInCommon);

		for (int i = 0; i < maxNbOfTrigInCommon; ++i) {
			wordT.add(new ArrayList<>());
		}

		for (Map.Entry<String, MutableInteger> entry : nbTrigramsPerWord.entrySet()) {
			wordT.get(entry.getValue().get() - 1).add(entry.getKey());
		}

		List<String> words_result = new ArrayList<>(nbWords);
		int max_found = maxNbOfTrigInCommon - 1;

		for (int i = 0; i < nbWords; ++i) {

			String w1 = null;
			while (w1 == null) {
				List<String> l = wordT.get(max_found);
				if (l.isEmpty()) {
					--max_found;
					continue;
				}
				w1 = l.iterator().next();
				l.remove(w1);
			}
			words_result.add(w1.substring(1, w1.length() - 1));
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
				Collection<String> associatedWords = trigramsToWords.get(trigram);
				if (associatedWords == null) {
					associatedWords = new ArrayList<>();
					trigramsToWords.put(trigram, associatedWords);
				}
				associatedWords.add(word);
			}
		}
	}

	private List<String> trigramsFromStr(String word) {
		assert word.length() > 2;

		int nb_trigrams = word.length() - 2;
		List<String> trigrams = new ArrayList<>(nb_trigrams);

		for (int i = 0; i < nb_trigrams; ++i) {
			trigrams.add(word.substring(i, i + 3));
		}
		return trigrams;
	}
}
