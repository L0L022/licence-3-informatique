import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class LiteralTest {
	@Test
	public void testHashCode_diff() {
		Literal l1 = new Literal("x"), l2 = new Literal("y");
		assertThat(l1.hashCode(), not(equalTo(l2.hashCode())));
	}
	
	@Test
	public void testHashCode_same() {
		Literal l1 = new Literal("x"), l2 = new Literal("x");
		assertThat(l1.hashCode(), equalTo(l2.hashCode()));
	}
	
	@Test
	public void testHashCode_not() {
		Literal l1 = new Literal("x"), l2 = new Literal("x", true);
		assertThat(l1.not().hashCode(), equalTo(l2.hashCode()));
		assertThat(l1.hashCode(), equalTo(l2.not().hashCode()));
		assertThat(l1.not().not().hashCode(), equalTo(l1.hashCode()));
	}
	
	@Test
	public void testIsNegative() {
		Literal l1 = new Literal("x"), l2 = new Literal("x", true);
		assertThat(l1.isNegative(), equalTo(false));
		assertThat(l2.isNegative(), equalTo(true));
	}
	
	@Test
	public void testAbs() {
		Literal l1 = new Literal("x"), l2 = new Literal("x", true);
		assertThat(l1.abs().isNegative(), equalTo(false));
		assertThat(l2.abs().isNegative(), equalTo(false));
	}
	
	@Test
	public void testEquals() {
		Literal l1 = new Literal("x"), l2 = new Literal("x"), l3 = new Literal("y");
		assertThat(l1.equals(l1), equalTo(true));
		assertThat(l1.equals(l2), equalTo(true));
		assertThat(l2.equals(l1), equalTo(true));
		assertThat(l1.equals(l1.not()), equalTo(false));
		assertThat(l1.equals(null), equalTo(false));
		assertThat(l1.equals(l3), equalTo(false));
	}
}
