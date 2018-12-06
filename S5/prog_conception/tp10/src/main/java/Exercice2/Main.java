package Exercice2;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		try {
			ServerSocket serverSocket = new ServerSocket(8088);
			List<Thread> clientThreads = new ArrayList<>();

			while (true) {
				Thread thread = new Thread(new Client(serverSocket.accept()));
				thread.start();
				clientThreads.add(thread);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
