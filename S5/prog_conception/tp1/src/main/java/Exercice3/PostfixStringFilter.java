package Exercice3;

/**
 * Conserve les length derniers caractères de string.
 * 
 * @author loic
 *
 */
public class PostfixStringFilter implements StringFilter {

	private int length;
	
	PostfixStringFilter(int length) {
		this.length = length;
	}
	
	@Override
	public String filter(String string) {
		return string.substring(string.length() - length, string.length());
	}

}
