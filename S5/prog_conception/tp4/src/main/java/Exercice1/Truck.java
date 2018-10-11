package Exercice1;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Dessine un camion.
 *
 * @author Loïc Escales
 *
 */
class Truck extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Construit Truck.
	 */
	public Truck() {
		setPreferredSize(new Dimension(130, 110));
	}

	private void paint(Painter painter) {
		painter.drawRectangle(10, 10, 70, 70);
		painter.drawRectangle(80, 45, 40, 35);
		painter.drawCircle(40, 80, 10);
		painter.drawCircle(100, 80, 10);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Painter p = new EnglishPainterAdapter(g);// new MarseillePainter(g);
		paint(p);
	}
}