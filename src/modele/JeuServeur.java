package modele;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controleur.Controle;
import controleur.Global;
import modele.Joueur;
import outils.connexion.Connection;

public class JeuServeur extends Jeu implements Global {
	
	private ArrayList<Mur> lesMurs = new ArrayList<Mur>();
	private ArrayList<Consommable> lesConsommables = new ArrayList<Consommable>();
	private ArrayList<Consommable> lesNewConsommables = new ArrayList<Consommable>();
	private Hashtable<Connection, Joueur> lesJoueurs = new Hashtable<Connection, Joueur>();
	private ArrayList<Joueur> lesJoueursDansLordre = new ArrayList<Joueur>();
	private JLabel newConsommable;
	
	public Consommable donneVie = new Consommable("ajout vie",CONSOVIE);
	public Consommable bloquer = new Consommable("bloquer",CONSOBLO);
	public Consommable damageup = new Consommable("degats supplementaires",CONSOUP);
	Consommable ListeConsommable[] = {donneVie, bloquer, damageup}; 
	
	public JeuServeur(Controle controle){
		this.controle = controle;
		Label.setNbLabel(0);
	}
	
	/**
	 * Création d'un nouveau consommable
	 */
	public void envoiConsommable(){	
		int x;
		int i=0;
		while(i<NBCONSO){
			x = (int) (Math.random() * 2);
			lesNewConsommables.add(ListeConsommable[x]);
			this.newConsommable = lesNewConsommables.get(i).getLabel().getjLabel();
			i++;
		}
		controle.demandeSupression(newConsommable);
	}
	
	public void majConsommable(Connection connection){
		controle.evenementModele(this, "envoi panel consommable", connection);
	}

	@Override
	public void setConnection(Connection connection) {
		this.lesJoueurs.put(connection, new Joueur(this));
	}

	@Override
	public void reception(Connection connection, Object info) {
		String [] infos = ((String)info).split(SEPARE);
		String laPhrase;
		switch(Integer.parseInt(infos[0])){
		
		case PSEUDO : 
			controle.evenementModele(this, "envoi panel mur", connection);
			controle.evenementModele(this, "envoi panel consommable", connection);
			for(Joueur unJoueur : lesJoueursDansLordre){
				super.envoi(connection, unJoueur.getLabel());
				super.envoi(connection, unJoueur.getMessage());
				super.envoi(connection, unJoueur.getBoule().getLabel());
			}
			lesJoueurs.get(connection).initPerso(infos[1], Integer.parseInt(infos[2]), this.lesJoueurs, this.lesMurs);
			this.lesJoueursDansLordre.add(this.lesJoueurs.get(connection)) ;
			laPhrase = "*** " + lesJoueurs.get(connection).getPseudo() + " vient de se connecter ***";
			this.controle.evenementModele(this, "ajout phrase", laPhrase);
			break;
			
		case CHAT :
			laPhrase = lesJoueurs.get(connection).getPseudo() + "> " + infos[1];
			this.controle.evenementModele(this, "ajout phrase", laPhrase);
			break;
			
		case ACTION:
			if(!lesJoueurs.get(connection).estMort()){
			lesJoueurs.get(connection).action(Integer.parseInt(infos[1]), lesJoueurs, lesMurs, lesConsommables);
			}
			break;
		}
	}

	@Override
	public void deconnection(Connection connection) {
		// TODO Auto-generated method stub
		this.lesJoueurs.get(connection).departJoueur();
		this.lesJoueurs.remove(connection);
	}
	
	public void constructionMurs(){
		for(int i=0; i < NBMURS; i++){
			lesMurs.add(new Mur());
			this.controle.evenementModele(this, "ajout mur", lesMurs.get(i).getLabel().getjLabel());
		}
	}
	
	//Ajoute aléatoirement un consommable à partir d'une liste de consomable
	public void ajoutConsommable(){
		int x;
		int i = 0;
		while(i<NBCONSO){
			x = (int) (Math.random() * 3);
			lesConsommables.add(ListeConsommable[x]);
			this.controle.evenementModele(this, "ajout consommable", lesConsommables.get(i).getLabel().getjLabel());
			i++;
		}
	}
	
	/**
	 * Retire l'effet du consommable dans l'arène
	 * @param unConsommable
	 */
	public void retirerEffetConsommable(Consommable unConsommable){
		lesConsommables.remove(unConsommable);
	}
	
	public void nouveauLabelJeu(Label label){
		this.controle.evenementModele(this, "ajout joueur", label.getjLabel());
	}

	/* (non-Javadoc)
	 * @see modele.Jeu#envoi(outils.connexion.Connection, java.lang.Object)
	 */
	public void envoi(Object info) {
		// TODO Auto-generated method stub
		for(Connection connection : lesJoueurs.keySet()){
			super.envoi(connection, info);
		}
	}
	
	

}
