
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.IOException;

import org.junit.Test;

public class TwoSATTest {

	@Test
	public void testFromFile() throws IOException {
		TwoSAT twoSAT = TwoSAT.fromFile("src/test/resources/formule-2-sat.txt");
		assertThat(twoSAT.toString(), equalTo("{ a ∨ ¬b, c ∨ d, ¬c ∨ ¬d }"));
	}

	@Test
	public void testToString() {
		Literal[] lit = {new Literal("x"), new Literal("y"), new Literal("z")};
		Clause[] clauses = {new Clause(lit[0], lit[1].not()), new Clause(lit[0].not(), lit[2]), new Clause(lit[0], lit[2]), new Clause(lit[1].not(), lit[2].not())};
		
		TwoSAT twoSAT = new TwoSAT(clauses);
		
		assertThat(twoSAT.toString(), equalTo("{ x ∨ ¬y, ¬x ∨ z, x ∨ z, ¬y ∨ ¬z }"));
	}

	@Test
	public void testIs_satisfiable_true() {
		// { x ∨ y, y ∨ ¬x, ¬x ∨ ¬y }
		
		Literal[] lit = {new Literal("x"), new Literal("y")};
		Clause[] clauses = {new Clause(lit[0], lit[1]), new Clause(lit[1], lit[0].not()), new Clause(lit[0].not(), lit[1].not())};
		
		TwoSAT twoSAT = new TwoSAT(clauses);
		
		assertThat(twoSAT.is_satisfiable(), equalTo(true));
	}
	
	@Test
	public void testIs_satisfiable_false() {
		// { x ∨ y, y ∨ ¬x, x ∨ ¬y, ¬x ∨ ¬y }
		
		Literal[] lit = {new Literal("x"), new Literal("y")};
		Clause[] clauses = {new Clause(lit[0], lit[1]), new Clause(lit[1], lit[0].not()), new Clause(lit[0], lit[1].not()), new Clause(lit[0].not(), lit[1].not())};
		
		TwoSAT twoSAT = new TwoSAT(clauses);
		
		assertThat(twoSAT.is_satisfiable(), equalTo(false));
	}

}
