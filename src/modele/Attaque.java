package modele;

import java.util.ArrayList;
import java.util.Hashtable;

import controleur.Global;
import outils.connexion.Connection;

public class Attaque extends Thread implements Global {
	private Joueur attaquant;
	private JeuServeur jeuServeur;
	private ArrayList<Mur> lesMurs;
	private Hashtable<Connection, Joueur> lesJoueurs;
	
	public Attaque(Joueur attaquant, JeuServeur jeuServeur, ArrayList<Mur> lesMurs, Hashtable<Connection, Joueur> lesJoueurs){
		this.attaquant = attaquant;
		this.lesJoueurs = lesJoueurs;
		this.jeuServeur = jeuServeur;
		this.lesMurs = lesMurs;
		super.start();
	}
	
	public void run(){

		this.attaquant.affiche(MARCHE, 1);
		Boule laBoule = this.attaquant.getBoule();
		int orientation = this.attaquant.getOrientation();
		laBoule.getLabel().getjLabel().setVisible(true);
		Joueur victime = null;
		do{
			
			if(orientation == GAUCHE){
				laBoule.setPosX(laBoule.getPosX() - LEPAS);
			}else{
				laBoule.setPosX(laBoule.getPosX() + LEPAS);
			}
			
			laBoule.getLabel().getjLabel().setBounds(laBoule.getPosX(), laBoule.getPosY(), L_BOULE, H_BOULE);
			pause(10,0);
			this.jeuServeur.envoi(laBoule.getLabel());
			victime = this.toucheJoueur();
			}while(laBoule.getPosX() > 0 && laBoule.getPosX() < L_ARENE && !toucheMur() && victime == null);
			if(victime != null && !victime.estMort()){
				
				if(attaquant.isSupplementaire() == true && victime.isBloquer() == false){
					victime.perteVieSupplementaire();
					attaquant.setSupplementaire(false);
				}
				else if(victime.isBloquer() == true){
					victime.blocage();
				}
				else{
					victime.perteVie();
				}
				
				this.jeuServeur.envoi(HURT);
				this.attaquant.gainVie();
				for(int i = 1; i <= NBETATSBLESSE; i++){
					victime.affiche(BLESSE, i);
					this.pause(80, 0);
				}
				if(victime.estMort()){
					this.jeuServeur.envoi(DEATH);
					for(int i = 1; i <= NBETATSMORT; i++){
						victime.affiche(MORT, i);
						this.pause(80, 0);
					}
				}else{
					victime.affiche(MARCHE, 1);
				}
				this.attaquant.affiche(MARCHE, 1);
			}
			laBoule.getLabel().getjLabel().setVisible(false);
			this.jeuServeur.envoi(laBoule.getLabel());
	}
	
	public void pause(long milli, int nano){
		try {
			Thread.sleep(milli, nano);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean toucheMur(){
		for(Mur mur : this.lesMurs){
				if(this.attaquant.getBoule().toucheObjet(mur)){
					return true;
				}
			}
		return false;
	}
	
	private Joueur toucheJoueur(){
		for(Joueur unJoueur : this.lesJoueurs.values()){
			if(this.attaquant.getBoule().toucheObjet(unJoueur)){
				return unJoueur;
			}
		}
		return null;
	}
}
