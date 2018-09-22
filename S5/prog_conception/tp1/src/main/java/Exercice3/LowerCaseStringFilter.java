package Exercice3;

/**
 * Convertit en minuscule les caractères.
 * 
 * @author loic
 *
 */
public class LowerCaseStringFilter implements StringFilter {

	@Override
	public String filter(String string) {
		return string.toLowerCase();
	}

}
