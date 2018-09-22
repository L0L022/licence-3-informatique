package Exercice3;

/**
 * Convertit en majuscule les caractères.
 * 
 * @author loic
 *
 */
public class UpperCaseStringFilter implements StringFilter {

	@Override
	public String filter(String string) {
		return string.toUpperCase();
	}

}
