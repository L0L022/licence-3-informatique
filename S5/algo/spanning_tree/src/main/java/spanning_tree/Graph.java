package spanning_tree;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Graph implements Iterable<Edge> {
	// classe de graphe non orientés permettant de manipuler
	// en même temps des arcs (orientés)
	// pour pouvoir stocker un arbre couvrant, en plus du graphe

	private class IteratorGraph implements Iterator<Edge> {

		private Set<Edge> edges;
		private Iterator<Edge> iterator;

		public IteratorGraph() {
			edges = new HashSet<>();
			for (List<Edge> edges_graph : adjacency) {
				for (Edge edge : edges_graph) {
					edges.add(edge);
				}
			}
			iterator = edges.iterator();
		}

		@Override
		public boolean hasNext() {
			return iterator.hasNext();
		}

		@Override
		public Edge next() {
			return iterator.next();
		}

	}

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
		order = upperBound;
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
		return new IteratorGraph();
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

	static String exportDotEdges(Collection<Edge> edges) {
		DecimalFormat df = new DecimalFormat("#0.0#");
		StringBuilder sb = new StringBuilder();
		sb.append("digraph g {\n\tedge [\n\t\tarrowhead=\"none\"\n\t];\n");
		for (Edge edge : edges) {
			sb.append("\t" + String.valueOf(edge.v1) + " -> " + String.valueOf(edge.v2) + " [label=\""
					+ df.format(edge.weight) + "\"]\n");
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
