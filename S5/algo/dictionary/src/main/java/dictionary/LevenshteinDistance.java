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

		int m = word1.length() + 1;
		int n = word2.length() + 1;
		int d[][] = new int[m][n];

		for (int i = 0; i < m; ++i) {
			d[i][0] = i;
		}

		for (int j = 0; j < n; ++j) {
			d[0][j] = j;
		}

		for (int i = 1; i < m; ++i) {
			for (int j = 1; j < n; ++j) {
				int deletionCost = d[i - 1][j] + 1;
				int insertionCost = d[i][j - 1] + 1;
				int substitutionCost = d[i - 1][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1);

				d[i][j] = Math.min(Math.min(deletionCost, insertionCost), substitutionCost);
			}
		}

		return d[m - 1][n - 1];
	}
}
