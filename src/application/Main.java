package application;

import javafx.application.Application;
import javafx.stage.Stage;
import modele.Jeu;
import modele.Joueur;
import modele.joueurs.Gentil;
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
		PanneauDeJeu panneau = new PanneauDeJeu(jeu, stage);
		Scene scene = new Scene(panneau);
		panneau.getControleur().setScene(scene);
		Joueur j1 = new Humain("Pierre");
		Joueur j2 = new Humain("Paul");
		Joueur j3 = new Gentil("Bot");
		 
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle("1000 bornes");
		stage.setResizable(false);
		stage.show();
		
		jeu.ajouteJoueurs(j1, j2, j3);
		jeu.prepareJeu();
		jeu.activeProchainJoueurEtTireCarte();
		
		panneau.actualiserAffichage();
		stage.sizeToScene();
	}	
}
