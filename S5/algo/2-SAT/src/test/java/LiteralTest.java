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
}
