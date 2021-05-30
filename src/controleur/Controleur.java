package controleur;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

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
	private boolean piocheEnCours;
	private boolean simulationEnCours;
	private int choixBot;
	private Joueur cibleBot;

	public Controleur(Jeu jeu, PanneauDeJeu panneau) {
		this.jeu = jeu;
		this.panneau = panneau;
	}

	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public void carteCliquee(MouseEvent event) {
		if (piocheEnCours || simulationEnCours) {
			event.consume();
			return;
		}
		scene.setCursor(Cursor.MOVE);
		this.carteSelectionne = GridPane.getColumnIndex((ImageView) event.getSource());
	}

	public void carteRelachee(MouseEvent event) {
		if (piocheEnCours || simulationEnCours) {
			event.consume();
			return;
		}

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
		jeu.activeProchainJoueur();
		panneau.actualiserAffichage();
		piocheEnCours = true;
		panneau.animationPioche();		
	}

	private void jouerTour() {
		jeu.tireCarte();
		panneau.actualiserAffichage();

		if (jeu.getJoueurActif() instanceof Gentil) {
			Gentil bot = (Gentil) jeu.getJoueurActif();
			Carte carte;
			int choix = bot.choisitCarte();
			if (choix < 0) {
				simulationEnCours = true;
				this.choixBot = -choix+1;
				this.cibleBot = null;
				panneau.simulerDefausse(-choix+1);
			} else {
				carte = bot.getMain().get(choix-1);
				if (carte instanceof Attaque) {
					simulationEnCours = true;
					this.choixBot = choix-1;
					this.cibleBot = bot.choisitAdversaire((Attaque) carte);
					panneau.simulerAttaque(choix-1, cibleBot);
				} else {
					simulationEnCours = true;
					this.choixBot = choix-1;
					this.cibleBot = bot;
					panneau.simulerAttaque(choix-1, bot);
				}
			}			
		}
	}

	public void finAnimation() {
		if (piocheEnCours) {
			piocheEnCours = false;
			jouerTour();
		} else if (simulationEnCours) {
			simulationEnCours = false;
			if (cibleBot == null)
				jeu.getJoueurActif().defausseCarte(jeu, choixBot);
			else if (cibleBot == jeu.getJoueurActif())
				jeu.getJoueurActif().joueCarte(jeu, choixBot);
			else
				jeu.getJoueurActif().joueCarte(jeu, choixBot, cibleBot);
			tourSuivant();
		}
	}

	public void sauvegarde() {
		jeu.sauvegarde();
	}
	
	public void charge() {
		JsonObject json = jeu.sauvegarde();
		System.out.println(json);
		Gson gson = new Gson();
		Jeu copieJeu = gson.fromJson(json, Jeu.class);
		System.out.println(copieJeu);
	}

	public void setEcouteurMenu() {
		panneau.getEcouteurMenu().setControleur(this);
	}

}
