package modele;

import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import controleur.Global;

public class Consommable extends Objet implements Global, Serializable {
	
	String image;
	String effet;
	
	public Consommable (String effet,String image){
		this.effet=effet;
		this.image = image;
		super.posX = (int) (Math.round(Math.random() * (L_ARENE - L_MUR)));
		super.posY = (int) (Math.round(Math.random() * (H_ARENE - H_MUR)));
		super.label = new Label(-1, new JLabel());
		super.label.getjLabel().setHorizontalAlignment(SwingConstants.CENTER);
		super.label.getjLabel().setVerticalAlignment(SwingConstants.CENTER);
		super.label.getjLabel().setBounds(posX, posY, L_MUR, H_MUR);
		super.label.getjLabel().setIcon(new ImageIcon(image));
		
	}

	/**
	 * @return the effet
	 */
	public String getEffet() {
		return effet;
	}
}