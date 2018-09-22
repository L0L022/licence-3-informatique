package Exercice3;

/**
 * Applique les filtres à string.
 * 
 * @author loic
 *
 */
public class CompositeStringFilter implements StringFilter {

	private StringFilter[] filters;
	
	CompositeStringFilter(StringFilter[] filters) {
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
