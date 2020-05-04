package outils.connexion;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

public class ClientSocket {
	
	private boolean connexionOk;
	
	public ClientSocket(String ip, int port, Object leRecepteur){
		connexionOk = false;
		Socket socket;
		try {
			socket = new Socket(ip, port);
			System.out.println("Connexion au serveur réussie");
			connexionOk = true;
			Connection connection = new Connection (socket, leRecepteur);
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "Serveur non disponible");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Problème d'entrée/sortie");
		}
	}

	/**
	 * @return the connexionOk
	 */
	public boolean isConnexionOk() {
		return connexionOk;
	}
	
	

}
