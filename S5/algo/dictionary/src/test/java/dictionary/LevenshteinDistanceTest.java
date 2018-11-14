package dictionary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class LevenshteinDistanceTest {

	@Test
	public void testChiens() {
		LevenshteinDistance ld = new LevenshteinDistance("chiens", "niche");

		assertThat(ld.editDistance(), equalTo(5));
	}

	@Test
	public void testKitten() {
		LevenshteinDistance ld = new LevenshteinDistance("kitten", "sitting");

		assertThat(ld.editDistance(), equalTo(3));
	}

	@Test
	public void testSaturday() {
		LevenshteinDistance ld = new LevenshteinDistance("Saturday", "Sunday");

		assertThat(ld.editDistance(), equalTo(3));
	}

	@Test
	public void test() {
		LevenshteinDistance ld = new LevenshteinDistance("bata", "patta");

		assertThat(ld.editDistance(), equalTo(0));
	}
}
