package spanning_tree;

import java.util.List;
import java.util.Random;

/*
 * Affecte des poids aléatoires à chaque arête, puis utilise Kruskal.
 */

public class RandomWeight implements MinimumWeightSpanningTreeGenerator {

	private static Random rand = new Random();

	@Override
	public List<Edge> generateTree(Graph graph) {
		Graph graphWithRandomWeightEdges = new Graph(graph.adjacency.size());

		for (Edge edge : graph) {
			graphWithRandomWeightEdges.addEdge(new Edge(edge.v1, edge.v2, rand.nextDouble()));
		}

		return (new Kruskal()).generateTree(graphWithRandomWeightEdges);
	}

	@Override
	public String name() {
		return "RandomWeight";
	}
}
