package spanning_tree;

import java.util.List;

public interface MinimumWeightSpanningTreeGenerator {
	public String name();

	public List<Edge> generateTree(Graph graph);
}
