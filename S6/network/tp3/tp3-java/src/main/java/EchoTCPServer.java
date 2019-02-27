import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
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
			ServerSocket sSocket = new ServerSocket(port);
			
			System.out.println("server ok");
			
			Socket s = sSocket.accept();
			BufferedOutputStream bos = new BufferedOutputStream(s.getOutputStream());
			BufferedInputStream bis = new BufferedInputStream(s.getInputStream());

			System.out.println("client connected");

			String line;
			do {
				int length = bis.read(buffer);
				line = ">" + new String(buffer, 0, length);

				System.out.print(line);

				byte[] line_b = line.getBytes();
				bos.write(line_b);
				bos.flush();
			} while (!line.equals(">shutdown\n"));

			bos.close();
			bis.close();
			s.close();
			sSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
