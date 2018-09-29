import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Test;

public class DepthFirstSearchTest {

	@Test
	public void testEmptyGraph() {
		DirectedGraph<Integer> graph = new DirectedGraph<>();
		DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(graph);
		assertThat(dfs.vertexData().isEmpty(), equalTo(true));
	}

	@Test
	public void testSimpleGraph() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		graph.addArrow(1, 2);
		
		DepthFirstSearch<Integer> dfs = new DepthFirstSearch<>(graph);
		assertThat(dfs.vertexData().get(0).timeDiscovered, equalTo(0));
		assertThat(dfs.vertexData().get(1).timeDiscovered, equalTo(1));
		assertThat(dfs.vertexData().get(2).timeDiscovered, equalTo(2));
		assertThat(dfs.vertexData().get(2).timeEnded, equalTo(3));
		assertThat(dfs.vertexData().get(1).timeEnded, equalTo(4));
		assertThat(dfs.vertexData().get(0).timeEnded, equalTo(5));
	}

}
