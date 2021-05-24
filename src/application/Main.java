package application;

import javafx.application.Application;
import javafx.stage.Stage;
import vue.PanneauDeJeu;
import javafx.scene.Scene;

public class Main extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		PanneauDeJeu panneau = new PanneauDeJeu();
		Scene scene = new Scene(panneau);
		 
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle("Une fenêtre basique");
		stage.setResizable(false);
		stage.show();
	}	
}
