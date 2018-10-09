
/**
 * Graphe d'implication.
 *
 * @author Loïc Escales
 *
 */
public class ImplicationGraph extends DirectedGraph<Literal> {
	/**
	 * Construit un graphe d'implication à partir d'une liste de clauses.
	 *
	 * @param clauses Liste de clauses.
	 */
	public ImplicationGraph(Clause[] clauses) {
		for (Clause clause : clauses) {
			addArrow(clause.left().not(), clause.right());
			addArrow(clause.right().not(), clause.left());
		}
	}
}
