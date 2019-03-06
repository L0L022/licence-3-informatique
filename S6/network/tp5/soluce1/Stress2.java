import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

// Va devenir optionnel

class Stress2 {
	public static void main(String[] args) {
		try {
		int nb_co = Integer.parseInt(args[0]);
		
		List<Socket> ss = new ArrayList<>(nb_co);
		List<OutputStream> oss = new ArrayList<>(nb_co);
		
		for (int i = 0; i < nb_co; ++i) {
				Socket s = new Socket("localhost", 12345);
				OutputStream os = s.getOutputStream();
				InputStream is = s.getInputStream();
				String str = new String("java client stress1 n°" + i);
				
				os.write(str.getBytes());
				os.flush();
				System.out.println("send: " + str);
			
				os.write(str.getBytes());
				os.flush();
				System.out.println("send: " + str);
				
				oss.add(os);
				ss.add(s);
		}
		
		for (int i = 0; i < nb_co; ++i) {
			oss.get(i).close();
			ss.get(i).close();
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}