package Exercice3;

/**
 * Permet d'appliquer un filtre sur une chaine de caractères passée en
 * paramètre.
 */
public interface StringFilter {
	/**
	 * Renvoie le résultat de l'application du filtre sur une chaine de caractères.
	 *
	 * @param string La chaine de caractères qui est filtrée.
	 * @return Le résultat du filtre
	 */
	String filter(String string);
}
