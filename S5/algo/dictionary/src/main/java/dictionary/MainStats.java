package dictionary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MainStats {

	static int COUNT = 20;

	public static void main(String[] args) throws FileNotFoundException {

		double total = 0.0;
		for (int i = 0; i < COUNT; ++i) {
			total += mesureDicFromFile();
		}
		total /= COUNT;
		System.out.println("Chargement du dico en moyenne : " + total / 1000000 + "ms");

		Dictionary dic = Dictionary.fromFile("src/test/resources/dico.txt");

		double total_mesureSimilarWords = mesureSimilarWords(dic);
		System.out.println("Calcul des mots en moyenne : " + total_mesureSimilarWords / 1000000 + "ms");
		System.out.println("Total : " + (total + total_mesureSimilarWords) / 1000000 + "ms");
	}

	private static long mesureDicFromFile() {
		long startTime = System.nanoTime();

		try {
			Dictionary.fromFile("src/test/resources/dico.txt");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		long stopTime = System.nanoTime();
		return stopTime - startTime;
	}

	private static double mesureSimilarWords(Dictionary dic) {
		List<String> words = null;
		try {
			words = Files.readAllLines(Paths.get("src/test/resources/fautes.txt"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		double total = 0.0;

		for (int i = 0; i < COUNT; ++i) {
			double sub_total = 0.0;
			for (String word : words) {
				sub_total += mesureSimilarWords(dic, word);
			}
			total += sub_total;
		}

		return total / COUNT;
	}

	private static long mesureSimilarWords(Dictionary dic, String word) {
		long startTime = System.nanoTime();

		dic.similarWords(word, 5);

		long stopTime = System.nanoTime();
		return stopTime - startTime;
	}
}
