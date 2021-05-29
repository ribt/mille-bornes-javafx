package vue;

import java.io.IOException;

import controleur.EcouteurSouris;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modele.Jeu;

public class PanneauDeJeu extends BorderPane {
	private Jeu jeu;
	private ZoneDeJeu zoneDeJeu;
	private ZoneAffichageJoueur affJoueurHaut;
	private ZoneAffichageJoueur affJoueurDroite;
	private ZoneAffichageJoueur affJoueurGauche;
	private ZoneMilieu milieu;
	private EcouteurSouris controleur;

	public PanneauDeJeu(Jeu jeu) {
		this.jeu = jeu;
		
		controleur = new EcouteurSouris(jeu, this);
		affJoueurHaut = new ZoneAffichageJoueur();
		affJoueurDroite = new ZoneAffichageJoueur();
		affJoueurGauche = new ZoneAffichageJoueur();
		milieu = new ZoneMilieu(controleur);
		zoneDeJeu = new ZoneDeJeu(controleur);	
		
		setAlignment(zoneDeJeu, Pos.CENTER);
		setBottom(zoneDeJeu);
	    
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
	}
	
	public void actualiserAffichage() {
		if (jeu.estPartieFinie()) {
			Alert msg = new Alert(AlertType.INFORMATION, "Victoire de "+jeu.getGagnant());
			msg.show();
		}
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
	
	public EcouteurSouris getEcouteurSouris() {
		return controleur;
	}
}
