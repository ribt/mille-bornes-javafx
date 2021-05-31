package controleur;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modele.Jeu;
import modele.Joueur;
import modele.cartes.Attaque;
import modele.cartes.Carte;
import modele.joueurs.Gentil;
import vue.Defausse;
import vue.PanneauDeJeu;
import vue.PanneauDebut;
import vue.ZoneAffichageJoueur;

public class Controleur {
	private Jeu jeu;
	private PanneauDeJeu panneau;
	private PanneauDebut accueil;
	private Scene scene;
	private int carteSelectionne = -1;
	private boolean piocheEnCours;
	private boolean simulationEnCours;
	private int choixBot;
	private Joueur cibleBot;

	public Controleur() {
		this.panneau = new PanneauDeJeu(this);
		this.accueil = new PanneauDebut(this);
	}

	public PanneauDebut getPanneauDebut() {
		return accueil;
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
		FileChooser dialogue = new FileChooser();
		File fichier = dialogue.showSaveDialog(null);
		if (!fichier.getName().endsWith(".json")) {
			fichier = new File(fichier.getAbsolutePath()+".json");
		}
		if (fichier != null) {
			DataOutputStream flux;
			try {
				flux = new DataOutputStream(new FileOutputStream(fichier));
				flux.writeChars(jeu.sauvegarde().toString());
				flux.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void charge() {
		FileChooser dialogue = new FileChooser();
		dialogue.getExtensionFilters().addAll(new ExtensionFilter("Fichiers JSON","*.json"), new ExtensionFilter("Tous les fichiers","*"));
		File fichier = dialogue.showOpenDialog(null);
		try {
			JsonObject jsonObject = new JsonObject();
			JsonParser parser = new JsonParser();
			JsonElement jsonElement = parser.parse(new FileReader(fichier.getAbsolutePath()));
			jsonObject = jsonElement.getAsJsonObject();
			Jeu jeuChargé = new Jeu(jsonObject);
			passerEnModeJeu(jeuChargé);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void hub() {
		scene.setRoot(accueil);
		scene.getWindow().sizeToScene();
	}

	public void passerEnModeJeu(Jeu jeu) {
		this.jeu = jeu;
		panneau.setJeu(jeu);
		scene.setRoot(panneau);
		scene.getWindow().sizeToScene();
		tourSuivant();
	}
}
