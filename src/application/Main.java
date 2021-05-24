package application;

import javafx.application.Application;
import javafx.stage.Stage;
import modele.Jeu;
import modele.Joueur;
import modele.joueurs.Humain;
import vue.PanneauDeJeu;
import javafx.scene.Scene;

public class Main extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		Jeu jeu = new Jeu();
		PanneauDeJeu panneau = new PanneauDeJeu(jeu);
		Scene scene = new Scene(panneau);
		
		Joueur j1 = new Humain("j1");
		Joueur j2 = new Humain("j2");
		
		jeu.ajouteJoueurs(j1, j2);
		jeu.prepareJeu();
		 
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle("Une fenêtre basique");
		stage.setResizable(false);
		stage.show();
	}	
}