
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

/**
 * Graphe orienté.
 *
 * @author Loïc Escales
 *
 * @param <Vertex> Sommet du graphe.
 */
public class DirectedGraph<Vertex> {
	private Map<Vertex, LinkedList<Vertex>> incidency;
	private int order, size;

	/**
	 * Construit un graphe orienté.
	 */
	public DirectedGraph() {
		incidency = new HashMap<>();
		order = 0;
		size = 0;
	}

	/**
	 * Renvoie l'ordre du graphe (nombre de sommets).
	 *
	 * @return L'ordre du graphe.
	 */
	public int order() {
		return order;
	}

	/**
	 * Renvoie la taille du graphe (nombre d'arcs).
	 *
	 * @return La taille du graphe.
	 */
	public int size() {
		return size;
	}

	/**
	 * Ajoute le sommet au graphe. Ne fait rien si le sommet existe déjà.
	 *
	 * @param vertex Le sommet à ajouter.
	 */
	public void addVertex(Vertex vertex) {
		if (incidency.containsKey(vertex)) {
			return;
		}

		incidency.put(vertex, new LinkedList<Vertex>());
		++order;
	}

	/**
	 * Supprime le sommet du graphe ainsi que tous les arcs avec lesquels il est
	 * connecté. Ne fait rien si le sommet n'existe pas.
	 *
	 * @param vertex Le sommet à supprimer.
	 */
	public void removeVertex(Vertex vertex) {
		if (incidency.remove(vertex) == null) {
			return;
		}

		for (LinkedList<Vertex> vertices : incidency.values()) {
			vertices.remove(vertex);
		}

		--order;
	}

	/**
	 * Indique si le sommet est dans le graphe.
	 *
	 * @param vertex Le sommet à chercher.
	 * @return Vrai si le sommet est dans le graphe sinon faux.
	 */
	public boolean hasVertex(Vertex vertex) {
		return incidency.containsKey(vertex);
	}

	/**
	 * Indique si tous les somments sont dans le graphe.
	 *
	 * @param vertices Les sommets à chercher.
	 * @return Vrai si tous les sommets sont dans le graphe sinon faux.
	 */
	public boolean hasVertices(Collection<Vertex> vertices) {
		return incidency.keySet().containsAll(vertices);
	}

	/**
	 * Ajoute un arc au graphe. Ne fait rien si l'arc existe déjà. Les deux sommets
	 * sont automatiquement ajoutés.
	 *
	 * @param tail
	 * @param head
	 */
	public void addArrow(Vertex tail, Vertex head) {
		addVertex(tail);
		addVertex(head);

		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices.contains(head)) {
			return;
		}
		vertices.addLast(head);
		++size;
	}

	/**
	 * Supprime un arc du graphe. Ne fait rien si l'arc n'existe pas.
	 *
	 * @param tail Le sommet source.
	 * @param head Le sommet de destination.
	 */
	public void removeArrow(Vertex tail, Vertex head) {
		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices == null) {
			return;
		}
		if (!vertices.remove(head)) {
			return;
		}
		--size;
	}

	/**
	 * Indique si l'arc est dans le graphe.
	 *
	 * @param tail Le sommet source.
	 * @param head Le sommet de destination.
	 * @return Vrai si l'arc est dans le graphe sinon faut.
	 */
	public boolean hasArrow(Vertex tail, Vertex head) {
		LinkedList<Vertex> vertices = incidency.get(tail);
		if (vertices == null) {
			return false;
		}
		return vertices.contains(head);
	}

	/**
	 * Renvoie le transposé du graphe.
	 *
	 * @return Le transposé du graphe.
	 */
	public DirectedGraph<Vertex> transpose() {
		DirectedGraph<Vertex> graph = new DirectedGraph<>();

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

	/**
	 * Renvoie les composantes fortement connexes du graphe.
	 *
	 * @return Les composantes fortement connexes du graphe.
	 */
	List<DirectedGraph<Vertex>> stronglyConnectedComponents() {
		final Map<Vertex, DepthFirstSearch<Vertex>.VertexData> dfs_data = (new DepthFirstSearch<>(this)).vertexData();

		Vector<Vertex> ordered_vertices = new Vector<>(dfs_data.keySet());
		Collections.sort(ordered_vertices, new Comparator<Vertex>() {
			@Override
			public int compare(Vertex l, Vertex r) {
				Integer l_time = dfs_data.get(l).timeEnded;
				Integer r_time = dfs_data.get(r).timeEnded;
				return l_time.compareTo(r_time);
			}
		});
		Collections.reverse(ordered_vertices);

		DirectedGraph<Vertex> t_graph = transpose();
		Vector<DirectedGraph<Vertex>> components = new Vector<>();
		Set<Vertex> visited_vertices = new HashSet<>();

		for (Vertex vertex : ordered_vertices) {
			if (visited_vertices.contains(vertex)) {
				continue;
			}

			DirectedGraph<Vertex> component = new DirectedGraph<>();
			Stack<Vertex> vertices_to_explore = new Stack<>();
			vertices_to_explore.push(vertex);

			while (!vertices_to_explore.isEmpty()) {
				Vertex vertex_to_explore = vertices_to_explore.pop();
				visited_vertices.add(vertex_to_explore);
				component.addVertex(vertex_to_explore);

				for (Vertex adjacent_v : t_graph.adjacentVertices(vertex_to_explore)) {
					if (!visited_vertices.contains(adjacent_v)) {
						vertices_to_explore.push(adjacent_v);
					}
				}
			}

			for (Vertex c_vertex : component.vertices()) {
				for (Vertex adj : adjacentVertices(c_vertex)) {
					if (component.hasVertex(adj)) {
						component.addArrow(c_vertex, adj);
					}
				}
			}

			components.add(component);
		}
		return components;
	}

	/**
	 * Renvoie les sommets du graphe.
	 *
	 * @return Les sommets du graphe.
	 */
	Set<Vertex> vertices() {
		return Collections.unmodifiableSet(incidency.keySet());
	}

	/**
	 * Renvoie les sommets adjacents à un sommet.
	 *
	 * @param vertex Le sommet adjacent aux sommets.
	 * @return Les sommets adjacents à un sommet.
	 */
	Collection<Vertex> adjacentVertices(Vertex vertex) {
		return Collections.unmodifiableCollection(incidency.get(vertex));
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof DirectedGraph<?>)) {
			return false;
		}

		DirectedGraph<Vertex> graph = (DirectedGraph<Vertex>) o;

		return order == graph.order && size == graph.size && incidency.equals(graph.incidency);
	}
}
