package spanning_tree;

import java.util.ArrayList;
import java.util.Random;

public class MnimumWeightSpanningTreeUsingRandomWeight {
	public static ArrayList<Edge> generateTree(Graph graph) {
		Graph rand_edges = new Graph(graph.adjacency.size());

		Random rand = new Random();

		for (Edge edge : graph) {
			rand_edges.addEdge(new Edge(edge.v1, edge.v2, rand.nextDouble()));
		}

		return Kruskal.generateTree(rand_edges);
	}
}
