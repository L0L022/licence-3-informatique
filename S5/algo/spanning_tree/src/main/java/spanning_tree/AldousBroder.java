package spanning_tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AldousBroder implements MinimumWeightSpanningTreeGenerator {

	@Override
	public List<Edge> generateTree(Graph graph) {
		List<Edge> edges = new ArrayList<>();

		int actualVertex = graph.randomVertex();
		Set<Integer> visitedVertices = new HashSet<>(graph.order);
		visitedVertices.add(actualVertex);

		while (visitedVertices.size() != graph.order) {
			Edge edge = graph.randomEdge(actualVertex);
			int oppositeVertex = edge.oppositeExtremity(actualVertex);
			if (!visitedVertices.contains(oppositeVertex)) {
				edges.add(edge);
				visitedVertices.add(oppositeVertex);
			}
			actualVertex = oppositeVertex;
		}

		return edges;
	}

	@Override
	public String name() {
		return "AldousBroder";
	}
}