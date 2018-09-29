
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

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
		
		Map<Integer, Literal> literals = new HashMap<Integer, Literal>();
		Vector<Clause> clauses = new Vector<Clause>();
		
		int nb_literals = Integer.parseInt(b.readLine()); // exception
		
		char next_char = 'a';
		
		for (int i = 1; i <= nb_literals; ++i) {
			Literal literal = new Literal("" + next_char);
			literals.put(i, literal);
			literals.put(-i, literal.not());
			++next_char;
		}
		
		String line = "";
		while ((line = b.readLine()) != null) {
			int space_index = line.indexOf(" ");
			// TODO si = -1
			String left_str = line.substring(space_index), right_str = line.substring(space_index, line.length());
			int left_int = Integer.parseInt(left_str), right_int = Integer.parseInt(right_str);
			// TODO exception
			
			Literal left_lit = literals.get(left_int), right_lit = literals.get(right_int);
			clauses.add(new Clause(left_lit, right_lit));
		}
		b.close();
		
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
		// TODO
		return false;
	}
}
