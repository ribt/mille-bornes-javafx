package controleur;

import java.io.IOException;
import java.nio.file.Files;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import java.nio.file.Path;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
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
		JsonObject save = null;
		try {
			save = JsonParser.parseReader(Files.newBufferedReader(Path.of("save.json"))).getAsJsonObject();
		} catch (Exception e) {
			Alert msg = new Alert(AlertType.ERROR, "Erreur de récupération de sauvegarde :"+e.toString());
			msg.showAndWait();
		}
		if(save != null) {
			Jeu jeuCharge = new Jeu(save);
			if(showConfirmation(jeuCharge).toString() == "OK_DONE") {
				jeu = jeuCharge;
				Stage stage = panneau.getStage();
				panneau = new PanneauDeJeu(jeu, stage);
				scene = new Scene(panneau);
				panneau.getControleur().setScene(scene);
				stage.setScene(scene);
				stage.sizeToScene();
				stage.setTitle("1000 bornes");
				stage.setResizable(false);
				stage.show();
				panneau.getControleur().tourSuivant();
			}
		}
	}

	public void setEcouteurMenu() {
		panneau.getEcouteurMenu().setControleur(this);
	}

	public void hub() {
		Stage stage = panneau.getStage();
		PanneauDebut panneauDebut = new PanneauDebut(stage);
		panneauDebut.getEcouteur().setStage(stage);
		stage.setScene(new Scene(panneauDebut));
		stage.setResizable(false);
		stage.setTitle("1000 bornes");
		stage.show();

		stage.sizeToScene();
	}

	private ButtonData showConfirmation(Jeu jeu) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Charger partie");
		alert.setHeaderText("Voulez vous charger cette partie ?");
		alert.setContentText(jeu.toString());
		alert.showAndWait();
		return alert.getResult().getButtonData();
	}
}
