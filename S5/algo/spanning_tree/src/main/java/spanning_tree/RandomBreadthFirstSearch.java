package spanning_tree;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * Parcours en largeur aléatoire.
 */
public class RandomBreadthFirstSearch implements MinimumWeightSpanningTreeGenerator {

	private static Random rand = new Random();

	public static ArrayList<Arc> generateTree(Graph graph, int vertex) {
		BitSet isVertexVisited = new BitSet(graph.order);
		isVertexVisited.set(vertex, true);

		ArrayList<Arc> arcs = new ArrayList<>();

		ArrayList<Integer> vertex_left = new ArrayList<>();
		vertex_left.add(vertex);

		for (int i = 0; i < vertex_left.size(); ++i) {
			int v = vertex_left.get(i);

			List<Edge> randomEdges = new ArrayList<>(graph.adjacency.get(v));
			Collections.shuffle(randomEdges, rand);

			for (Edge edge : randomEdges) {
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

		for (Arc arc : generateTree(graph, graph.randomVertex())) {
			tree.add(arc.support);
		}

		return tree;
	}

	@Override
	public String name() {
		return "RandomBreadthFirstSearch";
	}

}
