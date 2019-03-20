import java.io.IOException;

public class Stress1 implements Runnable{

	private String adr;
	private String port;
	private String nbThreads;
	private String closing;

	// Constructeur
	public Stress1(String adr, String port, String nbThreads, String closing){
		this.adr = adr;
		this.port = port;
		this.nbThreads = nbThreads;
		this.closing = closing;
	}

	// Pour l'appel par Stress1Threads
  public void run() {
    String[] arguments = {adr, port, nbThreads, closing};
    main(arguments);
  }

	public static void main(String[] args) {

		String closing = "";

		// Vérification des arguments
		//(qu'on l'appelle directement ou par le biais de Stress1Threads)
		int argc = args.length;
		if (argc != 3) {
			if(args.length != 4 || (!args[3].equals("-c") && !args[3].equals(""))) {
				System.out.println("Usage: java Stress1 @server @port @nbClients [-c : closing]");
				return;
			}
			if(args[3].equals("-c")){
				closing = "-c";
			}
		}

		int nbClients = Integer.parseInt(args[2]);
		String adresseServeur = args[0];
		String portServeur = args[1];

		int i;
		long totalTime = 0;

		// Création des threads client
		for(i = 0; i < nbClients; i++) {
			try {
				String[] argsClient = {adresseServeur, portServeur, Integer.toString(i), closing};
				totalTime += EchoClientStress1.main(argsClient);
			}
			catch (IOException e) {
				e.printStackTrace();
				System.err.println("Problème création client.");
			}
		}
		System.out.println("\n--------------------------------------------------------------------");
		System.out.println("\n/////////////////////// Temps total : " + totalTime / 1000000 + " ms ///////////////////////\n");
	}
}