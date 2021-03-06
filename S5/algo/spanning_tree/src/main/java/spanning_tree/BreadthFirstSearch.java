package spanning_tree;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/*
 * Parcours en largeur.
 */

public class BreadthFirstSearch implements MinimumWeightSpanningTreeGenerator {

	public static ArrayList<Arc> generateTree(Graph graph, int vertex) {
		BitSet isVertexVisited = new BitSet(graph.order);
		isVertexVisited.set(vertex, true);

		ArrayList<Arc> arcs = new ArrayList<>();

		ArrayList<Integer> vertex_left = new ArrayList<>();
		vertex_left.add(vertex);

		for (int i = 0; i < vertex_left.size(); ++i) {
			int v = vertex_left.get(i);

			for (Edge edge : graph.adjacency.get(v)) {
				int opposite = edge.oppositeExtremity(v);
				if (isVertexVisited.get(opposite)) {
					continue;
				}
				isVertexVisited.set(opposite, true);

				vertex_left.add(opposite);

				arcs.add(new Arc(new Edge(v, opposite, edge.weight), false));
			}
		}

		return arcs;
	}

	@Override
	public List<Edge> generateTree(Graph graph) {
		ArrayList<Edge> tree = new ArrayList<>();

		for (Arc arc : generateTree(graph, 0)) {
			tree.add(arc.support);
		}

		return tree;
	}

	@Override
	public String name() {
		return "BreadthFirstSearch";
	}

}
