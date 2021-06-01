package controleur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import modele.Jeu;
import modele.Joueur;
import modele.joueurs.Gentil;
import modele.joueurs.Humain;
import modele.joueurs.Mechant;

public class EcouteurMenuDebut {

	private Controleur controleur;
	private ObservableList<String> difficulteBot = FXCollections.observableArrayList("Humain", "Bot Gentil", "Bot Méchant");

	@FXML
	private TextField nomJoueur1;

	@FXML
	private TextField nomJoueur2;

	@FXML
	private TextField nomJoueur3;

	@FXML
	private TextField nomJoueur4;
	
	private TextField[] nomJoueurs;

	@FXML
	private ChoiceBox<String> botJoueur1;

	@FXML
	private ChoiceBox<String> botJoueur2;

	@FXML
	private ChoiceBox<String> botJoueur3;

	@FXML
	private ChoiceBox<String> botJoueur4;
	
	private ChoiceBox<String>[] botJoueurs;

	@FXML
	private void initialize() {
		this.nomJoueurs = new TextField[]{nomJoueur1, nomJoueur2, nomJoueur3, nomJoueur4};
		this.botJoueurs = new ChoiceBox[]{botJoueur1, botJoueur2, botJoueur3, botJoueur4};
		
		for (ChoiceBox<String> choice: botJoueurs) {
			choice.setItems(difficulteBot);
			choice.setValue("Humain");
		}
	}
	
	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}

	@FXML
	private void parametrerLaGame(ActionEvent event) {
		if (gameEstJouable()) {
			lancerLaGame();
		} else {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Vous n'êtes pas assez !");
			errorAlert.setContentText("Il faut être au moins deux joueurs ou bots pour lancer une partie.");
			errorAlert.showAndWait();
		}
	}

	void lancerLaGame() {
		Jeu jeu = new Jeu();
		for (int i = 0; i < 4; i++) {
			if (nomJoueurs[i].getText().trim().length() > 0) {
				jeu.ajouteJoueurs(creeLeJoueur(botJoueurs[i].getValue(), nomJoueurs[i].getText().trim()));
			}
		}
		jeu.prepareJeu();
		controleur.passerEnModeJeu(jeu);
	}

	private boolean gameEstJouable() {
		int nbJoueurs = 0;
		for (TextField text : nomJoueurs) {
			if (text.getText().trim().length() > 0) {
				nbJoueurs++;
			}
		}
		return nbJoueurs >= 2;
	}
	
	private Joueur creeLeJoueur(String etatBot, String nom) {
		switch (etatBot) {
			case "Humain":
				return new Humain(nom);
			case "Bot Gentil":
				return new Gentil(nom);
			case "Bot Méchant":
				return new Mechant(nom);
		}
		return null;

	}
}