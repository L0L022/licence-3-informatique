
public class Clause {
	Literal left, right;
	
	Clause(Literal left, Literal right) {
		this.left = left;
		this.right = right;
	}
	
	Literal left() {
		return left;
	}
	
	Literal right() {
		return right;
	}
	
	String asString() {
		return left.asString() + " ∨ " + right.asString();
	}
}
