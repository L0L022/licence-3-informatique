import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class EchoUDPServer {
	public static void main(String[] args) {
		try {
			int port;

			if (args.length == 1) {
				port = Integer.parseInt(args[0]);
			} else {
				System.err.println("port manquant");
				return;
			}

			byte[] buffer = new byte[100];
			DatagramSocket ds = new DatagramSocket(port);

			System.out.println("server ok");

			String line;
			do {
				DatagramPacket received_packet = new DatagramPacket(buffer, buffer.length);
				ds.receive(received_packet);
				line = ">" + new String(received_packet.getData(), 0, received_packet.getLength());

				System.out.print(line);

				byte[] line_b = line.getBytes();
				ds.send(new DatagramPacket(line_b, line_b.length, received_packet.getAddress(),
						received_packet.getPort()));
			} while (!line.equals(">shutdown\n"));

			ds.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
