package dictionary;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class LevenshteinDistanceTest {

	@Test
	public void test() {
		LevenshteinDistance ld = new LevenshteinDistance("chiens", "niche");

		assertThat(ld.editDistance(), equalTo(5));
	}

}
