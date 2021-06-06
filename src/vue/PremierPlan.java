package vue;

import controleur.Controleur;
import javafx.animation.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PremierPlan extends Pane {
	private final ImageView cadre;
	private Controleur controleur;
	private boolean dragging;

	public PremierPlan(Controleur controleur) {
		this.controleur = controleur;

		setPickOnBounds(false); // transmet l'évnènement souris s'il a lieu sur les parties vides du panneau
		cadre = new ImageView();
		cadre.setFitHeight(120);
		cadre.setPreserveRatio(true);

		dragging = false;
	}

	public void animation(Image img, Point2D debut, Point2D fin) {
		if (getChildren().contains(cadre))
			return;
		getChildren().add(cadre);
		cadre.setImage(img);
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
		line.setOnFinished(event -> {
			cadre.setLayoutX(0);
			cadre.setLayoutY(0);
			getChildren().remove(cadre);
			controleur.finAnimation();
		});
	}

	public void setDragging(Image img, Point2D pos, double offx, double offy) {
		this.dragging = true;
		cadre.setImage(img);
		getChildren().add(cadre);
		Point2D relative = screenToLocal(pos);
		cadre.setX(relative.getX());
		cadre.setY(relative.getY());
		cadre.setLayoutX(offx);
		cadre.setLayoutY(offy);
	}

	public void stopDragging() {
		this.dragging = false;
		cadre.setImage(null);
		getChildren().remove(cadre);
	}

	public void sourisBouge(MouseEvent event) {
		if (!dragging)
			return;
		cadre.setX(event.getX());
		cadre.setY(event.getY());
	}

}