package Exercice2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import org.junit.Test;

public class VectorTest {

	@Test
	public void testVectorInt() {
		Vector vector = new Vector(123);
		assertThat(vector.capacity(), equalTo(123));
		assertThat(vector.size(), equalTo(0));
	}

	@Test
	public void testVector() {
		Vector vector = new Vector();
		assertThat(vector.capacity(), equalTo(10));
		assertThat(vector.size(), equalTo(0));
	}

	@Test
	public void testEnsureCapacity_CapacityDoubled() {
		Vector vector = new Vector(23);
		vector.ensureCapacity(24);
		assertThat(vector.capacity(), greaterThanOrEqualTo(23 * 2));
	}

	@Test
	public void testEnsureCapacity_CapacitySatified() {
		Vector vector = new Vector(23);
		vector.ensureCapacity(120);
		assertThat(vector.capacity(), greaterThanOrEqualTo(120));
	}

	@Test
	public void testEnsureCapacity_CapacityAlwaysIncreased() {
		Vector vector = new Vector(120);
		vector.ensureCapacity(10);
		assertThat(vector.capacity(), equalTo(120));
	}

	@Test
	public void testEnsureCapacity_Negative() {
		Vector vector = new Vector();
		vector.ensureCapacity(-10);
		assertThat(vector.capacity(), greaterThanOrEqualTo(0));
	}

	@Test
	public void testResize() {
		Vector vector = new Vector();
		vector.resize(120);
		assertThat(vector.size(), equalTo(120));
	}

	@Test
	public void testResize_Negative() {
		Vector vector = new Vector();
		vector.resize(-10);
		assertThat(vector.size(), greaterThanOrEqualTo(0));
	}

	@Test
	public void testResize_Zeros() {
		Vector vector = new Vector(1);
		vector.add(Integer.valueOf(2));
		vector.resize(0);
		vector.resize(1);
		assertThat(vector.get(0), equalTo(null));
	}

	@Test
	public void testResize_CapacityIncreased() {
		Vector vector = new Vector();
		vector.resize(120);
		assertThat(vector.capacity(), greaterThanOrEqualTo(120));
	}

	@Test
	public void testResize_CapacityAlwaysIncreased() {
		Vector vector = new Vector();
		vector.resize(120);
		vector.resize(0);
		assertThat(vector.capacity(), greaterThanOrEqualTo(120));
	}

	@Test
	public void testIsEmpty() {
		Vector vector = new Vector();
		assertThat(vector.isEmpty(), equalTo(true));
		vector.resize(12);
		assertThat(vector.isEmpty(), equalTo(false));
	}

	@Test
	public void testAdd() {
		Vector vector = new Vector();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(10));
		assertThat(vector.size(), equalTo(3));
		assertThat(vector.get(0), equalTo((Object) Integer.valueOf(12)));
		assertThat(vector.get(1), equalTo((Object) Integer.valueOf(13)));
		assertThat(vector.get(2), equalTo((Object) Integer.valueOf(10)));
		vector.resize(1);
		vector.add(Integer.valueOf(2));
		assertThat(vector.get(0), equalTo((Object) Integer.valueOf(12)));
		assertThat(vector.get(1), equalTo((Object) Integer.valueOf(2)));
	}

	@Test
	public void testSet() {
		Vector vector = new Vector();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(10));
		vector.set(0, Integer.valueOf(2));
		vector.set(2, Integer.valueOf(4));
		vector.set(3, Integer.valueOf(123));
		assertThat(vector.get(0), equalTo((Object) Integer.valueOf(2)));
		assertThat(vector.get(1), equalTo((Object) Integer.valueOf(13)));
		assertThat(vector.get(2), equalTo((Object) Integer.valueOf(4)));
	}

	@Test
	public void testGet() {
		Vector vector = new Vector();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		assertThat(vector.get(-1), equalTo((Object) Integer.valueOf(0)));
		assertThat(vector.get(0), equalTo((Object) Integer.valueOf(12)));
		assertThat(vector.get(1), equalTo((Object) Integer.valueOf(13)));
		assertThat(vector.get(2), equalTo((Object) Integer.valueOf(0)));
	}

	@Test
	public void testToString() {
		Vector vector = new Vector();
		vector.add(Integer.valueOf(12));
		vector.add(Integer.valueOf(13));
		vector.add(Integer.valueOf(14));
		assertThat(vector.toString(), equalTo("[12, 13, 14]"));
	}
}