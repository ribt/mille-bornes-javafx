package controleur;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import modele.Jeu;
import modele.Joueur;
import modele.cartes.Attaque;
import modele.cartes.Carte;
import modele.joueurs.Gentil;
import vue.Defausse;
import vue.PanneauDeJeu;
import vue.ZoneAffichageJoueur;

public class Controleur {
	private Jeu jeu;
	private PanneauDeJeu panneau;
	private Scene scene;
	private int carteSelectionne = -1;

	public Controleur(Jeu jeu, PanneauDeJeu panneau) {
		this.jeu = jeu;
		this.panneau = panneau;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void carteCliquee(MouseEvent event) {
		scene.setCursor(Cursor.MOVE);
		this.carteSelectionne = GridPane.getColumnIndex((ImageView) event.getSource());
	}
	
	public void carteRelachee(MouseEvent event) {
		scene.setCursor(Cursor.DEFAULT);
		Node cible = event.getPickResult().getIntersectedNode();
		
		try {
			if (cible != null) {
				Joueur joueurVise = null;
				if (cible instanceof ZoneAffichageJoueur) {
					joueurVise = ((ZoneAffichageJoueur) cible).getJoueur();
				} else if (cible.getParent() instanceof ZoneAffichageJoueur) {
					joueurVise = ((ZoneAffichageJoueur) cible.getParent()).getJoueur();
				}
				
				if (joueurVise != null) {
					if (joueurVise == jeu.getJoueurActif()) {
						jeu.getJoueurActif().joueCarte(jeu, carteSelectionne);
						tourSuivant();
					} else {
						jeu.getJoueurActif().joueCarte(jeu, carteSelectionne, joueurVise);
						tourSuivant();
					}
				}
				
				if (cible instanceof Defausse) {
					jeu.getJoueurActif().defausseCarte(jeu, carteSelectionne);
					tourSuivant();
				}
			}
		} catch (IllegalStateException e) {
			Alert msg = new Alert(AlertType.ERROR, e.getMessage());
			msg.show();
		}
		this.carteSelectionne = -1;
		panneau.actualiserAffichage();
	}
	
	public void tourSuivant() {
		jeu.activeProchainJoueurEtTireCarte();
		if (jeu.getJoueurActif() instanceof Gentil) {
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
			panneau.actualiserAffichage(); // TODO : faire une jolie transition
			jeu.activeProchainJoueurEtTireCarte();
			panneau.actualiserAffichage();
			
		}
	}

}
