
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Parcours de graphe en profondeur.
 *
 * @author Loïc Escales
 *
 * @param <Vertex> Sommet du graphe.
 */
public class DepthFirstSearch<Vertex> {

	private DirectedGraph<Vertex> graph;
	private Map<Vertex, VertexData> vertex_data;
	private int time;

	/**
	 * Données associées à chaque sommet lors du parcours.
	 *
	 * @author Loïc Escales
	 *
	 */
	public class VertexData {
		/**
		 * Le moment où le sommet a été découvert.
		 */
		public int timeDiscovered;

		/**
		 * Le moment où l'exploration du sommet est terminée.
		 */
		public int timeEnded;
	}

	/**
	 * Fait le parcours en profondeur sur le graphe.
	 *
	 * @param graph Le graphe à explorer.
	 */
	public DepthFirstSearch(DirectedGraph<Vertex> graph) {
		this.graph = graph;
		vertex_data = new HashMap<>();
		time = 0;

		for (Vertex vertex : graph.vertices()) {
			explore(vertex);
		}
	}

	/**
	 * Renvoie les données du parcours en profondeur pour tous les sommets.
	 *
	 * @return Les données du parcours en profondeur.
	 */
	public Map<Vertex, VertexData> vertexData() {
		return Collections.unmodifiableMap(vertex_data);
	}

	private void explore(Vertex vertex) {
		if (vertex_data.containsKey(vertex)) {
			return;
		}

		VertexData data = new VertexData();
		data.timeDiscovered = time++;

		vertex_data.put(vertex, data);

		for (Vertex adjacent_v : graph.adjacentVertices(vertex)) {
			explore(adjacent_v);
		}

		data.timeEnded = time++;
	}
}
