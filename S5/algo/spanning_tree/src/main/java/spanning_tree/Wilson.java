package spanning_tree;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Wilson implements MinimumWeightSpanningTreeGenerator {

	@Override
	public String name() {
		return "Wilson";
	}

	@Override
	public List<Edge> generateTree(Graph graph) {
		List<Edge> edges = new ArrayList<>();

		int v = graph.maxDegVertex();

		BitSet isVertexVisited = new BitSet(graph.order);
		isVertexVisited.set(v, true);

		Edge[] chemin = new Edge[graph.adjacency.size()];

		while (edges.size() != graph.order - 1) {
			int u = -1;
			do {
				u = graph.randomVertex();
			} while (isVertexVisited.get(u));

			int begin = u;

			chemin[u] = graph.randomEdge(u);
			int w = chemin[u].oppositeExtremity(u);

			while (!isVertexVisited.get(w)) {
				u = w;

				chemin[u] = graph.randomEdge(u);
				w = chemin[u].oppositeExtremity(u);
			}

			v = begin;
			while (v != w) {
				isVertexVisited.set(v, true);
				edges.add(chemin[v]);
				v = chemin[v].oppositeExtremity(v);
			}
		}

		return edges;
	}
}
