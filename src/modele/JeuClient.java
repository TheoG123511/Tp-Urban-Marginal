package modele;

import javax.swing.JLabel;
import javax.swing.JPanel;
import controleur.Controle;
import outils.connexion.Connection;

public class JeuClient extends Jeu {
	
	private Connection connection;
	public boolean fait = false;
	
	public JeuClient(Controle controle){
		this.controle = controle;
	}
	
	@Override
	public void setConnection(Connection connection) {
		// TODO Auto-generated method stub
		this.connection = connection;
	}

	@Override
	public void reception(Connection connection, Object info) {
		if(info instanceof JPanel){
			if(fait = false)
			{
			this.controle.evenementModele(this, "ajout panel murs", info);
			fait = true;
			}
			else{
			this.controle.evenementModele(this, "ajout panel consommables", info);
			}
		}
		if(info instanceof JLabel){
			this.controle.evenementModele(this, "ajout nouveau consommable", info);
		}
		if(info instanceof Label){
			this.controle.evenementModele(this, "ajout joueur", info);
		}
		if(info instanceof String){
			this.controle.evenementModele(this, "remplace chat", info);
		}
		if(info instanceof Integer){
			this.controle.evenementModele(this, "son", info);
		}
	}

	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub
		System.exit(0);
	}
	
	public void envoi(Object info){
		super.envoi(connection, info);
	}

}
