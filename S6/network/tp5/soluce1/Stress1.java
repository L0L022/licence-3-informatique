import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class Stress1 {
	public static void main(String[] args) {
		try {
		int nb_co = Integer.parseInt(args[0]);
		
		List<Socket> ss = new ArrayList<>(nb_co);
		List<OutputStream> oss = new ArrayList<>(nb_co);
		
		for (int i = 0; i < nb_co; ++i) {
				Socket s = new Socket("localhost", 12345);
				String str = new String("java client stress1 n°" + i);
				OutputStream os = s.getOutputStream();
				os.write(str.getBytes());
				os.flush();
				oss.add(os);
				ss.add(s);
				os.close();
				s.close();
				System.out.println("send: "+str);
		}
		
//		for (int i = 0; i < nb_co; ++i) {
//			oss.get(i).close();
//			ss.get(i).close();
//		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}