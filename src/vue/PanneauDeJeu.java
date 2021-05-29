package vue;

import java.io.IOException;

import controleur.Controleur;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Jeu;

public class PanneauDeJeu extends StackPane {
	private Jeu jeu;
	private ZoneDeJeu zoneDeJeu;
	private ZoneAffichageJoueur affJoueurHaut;
	private ZoneAffichageJoueur affJoueurDroite;
	private ZoneAffichageJoueur affJoueurGauche;
	private ZoneMilieu milieu;
	private Controleur controleur;
	private Stage stage;
	private PremierPlan premierPlan;
	private BorderPane secondPlan;

	public PanneauDeJeu(Jeu jeu, Stage stage) {
		this.jeu = jeu;
		this.stage = stage;
		this.premierPlan = new PremierPlan();
		this.secondPlan = new BorderPane();
		
        getChildren().addAll(premierPlan, secondPlan);
        premierPlan.toFront();
		
		controleur = new Controleur(jeu, this);
		affJoueurHaut = new ZoneAffichageJoueur("haut");
		affJoueurDroite = new ZoneAffichageJoueur("droite");
		affJoueurGauche = new ZoneAffichageJoueur("gauche");
		milieu = new ZoneMilieu(controleur);
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
	}
	
	public void actualiserAffichage() {
		milieu.actualiserAffichage(jeu);
	    zoneDeJeu.actualiserAffichage(jeu.getJoueurActif());
	    
	    int n = jeu.getNbJoueurs();
	    
	    if (n == 2) {
	    	affJoueurHaut.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    } else if (n == 3) {
	    	affJoueurDroite.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    	affJoueurGauche.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur());
	    } else { // n == 4
	    	affJoueurDroite.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    	affJoueurHaut.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur());
	    	affJoueurGauche.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur().getProchainJoueur());
	    }
	    stage.sizeToScene();
	    
		if (jeu.estPartieFinie()) {
			Alert msg = new Alert(AlertType.INFORMATION, "Victoire de "+jeu.getGagnant());
			msg.show();
		}
	}
	
	public Controleur getControleur() {
		return controleur;
	}
	
	public void animationPioche() {
		premierPlan.animation(milieu.getPositionPioche(), zoneDeJeu.getPositionDernierecarte());
	}
}
