import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class ImplicationGraphTest {
	
	@Test
	public void testImplicationGraph() {
		// x, y, z
		Literal[] lit = {new Literal("x"), new Literal("y"), new Literal("z")};
		
		// { x ∨ ¬y, ¬x ∨ z, x ∨ z, ¬y ∨ ¬z }
		Clause[] clauses = {new Clause(lit[0], lit[1].not()), new Clause(lit[0].not(), lit[2]), new Clause(lit[0], lit[2]), new Clause(lit[1].not(), lit[2].not())};
		
		ImplicationGraph graph = new ImplicationGraph(clauses);
		
		//System.out.print(graph.toString());
		
		assertThat(graph.order(), equalTo(6));
		assertThat(graph.size(), equalTo(8));
		
		// x : z
		assertThat(graph.hasArrow(lit[0], lit[2]), equalTo(true));
		
		// y : x, ¬z
		assertThat(graph.hasArrow(lit[1], lit[0]), equalTo(true));
		assertThat(graph.hasArrow(lit[1], lit[2].not()), equalTo(true));
		
		// z : ¬y
		assertThat(graph.hasArrow(lit[2], lit[1].not()), equalTo(true));
		
		// ¬x : ¬y, z
		assertThat(graph.hasArrow(lit[0].not(), lit[1].not()), equalTo(true));
		assertThat(graph.hasArrow(lit[0].not(), lit[2]), equalTo(true));

		// ¬z : x, ¬x
		assertThat(graph.hasArrow(lit[2].not(), lit[0]), equalTo(true));
		assertThat(graph.hasArrow(lit[2].not(), lit[0].not()), equalTo(true));
	}

}
