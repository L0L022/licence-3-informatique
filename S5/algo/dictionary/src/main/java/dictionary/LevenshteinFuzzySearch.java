package dictionary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class LevenshteinFuzzySearch implements FuzzySearch {

	@Override
	public List<String> search(String word, Set<String> words, int nbWords) {

		HashMap<String, Integer> distancePerWord = new HashMap<>();

		for (String w : words) {
			distancePerWord.put(w, (new LevenshteinDistance(word, w)).editDistance());
		}
//		System.out.println(distancePerWord);
		nbWords = Math.min(nbWords, distancePerWord.size());
		List<String> words_result = new Vector<>(nbWords);

		for (int i = 0; i < nbWords; ++i) {
			Map.Entry<String, Integer> first_entry = distancePerWord.entrySet().iterator().next();
			int min = first_entry.getValue();
			String min_word = first_entry.getKey();
			for (Map.Entry<String, Integer> sc : distancePerWord.entrySet()) {
				if (sc.getValue() < min) {
					min = sc.getValue();
					min_word = sc.getKey();
				}
			}
			words_result.add(min_word);
			distancePerWord.remove(min_word);
		}

		return words_result;
	}

}
