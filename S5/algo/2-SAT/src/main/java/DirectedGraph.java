
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import java.util.LinkedList;
import java.util.List;

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
	
	public boolean hasVertices(Collection<Vertex> vertices) {
		return incidency.keySet().containsAll(vertices);
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
			graph.addVertex(entry.getKey());
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
	
	List<DirectedGraph<Vertex>> stronglyConnectedComponents() {
		System.out.println("_____ CALCUL ______");
		final Map<Vertex, DepthFirstSearch<Vertex>.VertexData> dfs_data = (new DepthFirstSearch<>(this)).vertexData();
		
		Vector<Vertex> vertices = new Vector<>(dfs_data.keySet());
		Collections.sort(vertices, new Comparator<Vertex>() {
	        @Override
	        public int compare(Vertex l, Vertex r)
	        {
	        	Integer l_time = dfs_data.get(l).timeEnded;
	        	Integer r_time = dfs_data.get(r).timeEnded;
	            return l_time.compareTo(r_time);
	        }
	    });
		Collections.reverse(vertices);
		
//		System.out.println(vertices.toString());
		for (Vertex vertex : vertices) {
			System.out.println(vertex.toString() + " : " + dfs_data.get(vertex).timeDiscovered + ", " + dfs_data.get(vertex).timeEnded);
		}
		
		DirectedGraph<Vertex> t_graph = transpose();
		
		System.out.println(t_graph.toString());
		
		Vector<DirectedGraph<Vertex>> components = new Vector<>();
		Set<Vertex> visited_vertex = new HashSet<>();
		
		for (Vertex vertex : vertices) {
			if (visited_vertex.contains(vertex)) continue;
			
			System.out.println("nouveau graphe");
			DirectedGraph<Vertex> graph = new DirectedGraph<>();

			Stack<Vertex> stack = new Stack<>();
			stack.push(vertex);
			
			while (!stack.isEmpty()) {
				Vertex parent = stack.pop();
				if (visited_vertex.contains(parent)) continue;
				visited_vertex.add(parent);
				System.out.println("parent : " + parent.toString());
				graph.addVertex(parent);
								
				for (Vertex child : t_graph.children(parent)) {
					System.out.println(child.toString());
					if (visited_vertex.contains(child)) continue;
					stack.push(child);
					graph.addArrow(parent, child);
				}
			}
			System.out.println(graph.toString());
			components.add(graph);
		}
		System.out.println("_____ CALCUL ______");
		return components;//components.toArray((DirectedGraph<Vertex>[]) new Object[components.size()]);
	}
	
	Set<Vertex> vertices() {
		return Collections.unmodifiableSet(incidency.keySet());
	}
	
	Collection<Vertex> children(Vertex vertex) {
		return Collections.unmodifiableCollection(incidency.get(vertex));
	}
}

