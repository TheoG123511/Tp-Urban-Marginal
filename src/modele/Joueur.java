package modele;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Hashtable;
import javax.swing.JLabel;

import controleur.Global;

import javax.swing.ImageIcon;
import outils.connexion.Connection;

public class Joueur extends Objet implements Global {
	

	
	private String pseudo;
	private int numPerso;
	private Boule boule;
	private Label message;
	private JeuServeur jeuServeur;
	private int vie, orientation, etape;
	private static final int MAXVIE = 10;
	private static final int GAIN = 1;
	private static final int PERTE = 2;
	private boolean bloquer = false;
	private boolean supplementaire = false;
	
	/**
	 * Perte de vie supplémentaire si l'attaquant a un bonus de dégât
	 */
	public void perteVieSupplementaire() {
		if(this.vie == 1){
			this.vie = vie -1;
		}
		else{
			this.vie = vie -PERTE*2;
		}
	}

	
	/**
	 * @param supplementaire the supplementaire to set
	 */
	public void setSupplementaire(boolean supplementaire) {
		this.supplementaire = supplementaire;
	}


	/**
	 * @return the supplementaire
	 */
	public boolean isSupplementaire() {
		return supplementaire;
	}
	
	/**
	 * @return the bloquer
	 */
	public boolean isBloquer() {
		return bloquer;
	}

	/**
	 * @return the message
	 */
	public Label getMessage() {
		return message;
	}

	/**
	 * @return the pseudo
	 */
	public String getPseudo() {
		return pseudo;
	}

	/**
	 * @return the boule
	 */
	public Boule getBoule() {
		return boule;
	}
	
	public void perteVie(){
		if(this.vie == 1){
			this.vie = vie -1;
		}
		else{
			this.vie = vie - PERTE;
		}
	}
	
	/**
	 * Aucun dégât si la victime a touché le consommable "bloquer"
	 */
	
	public void blocage(){
		if(this.vie >0 && this.bloquer == true){
			this.bloquer = false;
		}
	}
	
	public void gainVie(){
		this.vie = vie + GAIN;
	}
	
	public boolean estMort(){
		boolean mort = true;
		if (this.vie > 0){
			mort = false;
		}
		return mort;
	}

	public void initPerso(String pseudo, int numPerso, Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur> lesMurs){
		this.pseudo = pseudo;
		this.numPerso = numPerso;
		this.label = new Label(Label.getNbLabel(), new JLabel());
		this.label.getjLabel().setVerticalAlignment(JLabel.CENTER);
		this.label.getjLabel().setHorizontalAlignment(JLabel.CENTER);
		Label.setNbLabel(Label.getNbLabel()+1);
		this.jeuServeur.nouveauLabelJeu(this.label);
		
		this.message = new Label(Label.getNbLabel(), new JLabel());
		this.message.getjLabel().setHorizontalAlignment(JLabel.CENTER);
		Label.setNbLabel(Label.getNbLabel() + 1);
		this.message.getjLabel().setFont(new Font("Dialog", Font.PLAIN, 8));
		this.jeuServeur.nouveauLabelJeu(message);
		
		premierePosition(lesJoueurs, lesMurs);
		affiche(MARCHE, this.etape);
		this.boule = new Boule(this.jeuServeur);
		this.jeuServeur.envoi(boule.getLabel());
	}
	
	private boolean toucheJoueur(Hashtable<Connection, Joueur> lesJoueurs){
		for(Joueur unJoueur : lesJoueurs.values()){
			if(!unJoueur.equals(this)){
				if(this.toucheObjet(unJoueur)){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean toucheMur(ArrayList<Mur> lesMurs){
		for(Mur mur : lesMurs){
			if(this.toucheObjet(mur)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param lesConsommables
	 * @return Si l'objet a été touché
	 */
	private Consommable toucheUnConsommable(ArrayList<Consommable> lesConsommables){
		for(Consommable unConsommable : lesConsommables){
			if(this.toucheObjet(unConsommable)){
				return unConsommable;
			}
		}
		return null;
	}
	
	private void premierePosition(Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur> lesMurs){
		this.label.getjLabel().setBounds(0, 0, L_PERSO, H_PERSO);
		do{
			this.posX = (int) Math.round(Math.random() * (L_ARENE - L_PERSO)) + 10;
			this.posY = (int) Math.round(Math.random() * (H_ARENE - H_PERSO - H_MESSAGE)) + 10;
		}while(toucheJoueur(lesJoueurs) || toucheMur(lesMurs));
	}
	
	public Joueur(JeuServeur jeuServeur) {
		this.jeuServeur = jeuServeur;
		vie = MAXVIE;
		etape = 1;
		orientation = GAUCHE;
	}
	
	public void affiche(String etat, int etape){
		this.label.getjLabel().setBounds(posX, posY, L_PERSO, H_PERSO);
		this.label.getjLabel().setIcon(new ImageIcon(PERSO + this.numPerso + etat + etape + "d" + this.orientation + ESTIMAGE));
		
		this.message.getjLabel().setBounds(posX - 10, posY + H_PERSO + 5, L_PERSO + 20, H_MESSAGE);
		this.message.getjLabel().setText(this.pseudo + " : " + this.vie);
		
		this.jeuServeur.envoi(this.label);
		this.jeuServeur.envoi(this.message);
	}
	
	private int deplace(int action, int position, int orientation, int lepas, int max,  Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur>lesMurs,ArrayList<Consommable> lesConsommables){
		this.orientation = orientation;
		int ancpos = position;
		position += lepas;
		if(position < 0){
			position = 0;
		}else if(position > max){
			position = max;
		}
		
		
		if(action == GAUCHE || action == DROITE){
			this.posX = position;
		}else{
			this.posY = position;
		}
		
		if(this.toucheMur(lesMurs) || this.toucheJoueur(lesJoueurs)){
			position = ancpos;
		}
		
		if(this.toucheUnConsommable(lesConsommables) != null){
			Consommable unConsommable = this.toucheUnConsommable(lesConsommables);
				if("ajout vie" == unConsommable.getEffet()){
					this.vie = vie + 2;
					this.jeuServeur.envoiConsommable();
				}
				if("bloquer" == unConsommable.getEffet()){
					this.bloquer = true;
					this.jeuServeur.envoiConsommable();
				}
				if("degats supplementaires" == unConsommable.getEffet()){
					this.supplementaire = true;
					this.jeuServeur.envoiConsommable();
				}
				this.jeuServeur.retirerEffetConsommable(unConsommable);
		}
	
		
		if(this.etape < NBETATSMARCHE){
			this.etape = this.etape + 1;
		}else{
			this.etape = 1;
		}
		return position;
	}
	


	public void action(int action, Hashtable<Connection, Joueur> lesJoueurs, ArrayList<Mur> lesMurs, ArrayList<Consommable> lesConsommables){
		switch(action){
			case GAUCHE:
				this.posX = this.deplace(action, this.posX, GAUCHE, -LEPAS, L_ARENE - L_PERSO, lesJoueurs, lesMurs, lesConsommables);
				break;
				
			case DROITE:
				this.posX = this.deplace(action, this.posX, DROITE, LEPAS, L_ARENE - L_PERSO, lesJoueurs, lesMurs, lesConsommables);
				break;
				
			case HAUT:
				this.posY = this.deplace(action, this.posY, GAUCHE, -LEPAS, L_ARENE - L_PERSO - H_MESSAGE, lesJoueurs, lesMurs, lesConsommables);
				break;
				
			case BAS:
				this.posY = this.deplace(action, this.posY, DROITE, LEPAS, L_ARENE - L_PERSO - H_MESSAGE, lesJoueurs, lesMurs, lesConsommables);
				break;
			
			case TIRE:
				if(!this.boule.getLabel().getjLabel().isVisible()){
					this.jeuServeur.envoi(FIGHT);
					this.boule.tireBoule(this, lesMurs, lesJoueurs);
				}
				break;
			case DEFENSE:
				
				break;
		}
		this.affiche(MARCHE, this.etape);
	}

	public int getOrientation() {
		// TODO Auto-generated method stub
		return orientation;
	}
	
	public void departJoueur(){
		if(this.label != null){
			this.label.getjLabel().setVisible(false);
			this.message.getjLabel().setVisible(false);
			this.boule.getLabel().getjLabel().setVisible(false);
			this.jeuServeur.envoi(this.label);
			this.jeuServeur.envoi(this.message);
			this.jeuServeur.envoi(this.boule.getLabel());
		}
	}


}
