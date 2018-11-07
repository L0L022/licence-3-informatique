package dictionary;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		try {
			long totalTime = 0;
			long startTime = System.nanoTime();

			Dictionary dic = Dictionary.fromFile(args[0]);

			long stopTime = System.nanoTime();
			long timeElapsed = stopTime - startTime;
			totalTime += timeElapsed;

			System.out.println("Chargement du dico : " + timeElapsed / 1000000 + "ms");

			Scanner sc = new Scanner(System.in);

			long totalSimilarWords = 0;

			while (sc.hasNextLine()) {
				String word = sc.nextLine();

				if (word.isEmpty()) {
					break;
				}

				if (dic.contains(word)) {
					System.out.println(word + " est correct.");
				} else {

					System.out.print(word + " : ");
					startTime = System.nanoTime();

					List<String> words = dic.similarWords(word, 5);

					stopTime = System.nanoTime();
					timeElapsed = stopTime - startTime;
					totalSimilarWords += timeElapsed;

					System.out.println(words + "[" + timeElapsed / 1000000 + "ms]");
				}
			}

			System.out.println("Temps de recherche total : " + totalSimilarWords / 1000000 + "ms");
			totalTime += totalSimilarWords;
			System.out.println("Temps total : " + totalTime / 1000000 + "ms");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
