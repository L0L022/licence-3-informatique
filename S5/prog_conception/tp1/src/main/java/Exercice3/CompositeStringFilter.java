package Exercice3;

/**
 * Applique plusieurs filtres sur une chaine de caractères.
 */
public class CompositeStringFilter implements StringFilter {

	private StringFilter[] filters;

	/**
	 * Construit CompositeStringFilter.
	 *
	 * @param filters La liste des filtres à appliquer.
	 */
	public CompositeStringFilter(StringFilter[] filters) {
		this.filters = filters;
	}

	@Override
	public String filter(String string) {
		for (StringFilter filter : filters) {
			string = filter.filter(string);
		}
		return string;
	}

}
