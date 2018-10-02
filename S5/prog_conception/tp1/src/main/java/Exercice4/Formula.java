package Exercice4;

/**
 * Une formule qui peut être évaluée et affichée.
 *
 * @author Loïc Escales
 *
 */
public interface Formula {

	/**
	 * Renvoie la représentation textuelle de la formule.
	 *
	 * @return La représentation de la formule.
	 */
	String asString();

	/**
	 * Évalue le résultat de la formule.
	 *
	 * @return Le résultat de la formule.
	 */
	double asValue();
}
