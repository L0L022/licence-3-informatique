package dictionary;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		try {
			Dictionary dic = Dictionary.fromFile(args[0]);

			Scanner sc = new Scanner(System.in);

			while (true) {
				String word = sc.nextLine();
				if (dic.contains(word)) {
					System.out.println(word + " est correct.");
				} else {
					List<String> words = dic.similarWords(word, 5);
					System.out.println(words);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
