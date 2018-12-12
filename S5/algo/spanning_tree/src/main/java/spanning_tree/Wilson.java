package spanning_tree;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/*
 * Cette classer permet d'obtenir un arbre couvrant aléatoire par l'algorithme de Wilson.
 */

public class Wilson implements MinimumWeightSpanningTreeGenerator {

	@Override
	public String name() {
		return "Wilson";
	}

	@Override
	public List<Edge> generateTree(Graph graph) {
		List<Edge> edges = new ArrayList<>();

		int v = graph.maxDegVertex();

		BitSet isVertexInGraph = new BitSet(graph.order);
		isVertexInGraph.set(v, true);

		Edge[] path = new Edge[graph.adjacency.size()];

		while (edges.size() != graph.order - 1) {
			int u = -1;
			do {
				u = graph.randomVertex();
			} while (isVertexInGraph.get(u));

			int begin = u;

			path[u] = graph.randomEdge(u);
			int w = path[u].oppositeExtremity(u);

			while (!isVertexInGraph.get(w)) {
				u = w;

				path[u] = graph.randomEdge(u);
				w = path[u].oppositeExtremity(u);
			}

			v = begin;
			while (v != w) {
				isVertexInGraph.set(v, true);
				edges.add(path[v]);
				v = path[v].oppositeExtremity(v);
			}
		}

		return edges;
	}
}
