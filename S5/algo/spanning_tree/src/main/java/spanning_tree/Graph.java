package spanning_tree;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
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

	private static Random rand = new Random();

	int order;
	// int edgeCardinality;

	List<? extends List<Edge>> adjacency;
//	ArrayList<LinkedList<Arc>> inAdjacency;
//	ArrayList<LinkedList<Arc>> outAdjacency;

	public boolean isVertex(int index) {
		return adjacency.get(index).size() > 0;
	}

	public <T> List<? extends List<T>> makeList(int size) {
		List<ArrayList<T>> res = new ArrayList<>(size);
		for (int i = 0; i <= size; i++) {
			res.add(new ArrayList<T>());
		}
		return res;
	}

	public Graph(int upperBound) {
		adjacency = makeList(upperBound);
	}
//
//	public void addVertex(int indexVertex) {
//		// à remplir
//	}
//
//	public void ensureVertex(int indexVertex) {
//		// à remplir
//	}
//
//	public void addArc(Arc arc) {
//		// à remplir
//	}

	public void addEdge(Edge e) {
		adjacency.get(e.v1).add(e);
		adjacency.get(e.v2).add(e);

		if (adjacency.get(e.v1).size() == 1) {
			++order;
		}

		if (adjacency.get(e.v2).size() == 1) {
			++order;
		}
	}

	@Override
	public Iterator<Edge> iterator() {
		return new IteratorGraph();
	}

	public List<Arc> outNeighbours(int sommet) {
		// TODO Auto-generated method stub
		return null;
	}

	public int randomVertex() {
		int v;
		do {
			v = rand.nextInt(adjacency.size());
		} while (!isVertex(v));
		return v;
	}

	public Edge randomEdge(int vertex) {
		List<Edge> adjacencyEdges = adjacency.get(vertex);
		return adjacencyEdges.get(rand.nextInt(adjacencyEdges.size()));
	}

	public int maxDegVertex() {
		int vertex = 0;
		for (int i = 1; i < adjacency.size(); ++i) {
			if (adjacency.get(vertex).size() < adjacency.get(i).size()) {
				vertex = i;
			}
		}
		return vertex;
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
