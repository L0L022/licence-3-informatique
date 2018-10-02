package Exercice3;

/**
 * Convertit les caractères en minuscule.
 */
public class LowerCaseStringFilter implements StringFilter {

	@Override
	public String filter(String string) {
		return string.toLowerCase();
	}

}
