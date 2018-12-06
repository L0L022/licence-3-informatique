package Exercice2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

	private Socket socket;
	private BufferedReader reader;
	private PrintWriter writer;

	public Client(Socket socket) {
		this.socket = socket;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		try {
			for (;;) {
				if (Thread.interrupted()) {
					break;
				}

				String line = reader.readLine();
				if (line == null) {
					break;
				}
				writer.println("[" + line + "]");

				try {
					Thread.sleep(1000); // endort le thread une seconde.
				} catch (InterruptedException e) {
					// Un autre thread demande à l'exécution
					// du thread courant de s'arrêter.
					break; // on sort de la boucle.
				}
			}

			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
