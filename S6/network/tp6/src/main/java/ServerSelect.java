import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SelectionKey;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.util.*;

public class ServerSelect {
    
    // Ajoute et enregistre un nouveau socket client
    private static void addClient(Selector selector, ServerSocketChannel serverSocket) {
        try {
            SocketChannel clientSocket = serverSocket.accept();
            clientSocket.configureBlocking(false);
            clientSocket.register(selector, SelectionKey.OP_READ);  
        }
        catch(IOException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de l'ajout du socket client.");
        }
    }
 
    // Lecture
    private static void read(Selector selector, SelectionKey key, ByteBuffer buffer) {
        try {
            SocketChannel clientSocket = (SocketChannel) key.channel();
            clientSocket.read(buffer);
            String message = new String(buffer.array());
            System.out.print("Recu : " + message);
            clientSocket.register(selector, SelectionKey.OP_WRITE);
        }
        catch(IOException e) {
            e.printStackTrace();
            System.err.println("Problème de lecture.");
        }
    }

    // Ecriture
    private static void write(Selector selector, SelectionKey key, ByteBuffer buffer) {
        try {
            SocketChannel clientSocket = (SocketChannel) key.channel();
            buffer.flip();
            clientSocket.write(buffer);
            buffer.clear();
            clientSocket.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.err.println("Problème d'écriture.");
        }        
    }
 
    public static void main(String[] args) {
        try {
            // Vérification des arguments
            if(args.length != 1) {
                System.err.println("Usage : java TCP.SelectServer @numPort");
                return;
            }
            int port = Integer.parseInt(args[0]);
            // Ajout et enregistrement du socket serveur
            Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", port));
            System.out.println("Serveur créé sur le port " + port);
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer buffer = ByteBuffer.allocate(128);
            
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectedKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if(key.isAcceptable()) {
                        addClient(selector, serverSocket);
                    }
                    if(key.isReadable()) {
                        read(selector, key, buffer);
                    }
                    if(key.isWritable()) {
                        write(selector, key, buffer);
                    }
                    iterator.remove();
                }
            }
        }
        catch(IOException e) {
            e.printStackTrace();
            System.err.println("Problème au niveau du socket serveur.");
        }
    }
}
