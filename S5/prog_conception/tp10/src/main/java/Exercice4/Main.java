package Exercice4;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		Client client = new Client("localhost", 8088);
		new Thread(client).start();
		Scanner scanner = new Scanner(System.in);
		for (;;) {
			String line = scanner.nextLine();
			if (line.equals("STOP")) {
				break;
			}
			client.sendMessage(line);
		}
		client.close();
		scanner.close();
	}

}
