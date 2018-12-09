package spanning_tree;

import java.util.ArrayList;
import java.util.List;

public class BreadthFirstSearch implements MinimumWeightSpanningTreeGenerator {

	public static ArrayList<Arc> generateTree(Graph graph, int vertex, List<Boolean> visited_vertex) {
		ArrayList<Arc> arcs = new ArrayList<>();

		ArrayList<Integer> vertex_left = new ArrayList<>();
		vertex_left.add(vertex);
		visited_vertex.set(vertex, true);

		for (int i = 0; i < vertex_left.size(); ++i) {
			int v = vertex_left.get(i);

			for (Edge edge : graph.adjacency.get(v)) {
				int opposite = edge.oppositeExtremity(v);
				if (visited_vertex.get(opposite)) {
					continue;
				}
				visited_vertex.set(opposite, true);
				vertex_left.add(opposite);

				arcs.add(new Arc(new Edge(v, opposite, 0.0), false));
				// arcs.addAll(generateTree(graph, opposite));
			}
		}

		return arcs;
	}

	public static ArrayList<Arc> generateTree(Graph graph, int vertex) {
		List<Boolean> visited_vertex = new ArrayList<>(graph.adjacency.size());
		for (int i = 0; i < graph.adjacency.size(); ++i) {
			visited_vertex.add(false);
		}
		return generateTree(graph, vertex, visited_vertex);
	}

	@Override
	public List<Edge> generateTree(Graph graph) {
		ArrayList<Edge> randomTree;

		ArrayList<Arc> randomArcTree = BreadthFirstSearch.generateTree(graph, 0);
		randomTree = new ArrayList<>();
		for (Arc a : randomArcTree) {
			randomTree.add(a.support);
		}

		return randomTree;
	}

	@Override
	public String name() {
		return "BreadthFirstSearch";
	}

}
