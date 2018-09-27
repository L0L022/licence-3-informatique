
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TwoSAT {
	Clause[] clauses;
	int nb_literals;
	
	TwoSAT(Clause[] clauses, int nb_literals) {
		this.clauses = clauses;
		this.nb_literals = nb_literals;
	}
	
	static TwoSAT fromFile(String fileName) throws IOException {
		File f = new File(fileName);
		BufferedReader b = new BufferedReader(new FileReader(f));
		
		Map<Integer, Literal> literals = new HashMap<Integer, Literal>();
		Vector<Clause> clauses = new Vector<Clause>();
		
		int nb_literals = Integer.parseInt(b.readLine()); // exception
		
		String line = "";
		while ((line = b.readLine()) != null) {
			int space_index = line.indexOf(" ");
			// TODO si = -1
			String left_str = line.substring(space_index), right_str = line.substring(space_index, line.length());
			int left_int = Integer.parseInt(left_str), right_int = Integer.parseInt(right_str);
			// TODO exception
			
			if (!literals.containsKey(left_int)) {
				literals.put(left_int, new Literal(left_int));
			}
			if (!literals.containsKey(right_int)) {
				literals.put(right_int, new Literal(right_int));
			}
			
			Literal left_lit = literals.get(left_int), right_lit = literals.get(right_int);
			clauses.add(new Clause(left_lit, right_lit));
		}
		b.close();
		
		return new TwoSAT(clauses.toArray(new Clause[clauses.size()]), nb_literals);
	}
	
	String asString() {
		StringBuilder sb = new StringBuilder("{");
		int count = 0;
		for (Clause clause : clauses) {
			if (count > 0) {
				sb.append(", ");
			}
			sb.append(clause.asString());
		}
		sb.append("}");
		return sb.toString();
	}
	
	Boolean is_satisfiable() {
		// TODO
		return false;
	}
}
