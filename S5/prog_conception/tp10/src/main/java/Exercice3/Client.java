package Exercice3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable {

	private Socket socket;
	private Server server;
	private BufferedReader reader;
	private PrintWriter writer;

	public Client(Socket socket, Server server) {
		this.socket = socket;
		this.server = server;
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(socket.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		server.subscribeToBroadcast(this);
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
				server.broadcastMessage(line);

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
		server.unsubscribeToBroadcast(this);
	}

	void sendMessage(String msg) {
		writer.println("{" + msg + "}");
	}

}
