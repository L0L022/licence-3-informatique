package Exercice4;


/**
 * Une variable.
 * 
 * @author loic
 *
 */
public class Variable implements Formula {

	String label;
	double value;
	
	Variable(String label, double value) {
		this.label = label;
		this.value = value;
	}
	
	@Override
	public String asString() {
		return label;
	}

	@Override
	public double asValue() {
		return value;
	}

}
