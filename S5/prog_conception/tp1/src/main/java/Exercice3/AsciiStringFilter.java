package Exercice3;

/**
 * Conserve les caractères appartenant à l'encodage ASCII.
 */
public class AsciiStringFilter implements StringFilter {

	@Override
	public String filter(String string) {
		char[] ascii_chars = new char[string.length()];
		int length = 0;
		for (char c : string.toCharArray()) {
			if (c < 128) {
				ascii_chars[length++] = c;
			}
		}
		return new String(ascii_chars, 0, length);
	}

}
