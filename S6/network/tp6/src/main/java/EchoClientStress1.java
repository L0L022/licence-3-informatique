import java.io.*;
import java.net.*;
import java.util.*;

public class EchoClientStress1 {

  public static long main(String[] args) throws IOException {
    Socket echoSocket; // la socket client
    String ip; // adresse IPv4 du serveur en notation pointée
    int port; // port TCP serveur
    BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    PrintWriter out;
    BufferedReader in;

    boolean closing = false;

    //Cas ou on a précisé l'option "-c" dans Stress1
    if (args[3].equals("-c")){
      closing = true;
    }

    ip = args[0];
    port = Integer.parseInt(args[1]);
    int numero = Integer.parseInt(args[2]);

    if (port > 65535) {
      System.err.println("Port hors limite");
      System.exit(3);
    }

    /* Connexion */
    System.out.println("\n---------------------------------\n");
    System.out.println("Essai de connexion à  " + ip + " sur le port " + port + "\n");
    try {
      echoSocket = new Socket(ip, port);
      System.err.println("le n° de la socket est : " + echoSocket);
      /* Initialisation d'agréables flux d'entrée/sortie */
      out = new PrintWriter(echoSocket.getOutputStream(), true);
      in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
    } catch (UnknownHostException e) {
      System.err.println("Connexion: hôte inconnu : " + ip);
      e.printStackTrace();
      return 0;
    }

    /* Session */
    String lu;
    String tampon = "java client Stress1 n°" + numero;

    long startTime = System.nanoTime();

    /* Envoi des données */
    out.println(tampon);

    /* réception des données */
    lu = in.readLine();

    long endTime = System.nanoTime();

    //Temps de réponse
    long totalTime = (endTime-startTime);
    System.out.println("\nTemps de réponse de la part du serveur: " + totalTime + " ns.\n");

    System.out.println("reçu: " + lu);

    //Cas ou on veut fermer les clients à chaque fois
    if(closing){

      try {
        /* On ferme tout */
        in.close();
        out.close();
        stdin.close();
        echoSocket.close();

        System.err.println("Fin de la session.");
      }

      catch (IOException e) {
        System.err.println("Erreur E/S socket");
        e.printStackTrace();
        System.exit(8);
      }
    }

    return totalTime;
  }
}