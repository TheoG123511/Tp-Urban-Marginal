
package controleur;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modele.Label;
import modele.Jeu;
import modele.JeuClient;
import modele.JeuServeur;
import outils.connexion.ClientSocket;
import outils.connexion.Connection;
import outils.connexion.ServeurSocket;
import vue.Arene;
import vue.ChoixJoueur;
import vue.EntreeJeu;

public class Controle implements Global{
	
	private EntreeJeu frmEntreeJeu;
	private Jeu leJeu;
	private Arene frmArene;
	private ChoixJoueur frmChoixJoueur;
	private Connection connection;
	
	/**
	 * Suppression de l'ancien consommable et ajout du nouveau
	 * @param newConsommable
	 */
	public void demandeSupression(JLabel newConsommable ){
		frmArene.supprAjoutConsommable(newConsommable);
		((JeuServeur)this.leJeu).envoi(newConsommable);
	}
	
	private void evenementArene(Object info) {
		// TODO Auto-generated method stub
		((JeuClient)leJeu).envoi(info);
	}
	
	public void evenementModele(Object unJeu, String ordre, Object info ){
		if(unJeu instanceof JeuServeur){
			evenementJeuServeur(ordre, info);
		}
		if(unJeu instanceof JeuClient){
			evenementJeuClient(ordre,info);
		}
	}
	
	private void evenementJeuClient(String ordre, Object info) {
		if(ordre == "ajout panel murs"){
			this.frmArene.ajoutPanelMurs((JPanel)info);
		}
		if(ordre == "ajout joueur"){
			this.frmArene.ajoutModifJoueur(((Label)info).getNumLabel(), ((Label)info).getjLabel());
		}
		if(ordre == "ajout panel consommables"){
			this.frmArene.ajoutPanelConsommables((JPanel)info);
		}
		if(ordre == "ajout nouveau consommable"){
			this.frmArene.supprAjoutConsommable((JLabel)info);
		}
		if(ordre == "remplace chat"){
			this.frmArene.remplaceChat(((String)info));
		}
		if(ordre == "son"){
			this.frmArene.joueSon((Integer) info);
		}
	}

	private void evenementJeuServeur(String ordre, Object info) {
		// TODO Auto-generated method stub
		if(ordre == "ajout mur"){
			this.frmArene.ajoutMur((JLabel)info);
		}
		if(ordre == "ajout consommable"){
			this.frmArene.ajoutConsommable((JLabel)info);
		}
		if(ordre == "envoi panel mur"){
			((JeuServeur)this.leJeu).envoi((Connection)info, this.frmArene.getJpnMurs() );
		}
		if(ordre == "envoi panel consommable"){
			((JeuServeur)this.leJeu).envoi((Connection)info, this.frmArene.getJpnConso() );
		}
		if(ordre == "ajout joueur"){
			this.frmArene.ajoutJoueur((JLabel)info);
		}
		if(ordre == "ajout phrase"){
			this.frmArene.ajoutChat((String)info);
			((JeuServeur)this.leJeu).envoi((String)this.frmArene.getTxtChat().getText());
		}
		
	}

	public void setConnection(Connection connection){
		this.connection = connection;
		if(leJeu instanceof JeuServeur){
			this.leJeu.setConnection(connection);
		}
	}
	
	public void deconnection(Connection connection){
		leJeu.deconnection(connection);
	}
	
	public void evenementVue(JFrame uneFrame, Object info) {
		if(uneFrame instanceof EntreeJeu){
			evenementEntreeJeu(info);
		}
		if(uneFrame instanceof ChoixJoueur){
			evenementChoixJoueur(info);
		}
		if(uneFrame instanceof Arene){
			evenementArene(info);
		}
	}

	private void evenementChoixJoueur(Object info) {
		((JeuClient)this.leJeu).envoi(info);
		this.frmChoixJoueur.dispose();
		this.frmArene.setVisible(true);
	}

	private void evenementEntreeJeu(Object info) {
		if((String)info == "serveur"){
			ServeurSocket serveursocket = new ServeurSocket(this, PORT);
			this.leJeu = new JeuServeur(this);
			this.frmEntreeJeu.dispose();
			this.frmArene = new Arene("serveur", this);
			((JeuServeur)this.leJeu).constructionMurs();
			((JeuServeur)this.leJeu).ajoutConsommable();
			this.frmArene.setVisible(true);
		}
		else{
			ClientSocket clientsocket;
			(clientsocket = new ClientSocket ((String)info, PORT, this)).isConnexionOk();
			this.leJeu = new JeuClient(this);
			this.leJeu.setConnection(connection);
			this.frmArene = new Arene("client", this);
			this.frmChoixJoueur = new ChoixJoueur(this);
			this.frmChoixJoueur.setSize(416, 313);
			this.frmChoixJoueur.setVisible(true);
			this.frmEntreeJeu.dispose();
		}
	}
	
	public void receptionInfo(Connection connection, Object info){
		this.leJeu.reception(connection, info);
	}

	public Controle () {
		this.frmEntreeJeu = new EntreeJeu(this);
		this.frmEntreeJeu.setVisible(true);
	}

	public static void main(String[] args) {
		
		new Controle();
		
	}

}
