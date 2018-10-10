
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

/**
 * Un problème 2-SAT.
 *
 * @author Loïc Escales
 *
 */
public class TwoSAT {
	private Clause[] clauses;

	/**
	 * Construit le problème 2-SAT à partir d'une liste de clauses.
	 *
	 * @param clauses La liste des clauses.
	 */
	public TwoSAT(Clause[] clauses) {
		this.clauses = clauses;
	}

	/**
	 * Lit les clauses stockées dans un fichier et renvoie le problème 2-SAT qui en
	 * découle. Les littéraux doivent être représentés par des nombres. Les nombres
	 * positifs correspondent à des littéraux posififs et les nombres négatifs
	 * correspondent à des littéraux négatifs. Les littéraux sont remplacés par les
	 * lettres de l'alphabet (1 devient a, 2 devient b, etc). S'il y a plus de 26
	 * littéraux alors les littéraux sont représentés par les même nombres (1 reste
	 * 1, 2 reste 2, etc).
	 *
	 * @param fileName Le chemin vers le fichier.
	 * @return Le problème 2-SAT construit à partir du fichier.
	 * @throws IOException
	 */
	public static TwoSAT fromFile(String fileName) throws IOException {
		Scanner s = new Scanner(new BufferedReader(new FileReader(new File(fileName))));

		int nb_literals = s.nextInt();
		char lit_char = 'a';
		Map<Integer, Literal> number_to_literal = new HashMap<>();

		for (int i = 1; i <= nb_literals; ++i) {
			Literal literal = new Literal(nb_literals > 26 ? "" + i : "" + lit_char++);
			number_to_literal.put(i, literal);
			number_to_literal.put(-i, literal.not());
		}

		Vector<Clause> clauses = new Vector<>();

		while (s.hasNextInt()) {
			Literal left_lit = number_to_literal.get(s.nextInt());
			Literal right_lit = number_to_literal.get(s.nextInt());
			clauses.add(new Clause(left_lit, right_lit));
		}

		s.close();

		return new TwoSAT(clauses.toArray(new Clause[clauses.size()]));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("{ ");
		int count = 0;
		for (Clause clause : clauses) {
			if (count > 0) {
				sb.append(", ");
			}
			sb.append(clause.toString());
			++count;
		}
		sb.append(" }");
		return sb.toString();
	}

	/**
	 * Indique si le problème 2-SAT est satisfaisable ou non.
	 *
	 * @return Vrai si le problème est satisfaisable sinon non.
	 */
	public boolean isSatisfiable() {
		ImplicationGraph graph = new ImplicationGraph(clauses);
		List<DirectedGraph<Literal>> components = graph.stronglyConnectedComponents();
		return !a_literal_and_its_opposite_exist(components);
	}

	/**
	 * Indique si un littéral et son opposé existent en même temps dans la
	 * composante.
	 *
	 * @param component La composante à explorer.
	 * @return Vrai si un littéral et son opposé existent en même temps sinon faux.
	 */
	private boolean a_literal_and_its_opposite_exist(DirectedGraph<Literal> component) {
		Set<Literal> literals = new HashSet<>();
		for (Literal literal : component.vertices()) {
			literals.add(literal.abs());
		}
		return literals.size() != component.vertices().size();
	}

	/**
	 * Indique si un littéral et son opposé existent en même temps dans chacune des
	 * composantes.
	 *
	 * @param components Les composantes à explorer.
	 * @return Vrai si un littéral et son opposé existent en même temps sinon faux.
	 */
	private boolean a_literal_and_its_opposite_exist(List<DirectedGraph<Literal>> components) {
		for (DirectedGraph<Literal> component : components) {
			if (a_literal_and_its_opposite_exist(component)) {
				return true;
			}
		}
		return false;
	}
}
