package dictionary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

public class FuzzySearchTest {

	public void test(FuzzySearch fs) {
		String[] word = { "algoritmique" };
		String[][] words = { { "algorithmique", "algorithmiques", "algorithme", "algorithmes", "algorithmiquement" } };
		String[][] result = { { "algorithmique", "algorithmiques", "algorithme" } };

		for (int i = 0; i < word.length; ++i) {
			assertThat(fs.search(word[i], new HashSet<>(Arrays.asList(words[i])), 3),
					equalTo(Arrays.asList(result[i])));
		}
	}

	@Test
	public void testTrigram() {
		test(new TrigramFuzzySearch());
	}

	@Test
	public void testLevenshtein() {
		test(new LevenshteinFuzzySearch());
	}
}
