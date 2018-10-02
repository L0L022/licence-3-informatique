package Exercice2;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;

public class StackTest {

	@Test
	public void testPush() {
		Stack stack = new Stack();
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertThat(stack.pop(), equalTo(3));
		assertThat(stack.pop(), equalTo(2));
		assertThat(stack.pop(), equalTo(1));
	}

	@Test
	public void testPeek() {
		Stack stack = new Stack();

		assertThat(stack.peek(), equalTo(0));
		assertThat(stack.size(), equalTo(0));

		stack.push(1);
		assertThat(stack.peek(), equalTo(1));
		assertThat(stack.size(), equalTo(1));
	}

	@Test
	public void testPop() {
		Stack stack = new Stack();

		assertThat(stack.pop(), equalTo(0));
		assertThat(stack.size(), equalTo(0));

		stack.push(1);
		assertThat(stack.pop(), equalTo(1));
		assertThat(stack.size(), equalTo(0));
	}

	@Test
	public void testSize() {
		Stack stack = new Stack();
		assertThat(stack.size(), equalTo(0));
		stack.push(1);
		stack.push(2);
		stack.push(3);
		assertThat(stack.size(), equalTo(3));
	}

	@Test
	public void testIsEmpty() {
		Stack stack = new Stack();
		assertThat(stack.isEmpty(), equalTo(true));
		stack.push(1);
		assertThat(stack.isEmpty(), equalTo(false));
	}

}
