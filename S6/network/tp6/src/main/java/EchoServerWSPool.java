import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;

class EchoServerWSPool {
	class Data {
		public  boolean alreadyHaveAThread;
		
		public Data() {
			this.alreadyHaveAThread = false;
		}
	}
	
    // Ajoute et enregistre un nouveau socket client
    private static void addClient(EchoServerWSPool server, Selector selector, ServerSocketChannel serverSocket) {
        try {
        	System.out.println("add");
        	
        	Data d = server.new Data();
        	
            SocketChannel clientSocket = serverSocket.accept();
            clientSocket.configureBlocking(false);
            clientSocket.register(selector, SelectionKey.OP_READ, d);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout du socket client.");
        }
    }
 
    // Lecture et écriture
    private static void readAndWrite(Selector selector, SelectionKey key, ByteBuffer buffer) {
        try {
        	System.out.println("raw");
            SocketChannel clientSocket = (SocketChannel) key.channel();
            clientSocket.read(buffer);
            String message = new String(buffer.array());
            System.out.print("Recu : " + message);
            buffer.flip();
            clientSocket.write(ByteBuffer.wrap(("> " + message).getBytes()));
            buffer.clear();
            if(buffer.remaining() == 0)
            	clientSocket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.err.println("Problème de lecture.");
        }
    }

	/* Démarrage et délégation des connexions entrantes dans le cas d'un pool dynamique */
	public void demarrer(int port) {

		System.out.println("Lancement du serveur sur le port " + port);

		try {
			Executor executor = Executors.newWorkStealingPool();

			Selector selector = Selector.open();

			ServerSocketChannel serverSocket = ServerSocketChannel.open();
			serverSocket.bind(new InetSocketAddress("localhost", port));
			serverSocket.socket().setReuseAddress(true);
			serverSocket.configureBlocking(false);
			serverSocket.register(selector, SelectionKey.OP_ACCEPT);

			while (true) {
				selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if(key.isAcceptable()) {
                        System.out.println("accept");
						addClient(this, selector, serverSocket);
                    }
                    if(key.isReadable()) {
                    	Data d = (Data)key.attachment();
                    	if (d.alreadyHaveAThread)
                    		continue;
                    	d.alreadyHaveAThread = true;
                    	System.out.println("lit");
                        executor.execute(new rAndWHandler(selector, key));
                    }
                    iterator.remove();
                }
			}
		} 
		catch (IOException ex) {
			System.out.println("Arrêt anormal du serveur.");
			return;
		}
	}

	public static void main(String[] args) {

		int argc = args.length;
		EchoServerWSPool serveur;
		
		/* Traitement des arguments */
		if (argc == 1) {
			try {
				serveur = new EchoServerWSPool();
				
				serveur.demarrer(Integer.parseInt(args[0]));
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			System.out.println("Usage: java EchoServerStealingPool @port");
		}
		return;
	}

	class rAndWHandler implements Runnable {

		private ByteBuffer buffer;
		private Selector selector;
		private SelectionKey key;

		public rAndWHandler(Selector selector, SelectionKey key){
			this.key = key;
			this.buffer = ByteBuffer.allocate(128);
			this.selector = selector;
			this.key = key;
		}

		public void run() {
			System.out.println("rAndW");
            readAndWrite(selector, key, buffer);
		}
	}
}
