package Exercice1;

/**
 * Permet de dessiner des formes.
 *
 * @author Loïc Escales
 *
 */
public interface Painter {
	/**
	 * Dessine un rectangle.
	 *
	 * @param x      Axe x du point en haut à gauche du rectangle.
	 * @param y      Axe y du point en haut à gauche du rectangle.
	 * @param width  Largeur du rectangle.
	 * @param height Hauteur du rectangle.
	 */
	void drawRectangle(int x, int y, int width, int height);

	/**
	 * Dessine un cercle.
	 *
	 * @param x      Centre sur l'axe x du cercle.
	 * @param y      Centre sur l'axe y du cercle.
	 * @param radius Le rayon du cercle.
	 */
	void drawCircle(int x, int y, int radius);
}
