package Exercice2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Iterator;

import org.junit.Test;

public class GridTest {

	@Test
	public void testNbLines() {
		Integer elements[][] = { { 1, 2, 3 }, { 4, 5, 6 } };
		Grid<Integer> grid = new Grid<>(elements);
		assertThat(grid.nbLines(), equalTo(2));
	}

	@Test
	public void testNbColumns() {
		Integer elements[][] = { { 1, 2, 3 }, { 4, 5, 6 } };
		Grid<Integer> grid = new Grid<>(elements);
		assertThat(grid.nbColumns(), equalTo(3));
	}

	@Test
	public void testGet() {
		Integer elements[][] = { { 1, 2, 3 }, { 4, 5, 6 } };
		Grid<Integer> grid = new Grid<>(elements);
		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 3; ++j) {
				assertThat(grid.get(i, j), equalTo(elements[i][j]));
			}
		}
	}

	@Test
	public void testIterator() {
		Integer elements[][] = { { 1, 2, 3 }, { 4, 5, 6 } };
		Grid<Integer> grid = new Grid<>(elements);
		Iterator<Integer> it = grid.iterator();

		for (int i = 0; i < 2; ++i) {
			for (int j = 0; j < 3; ++j) {
				assertThat(it.hasNext(), equalTo(true));
				assertThat(it.next(), equalTo(elements[i][j]));
			}
		}
		assertThat(it.hasNext(), equalTo(false));
	}

}
