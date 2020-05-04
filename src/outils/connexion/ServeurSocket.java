package outils.connexion;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurSocket extends Thread {
	
	private Object leRecepteur;
	private ServerSocket serverSocket;
	
	public void run() {
		Socket socket;
		while(true){
			try {
				System.out.println("Le serveur attends");
				socket = serverSocket.accept();
				System.out.println("Un client s'est connecté");
				Connection connection = new Connection(socket, leRecepteur);
			} catch (IOException e) {
				System.out.println("erreur grave à l'acceptation du client : "+e);
				System.exit(0);
			}
		}
	}
	
	public ServeurSocket(Object leRecepteur, int port){
		
		this.leRecepteur = leRecepteur;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("erreur grave création socket serveur : "+e);
			System.exit(0);
		}
		
		start();
	}

}
