package Exercice2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

	@Test
	public void testOdd() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Filter filter = new Filter(new Odd());

		List<Integer> result = filter.apply(list);

		assertThat(result, equalTo(Arrays.asList(1, 3, 5, 7, 9)));
	}

	@Test
	public void testLeq() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Filter filter = new Filter(new Leq(6));

		List<Integer> result = filter.apply(list);

		assertThat(result, equalTo(Arrays.asList(1, 2, 3, 4, 5, 6)));
	}

	@Test
	public void testAnd() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		Filter filter = new Filter(new And(new Odd(), new Leq(6)));

		List<Integer> result = filter.apply(list);

		assertThat(result, equalTo(Arrays.asList(1, 3, 5)));
	}

	@Test
	public void testFilter() {
		List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		List<Integer> result = Filter.filter(new Odd(), list);

		assertThat(result, equalTo(Arrays.asList(1, 3, 5, 7, 9)));
	}
}
