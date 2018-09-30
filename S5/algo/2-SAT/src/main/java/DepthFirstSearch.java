
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class DepthFirstSearch<Vertex> {

	private DirectedGraph<Vertex> graph;
	private Map<Vertex, VertexData> vertex_data;
	private int time;

	public class VertexData {
		public int timeDiscovered, timeEnded;
	}

	public DepthFirstSearch(DirectedGraph<Vertex> graph) {
		this.graph = graph;
		vertex_data = new HashMap<>();
		time = 0;

		for (Vertex vertex : graph.vertices()) {
			explore(vertex);
		}
	}

	private void explore(Vertex vertex) {
		if (vertex_data.containsKey(vertex))
			return;

		VertexData data = new VertexData();
		data.timeDiscovered = time++;

		vertex_data.put(vertex, data);

		for (Vertex child : graph.children(vertex)) {
			explore(child);
		}

		data.timeEnded = time++;
	}

	public Map<Vertex, VertexData> vertexData() {
		return Collections.unmodifiableMap(vertex_data);
	}
}
