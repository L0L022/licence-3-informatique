package Exercice1.curve;

import java.io.IOException;
import java.io.Writer;

/**
 * Permet de tracer la courbe d'une fonction.
 *
 * @author Loïc Escales
 *
 */
public class Curve {
	private Function function;
	private double startValue, endValue, step;

	/**
	 * Construit Curve.
	 *
	 * @param function   La fonction à tracer.
	 * @param startValue La première valeur de l'antécédent.
	 * @param endValue   La dernière valeur de l'antécédent.
	 * @param step       Le pas à ajouter à la première valeur. Doit être
	 *                   strictement supprérieure à zéro.
	 */
	public Curve(Function function, double startValue, double endValue, double step) {
		assert startValue < endValue;
		assert step > 0;

		this.function = function;
		this.startValue = startValue;
		this.endValue = endValue;
		this.step = step;
	}

	/**
	 * Trace la courbe, c'est à dire : affiche l'antécédent suivi de son image sur
	 * chaque ligne.
	 *
	 * @param writer Là où tracer la courbe.
	 * @throws IOException
	 */
	public void writePoints(Writer writer) throws IOException {
		for (double i = startValue; i < endValue; i += step) {
			writer.write(i + " " + function.calculate(i) + "\n");
		}
	}
}
