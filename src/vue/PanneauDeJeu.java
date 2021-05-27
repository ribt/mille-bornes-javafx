package vue;

import java.io.IOException;

import controleur.EcouteurSouris;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modele.Jeu;

public class PanneauDeJeu extends StackPane {
	private Jeu jeu;
	private PremierPlan premierPlan;
	private BorderPane secondPlan;
	private ZoneDeJeu zoneDeJeu;
	private ZoneAffichageJoueur affJoueurHaut;
	private ZoneAffichageJoueur affJoueurDroite;
	private ZoneAffichageJoueur affJoueurGauche;
	private ZoneMilieu milieu;
	private EcouteurSouris controleur;

	public PanneauDeJeu(Jeu jeu) {
		this.jeu = jeu;
		
		premierPlan = new PremierPlan();
		secondPlan = new BorderPane();
		
		getChildren().addAll(premierPlan, secondPlan);
		premierPlan.toFront();
		
		controleur = new EcouteurSouris(jeu, this);
		affJoueurHaut = new ZoneAffichageJoueur();
		affJoueurDroite = new ZoneAffichageJoueur();
		affJoueurGauche = new ZoneAffichageJoueur();
		milieu = new ZoneMilieu();
		zoneDeJeu = new ZoneDeJeu(controleur);	
		
		secondPlan.setAlignment(zoneDeJeu, Pos.CENTER);
		secondPlan.setBottom(zoneDeJeu);
	    
		secondPlan.setAlignment(affJoueurDroite, Pos.CENTER);
		secondPlan.setRight(affJoueurDroite);
		secondPlan.setAlignment(affJoueurGauche, Pos.CENTER);
	    secondPlan.setLeft(affJoueurGauche);
	    secondPlan.setCenter(milieu);
	    
	    try {
			MenuBar menus = new FXMLLoader(PanneauDeJeu.class.getResource("menu.fxml")).load();
			secondPlan.setTop(new VBox(10, menus, affJoueurHaut));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
	    secondPlan.setOnMouseDragged(premierPlan::sourisBouge);
	}
	
	public void actualiserAffichage() {
		milieu.actualiserAffichage(jeu);
	    zoneDeJeu.actualiserAffichage(jeu.getJoueurActif());
	    
	    int n = jeu.getNbJoueurs();
	    
	    if (n == 2) {
	    	affJoueurHaut.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    } else if (n == 3) {
	    	affJoueurGauche.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    	affJoueurDroite.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur());
	    } else { // n == 4
	    	affJoueurGauche.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    	affJoueurHaut.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur());
	    	affJoueurDroite.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur().getProchainJoueur());
	    }
	}
	
	public PremierPlan getPremierPlan() {
		return premierPlan;
	}
}
