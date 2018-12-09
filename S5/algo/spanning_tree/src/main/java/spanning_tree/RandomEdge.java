package spanning_tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomEdge implements MinimumWeightSpanningTreeGenerator {

	private static Random rand = new Random();

	@Override
	public List<Edge> generateTree(Graph graph) {
		List<Edge> edges = new ArrayList<>();
		for (Edge edge : graph) {
			edges.add(edge);
		}

		List<Edge> result = new ArrayList<>();
		UnionFind unionFind = new UnionFind(graph.adjacency.size());

		while (result.size() < graph.order - 1) {
			Edge edge = edges.get(rand.nextInt(edges.size()));

			if (unionFind.find(edge.v1) != unionFind.find(edge.v2)) {
				unionFind.union(edge.v1, edge.v2);
				result.add(edge);
			}
		}

		return result;
	}

	@Override
	public String name() {
		return "RandomEdge";
	}
}
