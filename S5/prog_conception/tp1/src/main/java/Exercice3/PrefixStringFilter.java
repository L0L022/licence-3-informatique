package Exercice3;

/**
 * Conserve les x premiers caractères.
 */
public class PrefixStringFilter implements StringFilter {

	private int length;

	/**
	 * Construit PrefixStringFilter.
	 *
	 * @param length Le nombre de caractères à conserver.
	 */
	public PrefixStringFilter(int length) {
		this.length = length;
	}

	@Override
	public String filter(String string) {
		return string.substring(0, length);
	}

}
