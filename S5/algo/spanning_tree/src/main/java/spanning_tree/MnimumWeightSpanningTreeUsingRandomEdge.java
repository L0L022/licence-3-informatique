package spanning_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MnimumWeightSpanningTreeUsingRandomEdge {
	public static ArrayList<Edge> generateTree(Graph graph) {
		List<Edge> edges = new ArrayList<>();
		for (Edge edge : graph) {
			edges.add(edge);
		}

		ArrayList<Edge> result = new ArrayList<>();
		UnionFind unionFind = new UnionFind(graph.adjacency.size());
		Random rand = new Random();

		while (result.size() < graph.adjacency.size() - 1) {
			Edge edge = edges.get(rand.nextInt(edges.size()));

			if (unionFind.find(edge.v1) != unionFind.find(edge.v2)) {
				unionFind.union(edge.v1, edge.v2);
				result.add(new Edge(edge.v1, edge.v2, edge.weight));
			}
		}

		return result;
	}
}
