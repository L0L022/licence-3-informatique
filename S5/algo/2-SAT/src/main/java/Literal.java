import java.util.Objects;

public class Literal {
	private String label;
	private boolean is_negative;
	
	public Literal(String label, boolean is_negative) {
		this.label = label;
		this.is_negative = is_negative;
	}
	
	public Literal(String label) {
		this.label = label;
		this.is_negative = false;
	}
	
	public Literal not() {
		return new Literal(label, !is_negative);
	}
	
	@Override
	public String toString() {
		if (is_negative) {
			return "¬" + label;
		} else {
			return label;
		}
	}
	
	@Override
	public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Literal)) return false;

        Literal lit = (Literal) o;

        return is_negative == lit.is_negative && label.equals(lit.label);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(is_negative, label);
	}
}
