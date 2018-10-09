import java.io.IOException;

/**
 * Indique si un problème 2-SAT est satisfaisable ou non. Le problème est stocké
 * dans un fichier dont le chemin est passé en paramètre.
 *
 * @author Loïc Escales
 *
 */
public class Test2SAT {

	public static void main(String[] args) {
		if (args.length == 0 || args.length > 1) {
			System.out.println("Pas assez d'arguments.");
			return;
		}

		TwoSAT twoSAT = null;

		try {
			twoSAT = TwoSAT.fromFile(args[0]);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			return;
		}

		System.out.print(twoSAT.toString());
		if (twoSAT.isSatisfiable()) {
			System.out.println(" est satisfaisable.");
		} else {
			System.out.println(" n'est pas satisfaisable.");
		}
	}

}
