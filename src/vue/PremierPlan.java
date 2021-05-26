package vue;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PremierPlan extends Pane {
	private final ImageView cadre;
	private int draggingCardNumber = -1;

	public PremierPlan() {
		setPickOnBounds(false); // pour que le click traverse le PremierPlan
		/* Defines how the picking computation is done for this node
		 * when triggered by a MouseEvent or a contains function call.
		 * If pickOnBounds is true, then picking is computed by intersecting
		 * with the bounds of this node, else picking is computed by intersecting
		 * with the geometric shape of this node.
		 * 
		 * C'est ça qui empêche le print de "mouse moved" ??
		 */
		
		cadre = new ImageView();
		cadre.setFitHeight(120);
		cadre.setPreserveRatio(true);
		
		setOnMouseMoved((MouseEvent event) -> {
			System.out.println("mouse moved");
			cadre.setX(event.getSceneX());
			cadre.setY(event.getSceneY());
		});
	}
	
	public void setDragging(Image img, int number) {
		System.out.println("dragging "+number);
		draggingCardNumber = number;
		cadre.setImage(img);
	}

}
