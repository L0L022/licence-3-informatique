
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TwoSAT {
	private Clause[] clauses;
	
	public TwoSAT(Clause[] clauses) {
		this.clauses = clauses;
	}
	
	public static TwoSAT fromFile(String fileName) throws IOException {
		File f = new File(fileName);
		BufferedReader b = new BufferedReader(new FileReader(f));
		Scanner s = new Scanner(b);
		
		Map<Integer, Literal> literals = new HashMap<Integer, Literal>();
		Vector<Clause> clauses = new Vector<Clause>();
		
		//if (!s.hasNextInt()) exception
		
		int nb_literals = s.nextInt();
				
		char next_char = 'a';
		
		for (int i = 1; i <= nb_literals; ++i) {
			Literal literal = new Literal("" + next_char);
			literals.put(i, literal);
			literals.put(-i, literal.not());
			++next_char;
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
	
	public boolean is_satisfiable() {
		ImplicationGraph graph = new ImplicationGraph(clauses);
		List<DirectedGraph<Literal>> components = graph.stronglyConnectedComponents();		
		return literal_and_his_opposed_exists(components);
	}
	
	private boolean literal_and_his_opposed_exists(List<DirectedGraph<Literal>> components) {
		for (DirectedGraph<Literal> component : components) {
			Set<Literal> literals = new HashSet<>();
			for (Literal literal : component.vertices()) {
				literals.add(literal.abs());
			}
			System.out.println(component.vertices());
			System.out.println(literals);
			if (literals.size() != component.vertices().size()) return false;
		}
		
		return true;
	}
}
