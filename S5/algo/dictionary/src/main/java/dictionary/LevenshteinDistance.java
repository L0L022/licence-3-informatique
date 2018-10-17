package dictionary;

public class LevenshteinDistance {

	int editDistance;

	/**
	 * Construit l'algorithme de la distance de Levenshtein.
	 *
	 * @param word1
	 * @param word2
	 */
	public LevenshteinDistance(String word1, String word2) {
		editDistance = process(word1, word2);
	}

	/**
	 * Renvoie la distance d'édition entre les deux mots.
	 *
	 * @return La distance d'édition entre les deux mots.
	 */
	public int editDistance() {
		return editDistance;
	}

	private int process(String word1, String word2) {
		if (word1.isEmpty()) {
			return word2.length();
		}

		if (word2.isEmpty()) {
			return word1.length();
		}

		int m[][] = new int[word1.length() + 1][word2.length() + 1];

		for (int i = 0; i < word1.length() + 1; ++i) {
			m[i][0] = i;
		}

		for (int i = 0; i < word2.length() + 1; ++i) {
			m[0][i] = i;
		}

		for (int i = 0; i < word1.length() + 1; ++i) {
			for (int j = 0; j < word2.length() + 1; ++j) {

			}
		}

		return 0;
	}
}
