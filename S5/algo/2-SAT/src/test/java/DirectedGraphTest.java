import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import org.junit.Test;

public class DirectedGraphTest {

	@Test
	public void testOrder() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		assertThat(graph.order(), equalTo(0));
	}

	@Test
	public void testSize() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		assertThat(graph.size(), equalTo(0));
	}

	@Test
	public void testAddVertex_one() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();		
		graph.addVertex(10);
		assertThat(graph.order(), equalTo(1));
		assertThat(graph.hasVertex(10), equalTo(true));
	}
	
	@Test
	public void testAddVertex_two() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();		
		graph.addVertex(10);
		graph.addVertex(11);
		assertThat(graph.order(), equalTo(2));
		assertThat(graph.hasVertex(10), equalTo(true));
		assertThat(graph.hasVertex(11), equalTo(true));
	}
	
	@Test
	public void testAddVertex_twice_same() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();		
		graph.addVertex(10);
		graph.addVertex(10);
		assertThat(graph.order(), equalTo(1));
		assertThat(graph.hasVertex(10), equalTo(true));
	}

	@Test
	public void testRemoveVertex_once() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addVertex(10);
		graph.removeVertex(10);
		assertThat(graph.order(), equalTo(0));
		assertThat(graph.hasVertex(10), equalTo(false));
	}
	
	@Test
	public void testRemoveVertex_nothing() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();		
		graph.removeVertex(10);
		assertThat(graph.order(), equalTo(0));
	}
	
	@Test
	public void testRemoveVertex_twice_diff() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();		
		graph.addVertex(10);
		graph.addVertex(11);
		
		graph.removeVertex(10);
		assertThat(graph.order(), equalTo(1));
		assertThat(graph.hasVertex(10), equalTo(false));
		assertThat(graph.hasVertex(11), equalTo(true));
		
		graph.removeVertex(11);
		assertThat(graph.order(), equalTo(0));
		assertThat(graph.hasVertex(11), equalTo(false));
	}
	
	@Test
	public void testRemoveVertex_twice_same() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();		
		graph.addVertex(10);
		graph.addVertex(11);
		
		graph.removeVertex(10);
		graph.removeVertex(10);
		assertThat(graph.order(), equalTo(1));
		assertThat(graph.hasVertex(10), equalTo(false));
		assertThat(graph.hasVertex(11), equalTo(true));
		
		graph.removeVertex(11);
		assertThat(graph.order(), equalTo(0));
		assertThat(graph.hasVertex(11), equalTo(false));
	}
	
	@Test
	public void testHasVertex_nothing() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		assertThat(graph.hasVertex(0), equalTo(false));
	}
	
	@Test
	public void testHasVertex_one() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addVertex(10);

		assertThat(graph.hasVertex(10), equalTo(true));
	}

	@Test
	public void testAddArrow_one() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		
		assertThat(graph.order(), equalTo(2));
		assertThat(graph.size(), equalTo(1));
		assertThat(graph.hasArrow(0, 1), equalTo(true));
	}
	
	@Test
	public void testAddArrow_two() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		graph.addArrow(2, 3);
		
		assertThat(graph.order(), equalTo(4));
		assertThat(graph.size(), equalTo(2));
		assertThat(graph.hasArrow(0, 1), equalTo(true));
		assertThat(graph.hasArrow(2, 3), equalTo(true));
	}
	
	@Test
	public void testAddArrow_share_vertex() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		graph.addArrow(0, 2);
		graph.addArrow(1, 3);
		graph.addArrow(2, 3);
		
		assertThat(graph.order(), equalTo(4));
		assertThat(graph.size(), equalTo(4));
		assertThat(graph.hasArrow(0, 1), equalTo(true));
		assertThat(graph.hasArrow(0, 2), equalTo(true));
		assertThat(graph.hasArrow(1, 3), equalTo(true));
		assertThat(graph.hasArrow(2, 3), equalTo(true));
	}

	@Test
	public void testRemoveArrow_once() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		graph.removeArrow(0, 1);
		
		assertThat(graph.order(), equalTo(2));
		assertThat(graph.size(), equalTo(0));
		assertThat(graph.hasArrow(0, 1), equalTo(false));
	}
	
	@Test
	public void testRemoveArrow_nothing() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.removeArrow(0, 1);
		
		assertThat(graph.order(), equalTo(0));
		assertThat(graph.size(), equalTo(0));
	}
	
	@Test
	public void testRemoveArrow_twice_diff() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		graph.addArrow(0, 2);
		
		graph.removeArrow(0, 1);
		assertThat(graph.order(), equalTo(3));
		assertThat(graph.size(), equalTo(1));
		assertThat(graph.hasArrow(0, 1), equalTo(false));
		assertThat(graph.hasArrow(0, 2), equalTo(true));

		
		graph.removeArrow(0, 2);
		assertThat(graph.order(), equalTo(3));
		assertThat(graph.size(), equalTo(0));
		assertThat(graph.hasArrow(0, 2), equalTo(false));
	}
	
	@Test
	public void testRemoveArrow_twice_same() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		graph.addArrow(0, 2);
		
		graph.removeArrow(0, 1);
		graph.removeArrow(0, 1);
		assertThat(graph.order(), equalTo(3));
		assertThat(graph.size(), equalTo(1));
		assertThat(graph.hasArrow(0, 1), equalTo(false));
		assertThat(graph.hasArrow(0, 2), equalTo(true));
		
		graph.removeArrow(0, 2);
		assertThat(graph.order(), equalTo(3));
		assertThat(graph.size(), equalTo(0));
		assertThat(graph.hasArrow(0, 2), equalTo(false));
	}
	
	@Test
	public void testHasArrow_nothing() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		assertThat(graph.hasArrow(0, 1), equalTo(false));
	}
	
	@Test
	public void testHasArrow_one() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		assertThat(graph.hasArrow(0, 1), equalTo(true));
	}

	@Test
	public void testTranspose() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		graph.addArrow(0, 2);
		graph.addArrow(1, 3);
		graph.addArrow(2, 3);
		
		DirectedGraph<Integer> t_graph = graph.transpose();
		
		assertThat(t_graph.order(), equalTo(4));
		assertThat(t_graph.size(), equalTo(4));
		
		assertThat(t_graph.hasArrow(0, 1), equalTo(false));
		assertThat(t_graph.hasArrow(0, 2), equalTo(false));
		assertThat(t_graph.hasArrow(1, 3), equalTo(false));
		assertThat(t_graph.hasArrow(2, 3), equalTo(false));
		
		assertThat(t_graph.hasArrow(1, 0), equalTo(true));
		assertThat(t_graph.hasArrow(2, 0), equalTo(true));
		assertThat(t_graph.hasArrow(3, 1), equalTo(true));
		assertThat(t_graph.hasArrow(3, 2), equalTo(true));
	}

	@Test
	public void testToString() {
		DirectedGraph<Integer> graph = new DirectedGraph<Integer>();
		graph.addArrow(0, 1);
		graph.addArrow(0, 2);
		graph.addArrow(1, 3);
		graph.addArrow(2, 3);
		
		String graph_str = graph.toString();
		
		assertThat(graph_str.indexOf("[0] -> [1] -> [2]"), not(equalTo(-1)));
		assertThat(graph_str.indexOf("[1] -> [3]"), not(equalTo(-1)));
		assertThat(graph_str.indexOf("[2] -> [3]"), not(equalTo(-1)));
	}

}
