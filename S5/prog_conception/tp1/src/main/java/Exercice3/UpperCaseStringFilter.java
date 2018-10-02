package Exercice3;

/**
 * Convertit les caractères en majuscule.
 */
public class UpperCaseStringFilter implements StringFilter {

	@Override
	public String filter(String string) {
		return string.toUpperCase();
	}

}
