import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class EchoUDPClient {
	public static void main(String[] args) {
		try {
			InetAddress addr;
			int port;

			if (args.length == 2) {
				addr = InetAddress.getByName(args[0]);
				port = Integer.parseInt(args[1]);
			} else {
				System.err.println("serveur et/ou port manquant");
				return;
			}

			DatagramSocket ds = new DatagramSocket();
			Scanner input = new Scanner(System.in);

			System.out.println("client ok");

			do {
				byte[] line = (input.nextLine() + "\n").getBytes();
				ds.send(new DatagramPacket(line, line.length, addr, port));
			} while (input.hasNextLine());

			input.close();
			ds.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
