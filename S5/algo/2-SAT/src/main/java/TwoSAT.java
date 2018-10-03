
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

public class TwoSAT {
	private Clause[] clauses;

	/**
	 * Construit le problème 2-SAT à partir à partir d'une liste de clauses.
	 * 
	 * @param clauses
	 */
	public TwoSAT(Clause[] clauses) {
		this.clauses = clauses;
	}

	/**
	 * Lit les clauses stockées dans un fichier et renvoie le problème 2-SAT qui en
	 * découle. Le fichier doit contenir des littéraux représentaient par des
	 * nombres. Les nombres positifs correspondent à des littéraux posifif et vice
	 * versa. Les littéraux sont remplacés par les lettres de l'alphabet. S'il y a
	 * plus de 26 littéraux alors les littéraux seront représentés par des chiffres.
	 * 
	 * @param fileName
	 *            Le chemin vers le fichier.
	 * @return Le problème 2-SAT construit à partir du fichier.
	 * @throws IOException
	 */
	public static TwoSAT fromFile(String fileName) throws IOException {
		Scanner s = new Scanner(new BufferedReader(new FileReader(new File(fileName))));
		Map<Integer, Literal> literals = new HashMap<Integer, Literal>();
		Vector<Clause> clauses = new Vector<Clause>();

		int nb_literals = s.nextInt();
		char next_char = 'a';

		for (int i = 1; i <= nb_literals; ++i) {
			Literal literal = new Literal(nb_literals > 26 ? "" + i : "" + next_char++);
			literals.put(i, literal);
			literals.put(-i, literal.not());
		}

		while (s.hasNext()) {
			Literal left_lit = literals.get(s.nextInt()), right_lit = literals.get(s.nextInt());
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
	 * Indique si le problème 2-SAT est satisfiable ou non.
	 * 
	 * @return Vrai si le problème est satifiable sinon non.
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
	 * @param component
	 *            La composante à explorer.
	 * @return Vrai si un littéral et son opposé existent en même temps sinon faux.
	 */
	private boolean a_literal_and_its_opposite_exist(DirectedGraph<Literal> component) {
		Set<Literal> literals = new HashSet<>();
		for (Literal literal : component.vertices()) {
			literals.add(literal.abs());
		}
		return literals.size() != component.vertices().size();
	}

	private boolean a_literal_and_its_opposite_exist(List<DirectedGraph<Literal>> components) {
		for (DirectedGraph<Literal> component : components) {
			if (a_literal_and_its_opposite_exist(component))
				return true;
		}
		return false;
	}
}
