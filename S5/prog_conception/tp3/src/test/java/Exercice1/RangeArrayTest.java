package Exercice1;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Iterator;

import org.junit.Test;

public class RangeArrayTest {

	@Test
	public void testGetIndexMin() {
		RangeArray<Integer> array = new RangeArray<>(1, 2);
		assertThat(array.getIndexMin(), equalTo(1));
	}

	@Test
	public void testGetIndexMax() {
		RangeArray<Integer> array = new RangeArray<>(1, 2);
		assertThat(array.getIndexMax(), equalTo(2));
	}

	@Test
	public void testSize_one() {
		RangeArray<Integer> array = new RangeArray<>(0, 0);
		assertThat(array.size(), equalTo(1));
	}

	@Test
	public void testSize_two() {
		RangeArray<Integer> array = new RangeArray<>(0, 1);
		assertThat(array.size(), equalTo(2));
	}

	@Test
	public void testSize_ten() {
		RangeArray<Integer> array = new RangeArray<>(0, 9);
		assertThat(array.size(), equalTo(10));
	}

	@Test
	public void testSet() {
		RangeArray<Integer> array = new RangeArray<>(1, 2);
		array.set(1, Integer.valueOf(10));
		array.set(2, Integer.valueOf(11));
		assertThat(array.get(1), equalTo(Integer.valueOf(10)));
		assertThat(array.get(2), equalTo(Integer.valueOf(11)));
	}

	@Test
	public void testGet() {
		RangeArray<Integer> array = new RangeArray<>(1, 1);
		array.set(1, Integer.valueOf(10));
		assertThat(array.get(1), equalTo(Integer.valueOf(10)));
	}

	@Test
	public void testIterator() {
		RangeArray<Integer> array = new RangeArray<>(1, 2);
		array.set(1, Integer.valueOf(10));
		array.set(2, Integer.valueOf(11));

		Iterator<Integer> it = array.iterator();

		assertThat(it.hasNext(), equalTo(true));
		assertThat(it.next(), equalTo(Integer.valueOf(10)));
		assertThat(it.hasNext(), equalTo(true));
		assertThat(it.next(), equalTo(Integer.valueOf(11)));
		assertThat(it.hasNext(), equalTo(false));
	}

	@Test
	public void testTP() {
		RangeArray<String> habitations = new RangeArray<>(3, 5);
		habitations.set(3, "Maison");
		habitations.set(4, "Immeuble");
		habitations.set(5, "Hutte");

		StringBuilder sb = new StringBuilder();
		for (String habitation : habitations) {
			sb.append(habitation + " ");
		}

		assertThat(sb.toString(), equalTo("Maison Immeuble Hutte "));
	}
}
