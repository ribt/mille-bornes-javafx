package vue;

import java.io.IOException;

import controleur.EcouteurSouris;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Jeu;
import modele.cartes.Attaque;
import modele.cartes.Carte;
import modele.joueurs.Gentil;

public class PanneauDeJeu extends BorderPane {
	private Jeu jeu;
	private ZoneDeJeu zoneDeJeu;
	private ZoneAffichageJoueur affJoueurHaut;
	private ZoneAffichageJoueur affJoueurDroite;
	private ZoneAffichageJoueur affJoueurGauche;
	private ZoneMilieu milieu;
	private EcouteurSouris controleur;
	private Stage stage;

	public PanneauDeJeu(Jeu jeu, Stage stage) {
		this.jeu = jeu;
		this.stage = stage;
		
		controleur = new EcouteurSouris(jeu, this);
		affJoueurHaut = new ZoneAffichageJoueur("haut");
		affJoueurDroite = new ZoneAffichageJoueur("droite");
		affJoueurGauche = new ZoneAffichageJoueur("gauche");
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
		actualiserAutres();
		if (jeu.estPartieFinie()) {
			Alert msg = new Alert(AlertType.INFORMATION, "Victoire de "+jeu.getGagnant());
			msg.show();
		} else if (jeu.getJoueurActif() instanceof Gentil) {
			Gentil bot = (Gentil) jeu.getJoueurActif();
			int choix = bot.choisitCarte();
			if (choix < 0) {
				bot.defausseCarte(jeu, -choix+1);
			} else {
				Carte carte = bot.getMain().get(choix-1);
				if (carte instanceof Attaque)
					bot.joueCarte(jeu, choix-1, bot.choisitAdversaire((Attaque) carte));
				else
					bot.joueCarte(jeu, choix-1);
			}
			actualiserAutres(); // TODO : faire une jolie transition
			jeu.activeProchainJoueurEtTireCarte();
			actualiserAffichage();
		}
		stage.sizeToScene();
	}
	
	private void actualiserAutres() {
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
	
	public EcouteurSouris getEcouteurSouris() {
		return controleur;
	}
}
