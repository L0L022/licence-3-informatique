
public class Clause {
	private Literal left, right;
	
	public Clause(Literal left, Literal right) {
		this.left = left;
		this.right = right;
	}
	
	public Literal left() {
		return left;
	}
	
	public Literal right() {
		return right;
	}
	
	@Override
	public String toString() {
		return left.toString() + " ∨ " + right.toString();
	}
}
