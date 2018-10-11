package Exercice3;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.util.Iterator;

import org.junit.Test;

public class VectorTest {

	@Test
	public void testVectorInt() {
		Vector<Integer> vector = new Vector<>(123);
		assertThat(vector.capacity(), equalTo(123));
		assertThat(vector.size(), equalTo(0));
	}

	@Test
	public void testVector() {
		Vector<Integer> vector = new Vector<>();
		assertThat(vector.capacity(), equalTo(10));
		assertThat(vector.size(), equalTo(0));
	}

	@Test
	public void testEnsureCapacity_CapacityDoubled() {
		Vector<Integer> vector = new Vector<>(23);
		vector.ensureCapacity(24);
		assertThat(vector.capacity(), greaterThanOrEqualTo(23 * 2));
	}

	@Test
	public void testEnsureCapacity_CapacitySatified() {
		Vector<Integer> vector = new Vector<>(23);
		vector.ensureCapacity(120);
		assertThat(vector.capacity(), greaterThanOrEqualTo(120));
	}

	@Test
	public void testEnsureCapacity_CapacityAlwaysIncreased() {
		Vector<Integer> vector = new Vector<>(120);
		vector.ensureCapacity(10);
		assertThat(vector.capacity(), equalTo(120));
	}

	@Test
	public void testEnsureCapacity_Negative() {
		Vector<Integer> vector = new Vector<>();
		vector.ensureCapacity(-10);
		assertThat(vector.capacity(), greaterThanOrEqualTo(0));
	}

	@Test
	public void testResize() {
		Vector<Integer> vector = new Vector<>();
		vector.resize(120);
		assertThat(vector.size(), equalTo(120));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testResize_Negative() {
		Vector<Integer> vector = new Vector<>();
		vector.resize(-10);
	}

	@Test
	public void testResize_Zeros() {
		Vector<Integer> vector = new Vector<>(1);
		vector.add(Integer.valueOf(2));
		vector.resize(0);
		vector.resize(1);
		assertThat(vector.get(0), equalTo(null));
	}

	@Test
	public void testResize_CapacityIncreased() {
		Vector<Integer> vector = new Vector<>();
		vector.resize(120);
		assertThat(vector.capacity(), greaterThanOrEqualTo(120));
	}

	@Test
	public void testResize_CapacityAlwaysIncreased() {
		Vector<Integer> vector = new Vector<>();
		vector.resize(120);
		vector.resize(0);
		assertThat(vector.capacity(), greaterThanOrEqualTo(120));
	}

	@Test
	public void testIsEmpty() {
		Vector<Integer> vector = new Vector<>();
		assertThat(vector.isEmpty(), equalTo(true));
		vector.resize(12);
		assertThat(vector.isEmpty(), equalTo(false));
	}

	@Test
	public void testAdd() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(10));
		assertThat(vector.size(), equalTo(3));
		assertThat(vector.get(0), equalTo(Integer.valueOf(12)));
		assertThat(vector.get(1), equalTo(Integer.valueOf(13)));
		assertThat(vector.get(2), equalTo(Integer.valueOf(10)));
		vector.resize(1);
		vector.add(Integer.valueOf(2));
		assertThat(vector.get(0), equalTo(Integer.valueOf(12)));
		assertThat(vector.get(1), equalTo(Integer.valueOf(2)));
	}

	@Test
	public void testAddAll() {
		Vector<Integer> vector1 = new Vector<>();
		vector1.add(Integer.valueOf(1));
		vector1.add(Integer.valueOf(2));
		vector1.add(Integer.valueOf(3));
		Vector<Integer> vector2 = new Vector<>();
		vector2.add(Integer.valueOf(4));
		vector2.add(Integer.valueOf(5));
		vector2.add(Integer.valueOf(6));

		vector1.addAll(vector2);

		assertThat(vector1.get(0), equalTo(Integer.valueOf(1)));
		assertThat(vector1.get(1), equalTo(Integer.valueOf(2)));
		assertThat(vector1.get(2), equalTo(Integer.valueOf(3)));
		assertThat(vector1.get(3), equalTo(Integer.valueOf(4)));
		assertThat(vector1.get(4), equalTo(Integer.valueOf(5)));
		assertThat(vector1.get(5), equalTo(Integer.valueOf(6)));

	}

	@Test
	public void testSet() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(10));
		vector.set(0, Integer.valueOf(2));
		vector.set(2, Integer.valueOf(4));
		assertThat(vector.get(0), equalTo(Integer.valueOf(2)));
		assertThat(vector.get(1), equalTo(Integer.valueOf(13)));
		assertThat(vector.get(2), equalTo(Integer.valueOf(4)));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testSet_invalidIndex() {
		Vector<Integer> vector = new Vector<>();
		vector.set(0, 0);
	}

	@Test
	public void testGet() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		assertThat(vector.get(0), equalTo(Integer.valueOf(12)));
		assertThat(vector.get(1), equalTo(Integer.valueOf(13)));
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_negativeIndex() {
		Vector<Integer> vector = new Vector<>();
		vector.get(-1);
	}

	@Test(expected = IndexOutOfBoundsException.class)
	public void testGet_biggerIndex() {
		Vector<Integer> vector = new Vector<>();
		vector.get(10);
	}

	@Test
	public void testToString() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(14));
		assertThat(vector.toString(), equalTo("[12, 13, 14]"));
	}

	@Test
	public void testIterator() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(14));

		Iterator<Integer> it = vector.iterator();

		assertThat(it.hasNext(), equalTo(true));
		assertThat(it.next(), equalTo(Integer.valueOf(12)));
		assertThat(it.hasNext(), equalTo(true));
		assertThat(it.next(), equalTo(Integer.valueOf(13)));
		assertThat(it.hasNext(), equalTo(true));
		assertThat(it.next(), equalTo(Integer.valueOf(14)));
		assertThat(it.hasNext(), equalTo(false));
	}

	@Test
	public void testIsSorted_true() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(14));
		assertThat(vector.isSorted(), equalTo(true));
	}

	@Test
	public void testIsSorted_false() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(14));
		vector.add(Integer.valueOf(11));
		assertThat(vector.isSorted(), equalTo(false));
	}

	@Test
	public void testIsSorted_comparator_true() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(14));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(12));
		assertThat(vector.isSorted((l, r) -> r.compareTo(l)), equalTo(true));
	}

	@Test
	public void testIsSorted_comparator_false() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(14));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(12));
		assertThat(vector.isSorted((l, r) -> l.compareTo(r)), equalTo(false));
	}

	@Test
	public void testIsSorted_static() {
		Vector<Integer> vector = new Vector<>();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(14));
		assertThat(Vector.isSorted(vector), equalTo(true));
	}
}