package controleur;


import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import modele.Jeu;
import modele.Joueur;
import modele.cartes.Attaque;
import modele.cartes.Carte;
import modele.joueurs.Bot;
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
		if (jeu.estPartieFinie()) {
			Alert msg = new Alert(AlertType.INFORMATION);
			List<Joueur> gagnants = jeu.getGagnant();
			if (gagnants.size() == 1) {
				msg.setHeaderText("Victoire de "+gagnants.get(0).nom+" !");
			} else {
				String txt = "Joueurs ex aequo : "+gagnants.get(0).nom;
				for (int i = 1; i < gagnants.size(); i++) {
					txt += ", "+gagnants.get(i).nom;
				}
				msg.setHeaderText(txt);
			}
			msg.show();
			return;
		}
		jeu.activeProchainJoueur();
		panneau.actualiserAffichage();
		piocheEnCours = true;
		panneau.animationPioche();		
	}

	private void jouerTour() {
		if (jeu.getJoueurActif() instanceof Bot) {
			Bot bot = (Bot) jeu.getJoueurActif();
			Carte carte;
			int choix = bot.choisitCarte();
			if (choix < 0) {
				simulationEnCours = true;
				this.choixBot = -choix-1;
				this.cibleBot = null;
				panneau.simulerDefausse(-choix-1);
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
			jeu.tireCarte();
			panneau.actualiserAffichage();
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
		try {
			if (!fichier.getName().endsWith(".json")) {
				fichier = new File(fichier.getAbsolutePath()+".json");
			}
			FileWriter writer = new FileWriter(fichier.getAbsolutePath());
			writer.write(jeu.sauvegarde().toString());
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			Alert msg = new Alert(AlertType.ERROR);
			msg.setHeaderText("La sauvgarde a échoué.");
			msg.setContentText(e.getMessage());
			msg.show();
		}
	}

	public void charge() {
		FileChooser dialogue = new FileChooser();
		dialogue.getExtensionFilters().addAll(new ExtensionFilter("Fichiers JSON","*.json"), new ExtensionFilter("Tous les fichiers","*"));
		File fichier = dialogue.showOpenDialog(null);
		try {
			JsonObject obj = JsonParser.parseReader(Files.newBufferedReader(Path.of(fichier.getAbsolutePath()))).getAsJsonObject();
			this.jeu = new Jeu(obj);
			if (showConfirmation(jeu))
				passerEnModeJeu(jeu);
		} catch (Exception e) {
			e.printStackTrace();
			Alert msg = new Alert(AlertType.ERROR);
			msg.setHeaderText("Le chargement a échoué.");
			msg.setContentText(e.getMessage());
			msg.show();
		}
	}

	public void hub() {
		scene.setRoot(accueil);
		scene.getWindow().sizeToScene();
		scene.getWindow().centerOnScreen();
	}

	public void passerEnModeJeu(Jeu jeu) {
		this.jeu = jeu;
		panneau.setJeu(jeu);
		panneau.actualiserAffichage();
		scene.setRoot(panneau);
		scene.getWindow().sizeToScene();
		scene.getWindow().centerOnScreen();
		if (jeu.getNbCartesSabot()+6*jeu.getNbJoueurs() < 106) {// le jeu a déjà commencé : partie chargée
			if (jeu.getJoueurActif().getMain().size() == 6) { // il n'a pas encore pioché
				piocheEnCours = true;
				panneau.animationPioche();
			} else { // il a déjà pioché
				jouerTour();
			}
		} else {
			tourSuivant();
		}
	}

	private boolean showConfirmation(Jeu jeu) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Charger partie");
		alert.setHeaderText("Voulez vous charger cette partie ?");
		alert.setContentText(jeu.toString());
		Optional<ButtonType> result = alert.showAndWait();
		return (result.isPresent() && result.get() == ButtonType.OK);
	}
}
