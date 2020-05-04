package modele;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

public class Mur extends Objet implements Global {
	
	public Mur (){
		super.posX = (int) (Math.round(Math.random() * (L_ARENE - L_MUR)));
		super.posY = (int) (Math.round(Math.random() * (H_ARENE - H_MUR)));
		super.label = new Label(-1, new JLabel());
		super.label.getjLabel().setHorizontalAlignment(SwingConstants.CENTER);
		super.label.getjLabel().setVerticalAlignment(SwingConstants.CENTER);
		super.label.getjLabel().setBounds(posX, posY, L_MUR, H_MUR);
		super.label.getjLabel().setIcon(new ImageIcon(MUR));
		
	}

}
