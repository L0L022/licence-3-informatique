package spanning_tree;

// simple union-find based on int[] arrays
// for  "parent" and "rank"
// implements the "disjoint-set forests" described at
// http://en.wikipedia.org/wiki/Disjoint-set_data_structure
// which have almost constant "amortized" cost per operation
// (actually O(inverse Ackermann))
import java.util.Arrays;

public class UnionFind {

	private int[] _parent;
	private int[] _rank;

	public int find(int i) {

		int p = _parent[i];
		if (i == p) {
			return i;
		}
		return _parent[i] = find(p);

	}

	public void union(int i, int j) {

		int root1 = find(i);
		int root2 = find(j);

		if (root2 == root1) {
			return;
		}

		if (_rank[root1] > _rank[root2]) {
			_parent[root2] = root1;
		} else if (_rank[root2] > _rank[root1]) {
			_parent[root1] = root2;
		} else {
			_parent[root2] = root1;
			_rank[root1]++;
		}
	}

	public UnionFind(int max) {

		_parent = new int[max];
		_rank = new int[max];

		for (int i = 0; i < max; i++) {
			_parent[i] = i;
		}
	}

	@Override
	public String toString() {
		return "<UnionFind\np " + Arrays.toString(_parent) + "\nr " + Arrays.toString(_rank) + "\n>";
	}

	/*
	 * public static void main(String[] args) {
	 * 
	 * UnionFind uf = new UnionFind(5); System.out.println(uf);
	 * 
	 * uf.union(1,2); System.out.println("union 1 2"); System.out.println(uf);
	 * 
	 * uf.union(1,2); System.out.println("union 1 2"); System.out.println(uf);
	 * 
	 * uf.union(3,4); System.out.println("union 3 4"); System.out.println(uf);
	 * 
	 * uf.union(1,0); System.out.println("union 1 0"); System.out.println(uf);
	 * 
	 * uf.union(1,3); System.out.println("union 1 3"); System.out.println(uf);
	 * 
	 * uf.find(4); System.out.println("find 4"); System.out.println(uf);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * The tests in main should produce:
	 * 
	 * <UnionFind p [0, 1, 2, 3, 4] r [0, 0, 0, 0, 0] > union 1 2 <UnionFind p [0,
	 * 1, 1, 3, 4] r [0, 1, 0, 0, 0] > union 1 2 <UnionFind p [0, 1, 1, 3, 4] r [0,
	 * 1, 0, 0, 0] > union 3 4 <UnionFind p [0, 1, 1, 3, 3] r [0, 1, 0, 1, 0] >
	 * union 1 0 <UnionFind p [1, 1, 1, 3, 3] r [0, 1, 0, 1, 0] > union 1 3
	 * <UnionFind p [1, 1, 1, 1, 3] r [0, 2, 0, 1, 0] > find 4 <UnionFind p [1, 1,
	 * 1, 1, 1] r [0, 2, 0, 1, 0] >
	 */

}
