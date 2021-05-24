package vue;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import modele.Jeu;

public class PanneauDeJeu extends BorderPane {
	private Jeu jeu;
	private ZoneDeJeu zoneDeJeu;
	private ZoneAffichageJoueur affJoueurHaut;
	private ZoneAffichageJoueur affJoueurDroite;
	private ZoneAffichageJoueur affJoueurGauche;
	private ZoneMilieu milieu;

	public PanneauDeJeu(Jeu jeu) {
		super();
		
		this.jeu = jeu;
		
		
		zoneDeJeu = new ZoneDeJeu();		
		affJoueurHaut = new ZoneAffichageJoueur();
		affJoueurDroite = new ZoneAffichageJoueur();
		affJoueurGauche = new ZoneAffichageJoueur();
		milieu = new ZoneMilieu();
		
		setAlignment(zoneDeJeu, Pos.CENTER);
	    setBottom(zoneDeJeu);
	    setAlignment(affJoueurHaut, Pos.CENTER);
	    
	    setAlignment(affJoueurDroite, Pos.CENTER);
	    setRight(affJoueurDroite);
	    setAlignment(affJoueurGauche, Pos.CENTER);
	    setLeft(affJoueurGauche);
	    setCenter(milieu);
	    
	    try {
			MenuBar menus = new FXMLLoader(PanneauDeJeu.class.getResource("menu.fxml")).load();
			setTop(new VBox(10, menus, affJoueurHaut));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    milieu.actualiserAffichage(jeu);
	    affJoueurHaut.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    zoneDeJeu.actualiserAffichage(jeu.getJoueurActif());
	    
	}

}
