package Exercice3;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Server {

	private List<Client> clients;

	public Server() {
		System.out.println("Server init");
		clients = new ArrayList<>();
	}

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(8088);
			List<Thread> clientThreads = new ArrayList<>();

			while (true) {
				Thread thread = new Thread(new Client(serverSocket.accept(), this));
				thread.start();
				clientThreads.add(thread);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void subscribeToBroadcast(Client client) {
		clients.add(client);
	}

	public void unsubscribeToBroadcast(Client client) {
		clients.remove(client);
	}

	public void broadcastMessage(String message) {
		for (Client client : clients) {
			client.sendMessage(message);
		}
	}
}
