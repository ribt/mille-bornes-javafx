package vue;

import javafx.animation.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import modele.cartes.Carte;

public class PremierPlan extends Pane {
	private final ImageView cadre;

	public PremierPlan() {
		setPickOnBounds(false); // transmet l'évnènement souris s'il a lieu sur les parties vides du panneau
		
		cadre = new ImageView();
		cadre.setFitHeight(120);
		cadre.setPreserveRatio(true);
		cadre.setImage(new Image(Carte.class.getResource("/images/Dos.jpg").toString()));
	}
	
	public void animation(Point2D debut, Point2D fin) {
		if (getChildren().contains(cadre))
			return;
		getChildren().add(cadre);
		cadre.setX(debut.getX());
		cadre.setY(debut.getY());
		cadre.setLayoutX(0);
		cadre.setLayoutY(0);
		Timeline line = new Timeline();
		line.getKeyFrames().addAll(
		    new KeyFrame(Duration.seconds(1.5), new KeyValue(cadre.layoutXProperty(), fin.getX()-debut.getX(), Interpolator.LINEAR)),
		    new KeyFrame(Duration.seconds(1.5), new KeyValue(cadre.layoutYProperty(), fin.getY()-debut.getY(), Interpolator.LINEAR)));
		line.setCycleCount(1);
		line.play();
		line.setOnFinished(event -> getChildren().remove(cadre));
	}
}