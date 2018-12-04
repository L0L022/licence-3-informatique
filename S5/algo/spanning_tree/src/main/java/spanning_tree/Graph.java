package spanning_tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph implements Iterable<Edge> {
	// classe de graphe non orientés permettant de manipuler
	// en même temps des arcs (orientés)
	// pour pouvoir stocker un arbre couvrant, en plus du graphe

	int order;
	int edgeCardinality;

	ArrayList<LinkedList<Edge>> adjacency;
	ArrayList<LinkedList<Arc>> inAdjacency;
	ArrayList<LinkedList<Arc>> outAdjacency;

	public boolean isVertex(int index) {
		return false;
		// à remplir
	}

	public <T> ArrayList<LinkedList<T>> makeList(int size) {
		ArrayList<LinkedList<T>> res = new ArrayList<>(size);
		for (int i = 0; i <= size; i++) {
			res.add(new LinkedList<>());
		}
		return res;
	}

	public Graph(int upperBound) {
		adjacency = makeList(upperBound);
	}

	public void addVertex(int indexVertex) {
		// à remplir
	}

	public void ensureVertex(int indexVertex) {
		// à remplir
	}

	public void addArc(Arc arc) {
		// à remplir
	}

	public void addEdge(Edge e) {
		adjacency.get(e.v1).add(e);
		adjacency.get(e.v2).add(e);
	}

	@Override
	public Iterator<Edge> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Arc> outNeighbours(int sommet) {
		// TODO Auto-generated method stub
		return null;
	}

	static String exportDotEdges(List<? extends Collection<Edge>> graph) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		for (int src = 0; src < graph.size(); ++src) {
			for (Edge edge : graph.get(src)) {
				sb.append("\t" + String.valueOf(src) + " -> " + String.valueOf(edge.oppositeExtremity(src)) + "\n");
			}
		}
		sb.append("}\n");
		return sb.toString();
	}

	static String exportDotArcs(Collection<Arc> arcs) {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph G {\n");
		for (Arc arc : arcs) {
			sb.append("\t" + String.valueOf(arc.getSource()) + " -> " + String.valueOf(arc.getDest()) + "\n");
		}
		sb.append("}\n");
		return sb.toString();
	}
}
