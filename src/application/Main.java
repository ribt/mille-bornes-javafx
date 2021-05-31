package application;

import javafx.application.Application;
import javafx.stage.Stage;
import vue.PanneauDebut;
import javafx.scene.Scene;

public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) {
		PanneauDebut panneauDebut = new PanneauDebut(stage);
		panneauDebut.getEcouteur().setStage(stage);
		stage.setScene(new Scene(panneauDebut));
		stage.setResizable(false);
		stage.setTitle("1000 bornes");
		stage.show();
		
		stage.sizeToScene();
	}	
}
