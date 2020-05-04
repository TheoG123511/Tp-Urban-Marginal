package outils.son;

import outils.son.Sound;
import java.io.File;
import outils.son.exceptions.*;
import java.io.Serializable;

/**
 * <p>Titre : </p>
 *
 * <p>Description : </p>
 *
 * <p>Copyright : Copyright (c) 2007</p>
 *
 * <p>Soci�t� : </p>
 *
 * @author theo gaillard
 * @version 1.0
 */

public class Son implements Serializable {

    //--- propri�t�s ---
    private String son ;
    private Sound sound ;

    //--- constructeur : charge en m�moire le son ---
    public Son (String nomfic) {
        this.son = nomfic ;
        try {
            this.sound = new Sound(new File(this.son));
        } catch (SonException ex) {
        }
    }

    //--- pour jouer une fois un son ---
    public void play() {
        this.sound.boucle(1) ;
    }

    //--- pour jouer un son en boucle ---
    public void playContinue () {
        sound.boucle() ;
    }

	public void stop() {
		this.sound.stop();
	}

}
