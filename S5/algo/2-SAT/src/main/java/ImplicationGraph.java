
import java.util.HashMap;
import java.util.Map;

public class ImplicationGraph {
	
	Graph<String> graph;
	Map<Literal, Integer> lit2index;
	
	// nb_literals avec les négatifs !
	ImplicationGraph(Clause[] clauses, int nb_literals) {
		graph = new Graph<String>(nb_literals);
		lit2index = new HashMap<Literal, Integer>(nb_literals);
		
		int index = 0;
		
		for (Clause clause : clauses) {
			
			if (!lit2index.containsKey(clause.left())) {
				lit2index.put(clause.left(), index++);
			}
			
			int left_index = lit2index.get(clause.left());
			
//			clause.left.not() -> clause.right
//			clause.right.not() -> clause.left
		}
	}
}
