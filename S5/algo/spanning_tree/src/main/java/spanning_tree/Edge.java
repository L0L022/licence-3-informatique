package spanning_tree;

public class Edge implements Comparable<Edge> {

	protected int v1;
	protected int v2;
	double weight;

	public Edge(int v1, int v2, double weight) {
		this.v1 = v1;
		this.v2 = v2;
		this.weight = weight;
	}

	@Override
	public int compareTo(Edge e) {
		if (weight == e.weight) {
			return 0;
		}
		if (weight < e.weight) {
			return -1;
		}
		return 1;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Edge)) {
			return false;
		}

		Edge edg = (Edge) o;

		return (v1 == edg.v1 && v2 == edg.v2) || (v1 == edg.v2 && v2 == edg.v1);
	}

	public int oppositeExtremity(int vertex) {
		if (v1 != vertex && v2 != vertex) {
			throw new RuntimeException("Le sommet n'appartient pas au côté.");
		}

		return (v1 == vertex ? v2 : v1);
	}

	public int getV1() {
		return v1;
	}

	public int getV2() {
		return v2;
	}

}
