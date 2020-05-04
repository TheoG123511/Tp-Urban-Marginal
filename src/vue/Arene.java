package vue;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import controleur.Controle;
import controleur.Global;
import outils.son.Son;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Arene extends JFrame implements Global {
	
	private Controle controle;
	private JPanel contentPane;
	private JTextField txtSaisie = new JTextField();
	private JTextArea txtChat = new JTextArea();
	private boolean client;
	private Son[] lesSons = new Son[SON.length];
	JPanel jpnMurs = new JPanel();
	JPanel jpnJeu = new JPanel();
	JPanel jpnConso = new JPanel();
	
	public void supprAjoutConsommable(JLabel newConsommable){
		jpnConso.removeAll();
		jpnConso.add(newConsommable);
		jpnConso.repaint();
	}
	
	public void ajoutMur(JLabel unMur){
		jpnMurs.add(unMur);
		jpnMurs.repaint();
	}
	
	public void ajoutConsommable(JLabel unConsommable){
		jpnConso.add(unConsommable);
		jpnConso.repaint();
	}
	
	public void ajoutPanelMurs(JPanel objet){
		jpnMurs.add(objet);
		jpnMurs.repaint();
		contentPane.requestFocus();
	}
	
	public void ajoutPanelConsommables(JPanel objet){
		this.jpnConso.add(objet);
		this.jpnConso.repaint();
		contentPane.requestFocus();
	}
	
	public void ajoutJoueur(JLabel unJoueur){
		this.jpnJeu.add(unJoueur);
		this.jpnJeu.repaint();
	}
	
	public void ajoutModifJoueur(int num, JLabel unLabel){
		try {
			jpnJeu.remove(num);
		} catch (ArrayIndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
		}
		jpnJeu.add(unLabel,num);
		jpnJeu.repaint();
		
	}
	
	public void joueSon(int numSon){
		this.lesSons[numSon].play();
	}
	
	private void contentPane_keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		int valeur = -1;
		switch(arg0.getKeyCode()){
		
		case KeyEvent.VK_SPACE:
			valeur = TIRE;
			break;
			
		case KeyEvent.VK_Z:
			valeur = HAUT;
			break;
			
		case KeyEvent.VK_Q:
			valeur = GAUCHE;
			break;
			
		case KeyEvent.VK_S:
			valeur = BAS;
			break;
			
		case KeyEvent.VK_D:
			valeur = DROITE;
			break;
			
		case KeyEvent.VK_SHIFT:
			valeur = DEFENSE;
			break;
		}
		if(valeur != -1){
			this.controle.evenementVue(this, ACTION + SEPARE + valeur);
		}
	}
	
	private void txtSaisie_keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getKeyCode() == KeyEvent.VK_ENTER){
			if(txtSaisie.getText() != ""){
				this.controle.evenementVue(this, CHAT + SEPARE + txtSaisie.getText());
				this.txtSaisie.setText("");
			}
			this.contentPane.requestFocus();
		}
	}
	
	public void ajoutChat(String unePhrase){
		this.txtChat.setText(unePhrase + "\r\n" + this.txtChat.getText());
	}
	
	public void remplaceChat(String contenu){
		this.txtChat.setText(contenu);
	}

	/**
	 * @return the jpnMurs
	 */
	public JPanel getJpnMurs() {
		return jpnMurs;
	}
	
	/**
	 * @return the jpnConso
	 */
	public JPanel getJpnConso() {
		return jpnConso;
	}

	/**
	 * @return the txtChat
	 */
	public JTextArea getTxtChat() {
		return txtChat;
	}

	/**
	 * Create the frame.
	 */
	public Arene(String typeJeu, Controle controle) {
		this.controle = controle;
		this.client = typeJeu == "client";
		
		setTitle("Arena");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, L_ARENE+3*MARGE, H_ARENE + H_CHAT);
		contentPane = new JPanel();
		
		if(this.client){
			contentPane.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent arg0) {
					contentPane_keyPressed(arg0);
				}
			});
		}
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		jpnJeu.setBounds(0, 0, L_ARENE, H_ARENE);
		contentPane.add(jpnJeu);
		jpnJeu.setLayout(null);
		jpnJeu.setOpaque(false);
		
				
		jpnConso.setBounds(0, 0, L_ARENE, H_ARENE);
		contentPane.add(jpnConso);
		jpnConso.setLayout(null);
		jpnConso.setOpaque(false);
		
		jpnMurs.setBounds(0, 0, L_ARENE, H_ARENE);
		contentPane.add(jpnMurs);
		jpnMurs.setLayout(null);
		jpnMurs.setOpaque(false);
		
		jpnJeu.setOpaque(false);
		
		JLabel lblFond = new JLabel("");
		lblFond.setBounds(0, 0, L_ARENE, H_ARENE);
		lblFond.setIcon(new ImageIcon(FONDARENE));
		contentPane.add(lblFond);
		
		if(client){
		txtSaisie.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				txtSaisie_keyPressed(arg0);
			}
		});
		txtSaisie.setBounds(0, H_ARENE, L_ARENE, H_SAISIE);
		contentPane.add(txtSaisie);
		txtSaisie.setColumns(10);
		}
		JScrollPane jspChat = new JScrollPane();
		jspChat.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jspChat.setBounds(0, H_ARENE + H_SAISIE, L_ARENE, H_CHAT - H_SAISIE - 7*MARGE );
		contentPane.add(jspChat);
		

		jspChat.setViewportView(txtChat);
		
		if(this.client){
			for(int i = 0; i < SON.length; i++){
				this.lesSons[i] = new Son(CHEMINSONS + SON[i]);
			}
		}
		
	}


}
