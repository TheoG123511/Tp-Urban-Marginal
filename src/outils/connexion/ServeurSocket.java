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
				System.out.println("Un client s'est connect�");
				Connection connection = new Connection(socket, leRecepteur);
			} catch (IOException e) {
				System.out.println("erreur grave � l'acceptation du client : "+e);
				System.exit(0);
			}
		}
	}
	
	public ServeurSocket(Object leRecepteur, int port){
		
		this.leRecepteur = leRecepteur;
		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("erreur grave cr�ation socket serveur : "+e);
			System.exit(0);
		}
		
		start();
	}

}
