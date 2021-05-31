package controleur;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Jeu;
import modele.Joueur;
import modele.joueurs.Gentil;
import modele.joueurs.Humain;
import vue.PanneauDeJeu;

public class EcouteurMenuDebut {
	
	private Stage stage;
	private ObservableList<String> difficulteBot = FXCollections
			.observableArrayList("Joueur", "Bot Facile");

	@FXML
	private TextField nomJoueur1;

	@FXML
	private TextField nomJoueur2;

	@FXML
	private TextField nomJoueur3;

	@FXML
	private TextField nomJoueur4;

	@FXML
	private ChoiceBox<String> botJoueur1;

	@FXML
	private ChoiceBox<String> botJoueur2;

	@FXML
	private ChoiceBox<String> botJoueur3;

	@FXML
	private ChoiceBox<String> botJoueur4;

	@FXML
	private void initialize() {
		botJoueur1.setItems(difficulteBot);
		botJoueur1.setValue("Joueur");
		botJoueur2.setItems(difficulteBot);
		botJoueur2.setValue("Joueur");
		botJoueur3.setItems(difficulteBot);
		botJoueur3.setValue("Joueur");
		botJoueur4.setItems(difficulteBot);
		botJoueur4.setValue("Joueur");
	}

	@FXML
	void ParametrerLaGame(ActionEvent event) {
		if(gameEstJouable()) {
			LancerLaGame();
		}else {
			Alert errorAlert = new Alert(AlertType.ERROR);
			errorAlert.setHeaderText("Vous n'êtes pas assez !");
			errorAlert.setContentText("Il faut être au moins deux joueurs ou bots pour lancer une partie.");
			errorAlert.showAndWait();
		}
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	void LancerLaGame() {
		Jeu jeu = new Jeu();

		if(nomJoueur1.getText().trim().length()>0) {
			Joueur j1 = creeLeJoueur(botJoueur1.getValue(), nomJoueur1.getText().trim());
			jeu.ajouteJoueurs(j1);
		}
		if(nomJoueur2.getText().trim().length()>0) {
			Joueur j2 = creeLeJoueur(botJoueur2.getValue(), nomJoueur2.getText().trim());
			jeu.ajouteJoueurs(j2);
		}
		if(nomJoueur3.getText().trim().length()>0) {
			Joueur j3 = creeLeJoueur(botJoueur3.getValue(), nomJoueur3.getText().trim());
			jeu.ajouteJoueurs(j3);
		}
		if(nomJoueur4.getText().trim().length()>0) {
			Joueur j4 = creeLeJoueur(botJoueur1.getValue(), nomJoueur4.getText().trim());
			jeu.ajouteJoueurs(j4);
		}
		jeu.prepareJeu();
		

		PanneauDeJeu panneau = new PanneauDeJeu(jeu, stage);
		Scene scene = new Scene(panneau);
		panneau.getControleur().setScene(scene);

		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle("1000 bornes");
		stage.setResizable(false);
		stage.show();
		panneau.getControleur().tourSuivant();
	}

	private boolean gameEstJouable() {
		int i = 0;
		if(nomJoueur1.getText().trim().length()>0) {
			i++;
		}
		if(nomJoueur2.getText().trim().length()>0) {
			i++;
		}
		if(nomJoueur3.getText().trim().length()>0) {
			i++;
		}
		if(nomJoueur4.getText().trim().length()>0) {
			i++;
		}
		if(i>=2) {
			return true;
		}
		return false;
	}
	private Joueur creeLeJoueur(String etatBot, String nom) {
		switch (etatBot) {
		case "Joueur":
			return new Humain(nom);
		case "Bot Facile":
			return new Gentil(nom);
		}
		return null;

	}
}