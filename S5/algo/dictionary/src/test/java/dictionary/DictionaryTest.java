package dictionary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import org.junit.Test;

public class DictionaryTest {

	@Test
	public void testFromFile() throws FileNotFoundException {
		Dictionary dic = Dictionary.fromFile("src/test/resources/dico.txt");

		assertThat(dic.contains(""), equalTo(false));
		assertThat(dic.contains("azertyuiopmlkjhgfdsqwxcvbn"), equalTo(false));
		assertThat(dic.contains("A"), equalTo(true));
		assertThat(dic.contains("grandiloquent"), equalTo(true));
		assertThat(dic.contains("œuvés"), equalTo(true));
	}

	@Test
	public void testContains() {
		Dictionary dic = new Dictionary(new HashSet<>(Arrays.asList("A", "grandiloquent", "œuvés")));

		assertThat(dic.contains(""), equalTo(false));
		assertThat(dic.contains("azertyuiopmlkjhgfdsqwxcvbn"), equalTo(false));
		assertThat(dic.contains("A"), equalTo(true));
		assertThat(dic.contains("grandiloquent"), equalTo(true));
		assertThat(dic.contains("œuvés"), equalTo(true));
	}

	@Test
	public void testSimilarWords() {
		Dictionary dic = new Dictionary(new HashSet<>(Arrays.asList("convainquirent", "convainquis", "convainquisse",
				"convainquissent", "convainquisses", "chartreuse", "pimenteras")));

		Collection<String> words = dic.similarWords("convinque", 5);

		assertThat(words, equalTo(
				Arrays.asList("convainquirent", "convainquis", "convainquisse", "convainquissent", "convainquisses")));
	}

}
