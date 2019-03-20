/* echo / serveur basique
   Master Informatique 2012 -- Université Aix-Marseille
   Bilel Derbel, Emmanuel Godard
*/

import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.*;
import java.io.*;

class EchoServer {

	class Data {
		String line;
		ByteBuffer buffer;
		long compteur;
		
		Data() {
			line = "";
			buffer = ByteBuffer.allocate(128);
			compteur = 0;
		}
	}
	
  /* Démarrage et délégation des connexions entrantes */
  public void demarrer(int port) {
	  try {
		ServerSocketChannel ssc= ServerSocketChannel.open();
		ssc.configureBlocking(false);
		ssc.setOption(StandardSocketOptions.SO_REUSEADDR, true);
		ssc.bind(new InetSocketAddress(InetAddress.getLoopbackAddress(), port));
  
		Selector selector = Selector.open();
		ssc.register(selector, SelectionKey.OP_ACCEPT);

		System.out.println("Lancement du serveur sur le port " + port);
		
		while (true) {
			selector.select();
			/*
			Set<SelectionKey> crunchifyKeys = selector.selectedKeys();
			Iterator<SelectionKey> crunchifyIterator = crunchifyKeys.iterator();
 
			while (crunchifyIterator.hasNext()) {
				SelectionKey sk = crunchifyIterator.next();
			*/
			for (SelectionKey sk : selector.selectedKeys()) {
				if (sk.isAcceptable()) {
					Data d = new Data();
					
					SocketChannel sc = ssc.accept();
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE, d);
					
					Socket s = sc.socket();
					
					sc.write(ByteBuffer.wrap(("Bonjour " + s.getInetAddress() + "! (vous utilisez le port " + s.getPort() + ")").getBytes()));
				}
				
				if (sk.isReadable()) {
					Data d = (Data)sk.attachment();
					SocketChannel sc = (SocketChannel)sk.channel();
					Socket s = sc.socket();
					sc.read(d.buffer);
					d.buffer.flip();
					//System.out.println(d.buffer);
					while (d.buffer.hasRemaining()) {
						byte c_ = d.buffer.get();
						char c = (char)c_;
						//System.out.println("get: "+c);
						if (c == '\n') {
							d.compteur++;
							d.line += '\n';
				            /* log */

				            System.err.print("[" + s.getInetAddress() + ":" + s.getPort() + "]: " + d.compteur + ":" + d.line);
				            
				            /* echo vers le client */
				            ((SocketChannel)sk.channel()).write(ByteBuffer.wrap(("> " + d.line).getBytes()));
				            
				            d.line = "";
							
						} else {
							d.line += c;
						}
					}
					d.buffer.clear();
				}
			}
			selector.selectedKeys().clear();
		}
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		System.out.println("Arrêt anormal du serveur.");
	}
  }

  public static void main(String[] args) {
    int argc = args.length;
    EchoServer serveur;

    /* Traitement des arguments */
    if (argc == 1) {
      try {
        serveur = new EchoServer();
        serveur.demarrer(Integer.parseInt(args[0]));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("Usage: java EchoServer port");
    }
    return;
  }

  /*
     echo des messages reçus (le tout via la socket).
     NB classe Runnable : le code exécuté est défini dans la
     méthode run().
  */
  class Handler implements Runnable {

    Socket socket;
    PrintWriter out;
    BufferedReader in;
    InetAddress hote;
    int port;

    Handler(Socket socket) throws IOException {
      this.socket = socket;
      out = new PrintWriter(socket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      hote = socket.getInetAddress();
      port = socket.getPort();
    }

    public void run() {
      String tampon;
      long compteur = 0;

      try {
        /* envoi du message d'accueil */
        out.println("Bonjour " + hote + "! (vous utilisez le port " + port + ")");

        do {
          /* Faire echo et logguer */
          tampon = in.readLine();
          if (tampon != null) {
            compteur++;
            /* log */
            System.err.println("[" + hote + ":" + port + "]: " + compteur + ":" + tampon);
            /* echo vers le client */
            out.println("> " + tampon);
          } else {
            break;
          }
        } while (true);

        /* le correspondant a quitté */
        in.close();
        out.println("Au revoir...");
        out.close();
        socket.close();

        System.err.println("[" + hote + ":" + port + "]: Terminé...");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
}
