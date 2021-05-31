package application;

import javafx.application.Application;
import javafx.stage.Stage;
import modele.Jeu;
import modele.Joueur;
import modele.joueurs.Gentil;
import modele.joueurs.Humain;
import vue.PanneauDeJeu;
import vue.PanneauDebut;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		PanneauDebut panneauDebut = new PanneauDebut(stage);
		BorderPane mainPane = panneauDebut.getCadre();
		panneauDebut.getEcouteur().setStage(stage);
		stage.setScene(new Scene(mainPane));
		stage.setResizable(false);
		stage.setTitle("1000 bornes");
		stage.show();
		
		stage.sizeToScene();
	}	
}
