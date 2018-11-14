package dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class LevenshteinFuzzySearch implements FuzzySearch {

	@Override
	public List<String> search(String word, Set<String> words, int nbWords) {
		nbWords = Math.min(nbWords, words.size());

		List<WordDistance> wordDistances = new ArrayList<>(words.size());

		for (String w : words) {
			wordDistances.add(new WordDistance(w, (new LevenshteinDistance(word, w)).editDistance()));
		}

		Collections.sort(wordDistances);

		List<String> result = new ArrayList<>(nbWords);

		for (WordDistance wordDistance : wordDistances.subList(0, nbWords)) {
			result.add(wordDistance.word);
		}

		return result;
	}

}
