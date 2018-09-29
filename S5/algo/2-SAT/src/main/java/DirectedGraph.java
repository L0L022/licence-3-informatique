
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

public class DirectedGraph<Vertex> {
	private Map<Vertex, LinkedList<Vertex>> incidency;
	private int order, size;
	
	DirectedGraph() {
		incidency = new HashMap<>();
		order = 0;
		size = 0;
	}
	
	int order() {
		return order;
	}
	
	int size() {
		return size;
	}
	
	void addVertex(Vertex vertex) {
		if (incidency.containsKey(vertex)) return;
		
		incidency.put(vertex, new LinkedList<Vertex>());
		++order;
	}
	
	void removeVertex(Vertex vertex) {
		if (incidency.remove(vertex) == null) return;
		
		for (LinkedList<Vertex> vertices : incidency.values()) {
			vertices.remove(vertex);
		}
		
		--order;
	}
	
	boolean hasVertex(Vertex vertex) {
		return incidency.containsKey(vertex);
	}
	
	void addArrow(Vertex tail, Vertex head) {
		addVertex(tail);
		addVertex(head);
		
		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices.contains(head)) return;
		vertices.addLast(head);
		++size;
	}
	
	void removeArrow(Vertex tail, Vertex head) {
		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices == null) return;
		if (!vertices.remove(head)) return;
		--size;
	}
	
	boolean hasArrow(Vertex tail, Vertex head) {
		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices == null) return false;
		return vertices.contains(head);
	}
	
	DirectedGraph<Vertex> transpose() {
		DirectedGraph<Vertex> graph = new DirectedGraph<Vertex>();
		
		for (Map.Entry<Vertex, LinkedList<Vertex>> entry : incidency.entrySet()) {
			for (Vertex vertex : entry.getValue()) {
				graph.addArrow(vertex, entry.getKey());
			}
		}
		
		return graph;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry<Vertex, LinkedList<Vertex>> entry : incidency.entrySet()) {
			sb.append("[" + entry.getKey().toString() + "]");
			for (Vertex vertex : entry.getValue()) {
				sb.append(" -> [" + vertex.toString() + "]");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}

//
//public class Graph<Label> {
//
//    private class Edge {
//        public int source;
//        public int destination;
//        public Label label;
//
//        public Edge(int from, int to, Label label) {
//            this.source = from;
//            this.destination = to;
//            this.label = label;
//        }
//    }
//
//    private int cardinal;
//    private ArrayList<LinkedList<Edge>> incidency;
//
//
//    public Graph(int size) {
//        cardinal = size;
//        incidency = new ArrayList<LinkedList<Edge>>(size+1);
//        for (int i = 0;i<cardinal;i++) {
//            incidency.add(i, new LinkedList<Edge>());
//        }
//    }
//
//    public int order() {
//        return cardinal;
//    }
//
//    public void addArc(int source, int dest, Label label) {
//        incidency.get(source).addLast(new Edge(source,dest,label));
//    }
//
//    public String toString() {
//        String result = new String("");
//        result.concat(cardinal + "\n");
//        for (int i = 0; i<cardinal;i++) {
//            for (Edge e : incidency.get(i)) {
//                result.concat(e.source + " " + e.destination + " "
//                        + e.label.toString() + "\n");
//            }
//        }
//        return result;
//
//    }
//}
