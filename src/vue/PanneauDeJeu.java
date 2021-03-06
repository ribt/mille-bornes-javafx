package vue;

import java.io.IOException;

import controleur.Controleur;
import controleur.EcouteurMenu;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import modele.Jeu;
import modele.Joueur;
import modele.cartes.Carte;

public class PanneauDeJeu extends StackPane {
	private Jeu jeu;
	private ZoneDeJeu zoneDeJeu;
	private ZoneAffichageJoueur affJoueurHaut;
	private ZoneAffichageJoueur affJoueurDroite;
	private ZoneAffichageJoueur affJoueurGauche;
	private ZoneMilieu milieu;
	private Controleur controleur;
	private PremierPlan premierPlan;
	private BorderPane secondPlan;
	private EcouteurMenu ecouteurMenu;
	private MenuBar menus;

	public PanneauDeJeu(Controleur controleur) {
		this.controleur = controleur;
		this.premierPlan = new PremierPlan(controleur);
		this.secondPlan = new BorderPane();
		
		setMinWidth(750);
		
        getChildren().addAll(premierPlan, secondPlan);
        premierPlan.toFront();
		
		
		affJoueurHaut = new ZoneAffichageJoueur("haut");
		affJoueurDroite = new ZoneAffichageJoueur("droite");
		affJoueurGauche = new ZoneAffichageJoueur("gauche");
		milieu = new ZoneMilieu(controleur);
		zoneDeJeu = new ZoneDeJeu(controleur);	
		
		BorderPane.setAlignment(zoneDeJeu, Pos.CENTER);
		secondPlan.setBottom(zoneDeJeu);
	    
		BorderPane.setAlignment(affJoueurDroite, Pos.CENTER);
		BorderPane.setAlignment(affJoueurGauche, Pos.CENTER);
		
		secondPlan.setCenter(milieu);
	    
	    try {
	    	FXMLLoader loader = new FXMLLoader(PanneauDeJeu.class.getResource("menu.fxml"));
			this.menus = loader.load();
			ecouteurMenu = loader.getController();
			ecouteurMenu.setControleur(controleur);
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
	    	affJoueurDroite.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    	affJoueurGauche.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur());
	    } else { // n == 4
	    	affJoueurDroite.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur());
	    	affJoueurHaut.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur());
	    	affJoueurGauche.actualiserAffichage(jeu.getJoueurActif().getProchainJoueur().getProchainJoueur().getProchainJoueur());
	    }
	}
	
	public Controleur getControleur() {
		return controleur;
	}
	
	public void animationPioche() {
		getScene().getWindow().sizeToScene(); // trust me ;)
		premierPlan.animation(Carte.imageDos, milieu.getPositionPioche(), zoneDeJeu.getPositionCarte(6));
	}
	
	public void simulerDefausse(int choix) {
		zoneDeJeu.cacherCarte(choix);
		premierPlan.animation(jeu.getJoueurActif().getMain().get(choix).getImage(), zoneDeJeu.getPositionCarte(choix), milieu.getPositionDefausse());
	}
	
	public void simulerAttaque(int choix, Joueur cible) {
		ZoneAffichageJoueur zoneCible;
		if (cible == jeu.getJoueurActif())
			zoneCible = zoneDeJeu;
		else if (cible == affJoueurHaut.getJoueur())
			zoneCible = affJoueurHaut;
		else if (cible == affJoueurGauche.getJoueur())
			zoneCible = affJoueurGauche;
		else
			zoneCible = affJoueurDroite;
		
		zoneDeJeu.cacherCarte(choix);
		premierPlan.animation(jeu.getJoueurActif().getMain().get(choix).getImage(), zoneDeJeu.getPositionCarte(choix), zoneCible.getPositionBataille());
	}
	
	public EcouteurMenu getEcouteurMenu() {
		return ecouteurMenu;
	}
	
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
		VBox partieHaute = ((VBox)secondPlan.getTop());
		partieHaute.getChildren().remove(affJoueurHaut);

		secondPlan.setRight(null);
		secondPlan.setLeft(null);

		int n = jeu.getNbJoueurs();
	    if (n == 2) {
	    	partieHaute.getChildren().add(affJoueurHaut);
	    } else if (n == 3) {
	    	secondPlan.setRight(affJoueurDroite);
			secondPlan.setLeft(affJoueurGauche);
	    } else { // n == 4
	    	partieHaute.getChildren().add(affJoueurHaut);
	    	secondPlan.setRight(affJoueurDroite);
			secondPlan.setLeft(affJoueurGauche);
	    }
	}
	
	public PremierPlan getPremierPlan() {
		return premierPlan;
	}
	
	public BorderPane getSecondPlan() {
		return secondPlan;
	}
	
	public Pane[] getZones() {
		return new Pane[]{zoneDeJeu, affJoueurDroite, affJoueurHaut, affJoueurGauche, milieu};
	}
	
	public void cacherCarte(int rang) {
		zoneDeJeu.cacherCarte(rang);
	}
}
