package vue;

import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import modele.Jeu;

public class PanneauDeJeu extends BorderPane{
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
		
	    setBottom(zoneDeJeu);
	    setAlignment(affJoueurHaut, Pos.CENTER);
	    setTop(affJoueurHaut);
	    setAlignment(affJoueurDroite, Pos.CENTER);
	    setRight(affJoueurDroite);
	    setAlignment(affJoueurGauche, Pos.CENTER);
	    setLeft(affJoueurGauche);
	    setCenter(milieu);
	    
	    //Pane centre = new Pane(zoneDeDessin);
	    //cadre.setCenter(centre);

	    //Un canvas n'étant pas resizable, on doit le redimensionner "à la main".
	    //zoneDeDessin.widthProperty().bind(centre.widthProperty());
	    //zoneDeDessin.heightProperty().bind(centre.heightProperty()); // Prend en compte la barre d'état...
	}

}
