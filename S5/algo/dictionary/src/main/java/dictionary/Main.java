package dictionary;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		try {
			long startTime = System.nanoTime();

			Dictionary dic = Dictionary.fromFile(args[0]);

			long stopTime = System.nanoTime();
			long timeElapsed = stopTime - startTime;

			System.out.println("Chargement du dico : " + timeElapsed / 1000000 + "ms");

			Scanner sc = new Scanner(System.in);

			while (true) {
				String word = sc.nextLine();
				if (dic.contains(word)) {
					System.out.println(word + " est correct.");
				} else {

					startTime = System.nanoTime();

					List<String> words = dic.similarWords(word, 5);

					stopTime = System.nanoTime();
					timeElapsed = stopTime - startTime;

					System.out.println(words + "[" + timeElapsed / 1000000 + "ms]");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
