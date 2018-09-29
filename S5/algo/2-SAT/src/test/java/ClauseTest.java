import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class ClauseTest {
	@Test
	public void testToString() {
		Clause clause = new Clause(new Literal("x"), new Literal("y"));
		assertThat(clause.toString(), equalTo("x ∨ y"));
	}
}
