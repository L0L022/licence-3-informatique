import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class TwoSATTest {

	@Test
	public void testTwoSAT() {
		fail("Not yet implemented");
	}

	@Test
	public void testFromFile() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		Literal[] lit = {new Literal("x"), new Literal("y"), new Literal("z")};
		Clause[] clauses = {new Clause(lit[0], lit[1].not()), new Clause(lit[0].not(), lit[2]), new Clause(lit[0], lit[2]), new Clause(lit[1].not(), lit[2].not())};
		
		TwoSAT twoSAT = new TwoSAT(clauses);
		
		assertThat(twoSAT.toString(), equalTo("{ x ∨ ¬y, ¬x ∨ z, x ∨ z, ¬y ∨ ¬z }"));
	}

	@Test
	public void testIs_satisfiable() {
		fail("Not yet implemented");
	}

}
