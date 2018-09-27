
public class Literal {
//	String label;
//	Boolean is_negative;
//	
//	Literal(String label, Boolean is_negative) {
//		this.label = label;
//		this.is_negative = is_negative;
//	}
//	
//	String asString() {
//		if (is_negative) {
//			return "¬" + label;
//		} else {
//			return label;
//		}
//	}
	
	int number;
	
	Literal(int number) {
		this.number = number;
	}
	
	String asString() {
		if (number < 0) {
			return "¬" + number;
		} else {
			return "" + number;
		}
	}
	
	Literal not() {
		return new Literal(-number);
	}
}
