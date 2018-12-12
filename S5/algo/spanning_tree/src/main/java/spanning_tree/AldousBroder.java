package spanning_tree;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/*
 * Cette classer permet d'obtenir un arbre couvrant aléatoire par l'algorithme d'Aldous Broder.
 */

public class AldousBroder implements MinimumWeightSpanningTreeGenerator {

	@Override
	public List<Edge> generateTree(Graph graph) {
		List<Edge> edges = new ArrayList<>();

		int actualVertex = graph.randomVertex();
		BitSet isVertexVisited = new BitSet(graph.order);
		isVertexVisited.set(actualVertex, true);

		while (edges.size() != graph.order - 1) {
			Edge edge = graph.randomEdge(actualVertex);
			int oppositeVertex = edge.oppositeExtremity(actualVertex);
			if (!isVertexVisited.get(oppositeVertex)) {
				edges.add(edge);
				isVertexVisited.set(oppositeVertex, true);
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