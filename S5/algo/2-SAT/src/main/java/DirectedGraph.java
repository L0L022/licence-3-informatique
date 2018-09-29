
import java.util.HashMap;
import java.util.Map;
import java.util.LinkedList;

public class DirectedGraph<Vertex> {
	private Map<Vertex, LinkedList<Vertex>> incidency;
	private int order, size;
	
	public DirectedGraph() {
		incidency = new HashMap<>();
		order = 0;
		size = 0;
	}
	
	public int order() {
		return order;
	}
	
	public int size() {
		return size;
	}
	
	public void addVertex(Vertex vertex) {
		if (incidency.containsKey(vertex)) return;
		
		incidency.put(vertex, new LinkedList<Vertex>());
		++order;
	}
	
	public void removeVertex(Vertex vertex) {
		if (incidency.remove(vertex) == null) return;
		
		for (LinkedList<Vertex> vertices : incidency.values()) {
			vertices.remove(vertex);
		}
		
		--order;
	}
	
	public boolean hasVertex(Vertex vertex) {
		return incidency.containsKey(vertex);
	}
	
	public void addArrow(Vertex tail, Vertex head) {
		addVertex(tail);
		addVertex(head);
		
		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices.contains(head)) return;
		vertices.addLast(head);
		++size;
	}
	
	public void removeArrow(Vertex tail, Vertex head) {
		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices == null) return;
		if (!vertices.remove(head)) return;
		--size;
	}
	
	public boolean hasArrow(Vertex tail, Vertex head) {
		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices == null) return false;
		return vertices.contains(head);
	}
	
	public DirectedGraph<Vertex> transpose() {
		DirectedGraph<Vertex> graph = new DirectedGraph<Vertex>();
		
		for (Map.Entry<Vertex, LinkedList<Vertex>> entry : incidency.entrySet()) {
			for (Vertex vertex : entry.getValue()) {
				graph.addArrow(vertex, entry.getKey());
			}
		}
		
		return graph;
	}
	
	@Override
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

