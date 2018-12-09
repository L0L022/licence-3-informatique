package spanning_tree;

import java.util.List;
import java.util.Random;

public class RandomWeight implements MinimumWeightSpanningTreeGenerator {
	@Override
	public List<Edge> generateTree(Graph graph) {
		Graph rand_edges = new Graph(graph.adjacency.size());

		Random rand = new Random();

		for (Edge edge : graph) {
			rand_edges.addEdge(new Edge(edge.v1, edge.v2, rand.nextDouble()));
		}

		return (new Kruskal()).generateTree(rand_edges);
	}

	@Override
	public String name() {
		return "RandomWeight";
	}
}
