package Exercice3;

/**
 * Conserve les length premiers caractères de string.
 * 
 * @author loic
 *
 */
public class PrefixStringFilter implements StringFilter {

	private int length;
	
	PrefixStringFilter(int length) {
		this.length = length;
	}
	
	@Override
	public String filter(String string) {
		return string.substring(0, length);
	}

}
