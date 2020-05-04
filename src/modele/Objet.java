package modele;

public abstract class Objet {
	protected int posX;
	protected int posY;
	protected Label label;
	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}
	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}
	/**
	 * @return the label
	 */
	public Label getLabel() {
		return label;
	}
	
	/**
	 * @param posX the posX to set
	 */
	public void setPosX(int posX) {
		this.posX = posX;
	}
	/**
	 * @param posY the posY to set
	 */
	public void setPosY(int posY) {
		this.posY = posY;
	}
	/**  * contrôle si l'objet actuel touche l'objet passé en paramètre  
	 * @param objet  
	 * @return vrai si les 2 objets se touchent  
	 */ 
	
	public boolean toucheObjet (Objet objet) {
		if (objet.label==null) {
			return false ;  
			}else{
				if (objet.label.getjLabel()==null) {
					return false ; 	 
					}else{    
						int l_obj = objet.label.getjLabel().getWidth() ;    
						int h_obj = objet.label.getjLabel().getHeight() ;    
						int l_this = this.label.getjLabel().getWidth() ;    
						int h_this = this.label.getjLabel().getHeight() ;    
						return(!((this.posX+l_this<objet.posX ||
								this.posX>objet.posX+l_obj) ||
								(this.posY+h_this<objet.posY ||
										this.posY>objet.posY+h_obj))) ;
						}  
				} 
		} 
}
