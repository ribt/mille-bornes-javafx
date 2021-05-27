package vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import modele.cartes.Carte;

public class PremierPlan extends Pane {
	private final ImageView cadre;
	private int draggingCardNumber = -1;
	private double offX;
	private double offY;

	public PremierPlan() {
		setPickOnBounds(false); // transmet l'évnènement souris s'il a lieu sur les parties vides du panneau
		
		cadre = new ImageView();
		cadre.setFitHeight(120);
		cadre.setPreserveRatio(true);
		
		getChildren().add(cadre);
	}
	
	public void setDragging(Image img, int number, double offX, double offY) {
		this.offX = offX;
		this.offY = offY;
		this.draggingCardNumber = number;
		cadre.setImage(img);
	}
	
	public void stopDragging() {
		cadre.setImage(null);
	}

	public void sourisBouge(MouseEvent event) {
		cadre.setX(event.getSceneX()+offX);
		cadre.setY(event.getSceneY()+offY);
	}

}
