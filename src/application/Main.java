package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage) {
		Canvas canvas = new Canvas(600, 400);
		canvas.setOnMousePressed((MouseEvent event) -> {});
		canvas.setOnMouseDragged((MouseEvent event) -> {});

		Group contenu = new Group(canvas);
		Scene scene = new Scene(contenu);
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle("Une fenêtre basique");
		stage.setResizable(false);
		stage.show();
	}	
}
