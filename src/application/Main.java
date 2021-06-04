package application;

import controleur.Controleur;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		Controleur controleur = new Controleur();
		Scene scene = new Scene(controleur.getPanneauDebut());
		controleur.setScene(scene);
		stage.setScene(scene);
		stage.setResizable(false);
		stage.setTitle("1000 bornes");
		stage.show();
		controleur.hub();
	}	
}
