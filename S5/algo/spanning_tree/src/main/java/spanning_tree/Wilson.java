package spanning_tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Wilson implements MinimumWeightSpanningTreeGenerator {

	@Override
	public String name() {
		return "Wilson";
	}

	@Override
	public List<Edge> generateTree(Graph graph) {
		List<Edge> edges = new ArrayList<>();

		int v = graph.maxDegVertex();
		Set<Integer> visitedVertices = new HashSet<>(graph.order);
		visitedVertices.add(v);
		Edge[] chemin = new Edge[graph.adjacency.size()];

		while (visitedVertices.size() != graph.order) {
			int u = -1;
			do {
				u = graph.randomVertex();
			} while (visitedVertices.contains(u));

			int begin = u;

			chemin[u] = graph.randomEdge(u);
			int w = chemin[u].oppositeExtremity(u);

			while (!visitedVertices.contains(w)) {
				u = w;

				chemin[u] = graph.randomEdge(u);
				w = chemin[u].oppositeExtremity(u);
			}

			v = begin;
			while (v != w) {
				visitedVertices.add(v);
				edges.add(chemin[v]);
				v = chemin[v].oppositeExtremity(v);
			}
		}

		return edges;
	}
}
