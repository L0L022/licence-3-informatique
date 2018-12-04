package spanning_tree;

public class Arc {
	Edge support;
	boolean reversed;

	public Arc(Edge e, boolean reversed) {
		support = e;
		this.reversed = reversed;
	}

	public int getSource() {
		return (reversed ? support.v2 : support.v1);
	}

	public int getDest() {
		return (reversed ? support.v1 : support.v2);
	}
}
