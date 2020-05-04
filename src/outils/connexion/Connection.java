package outils.connexion;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;
import controleur.Controle;

public class Connection extends Thread {
	
	private Object leRecepteur;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public synchronized void envoi(Object unObjet){
		try {
			this.out.reset() ;
			out.writeObject(unObjet);
			out.flush();
		} catch (IOException e) {
			System.out.println("Erreur sur l'objet out : "+e);
		}
	}
	
	public void run(){
		boolean inOk = true;
		Object reception;
		while(inOk){
			try {
				reception = in.readObject();
				((Controle)this.leRecepteur).receptionInfo(this,reception);
			} catch (ClassNotFoundException e) {
				System.out.println("Classe Object non trouvée : "+e);
				System.exit(0);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "l'ordinateur distant s'est déconnecté");
				inOk = false;
				((Controle)this.leRecepteur).deconnection(this);
				try {
					in.close();
				} catch (IOException e1) {
					System.out.println("erreur lors de la fermeture du canal : "+e1);
				}
			}
		}
	}
	
	public Connection(Socket socket, Object leRecepteur){
		this.leRecepteur = leRecepteur;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			System.out.println("erreur grave lors de la création du canal de sortie : "+e);
			System.exit(0);
		}
		
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			System.out.println("erreur grave lors de la création du canal d'entrée : "+e);
			System.exit(0);
		}
		
		start();
		((controleur.Controle)this.leRecepteur).setConnection(this);
	}
}
