
public class ImplicationGraph extends DirectedGraph<Literal> {
	public ImplicationGraph(Clause[] clauses) {
		for (Clause clause : clauses) {
			addArrow(clause.left().not(), clause.right());
			addArrow(clause.right().not(), clause.left());
		}
	}
}
