package spanning_tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kruskal implements MinimumWeightSpanningTreeGenerator {
	@Override
	public List<Edge> generateTree(Graph graph) {
		List<Edge> result = new ArrayList<>();

		UnionFind unionFind = new UnionFind(graph.adjacency.size());

		List<Edge> edges = new ArrayList<>();
		for (Edge edge : graph) {
			edges.add(edge);
		}

		Collections.sort(edges);

		for (Edge edge : edges) {
			if (unionFind.find(edge.v1) != unionFind.find(edge.v2)) {
				unionFind.union(edge.v1, edge.v2);
				result.add(new Edge(edge.v1, edge.v2, edge.weight));
			}
		}

		return result;
	}

	@Override
	public String name() {
		return "Kruskal";
	}
}
