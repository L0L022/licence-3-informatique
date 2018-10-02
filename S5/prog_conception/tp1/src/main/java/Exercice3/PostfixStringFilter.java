package Exercice3;

/**
 * Conserve les x derniers caractères.
 */
public class PostfixStringFilter implements StringFilter {

	private int length;

	/**
	 * Construit PostfixStringFilter.
	 *
	 * @param length Le nombre de caractères à conserver.
	 */
	public PostfixStringFilter(int length) {
		this.length = length;
	}

	@Override
	public String filter(String string) {
		return string.substring(string.length() - length, string.length());
	}

}
