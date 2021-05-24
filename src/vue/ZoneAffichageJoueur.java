package vue;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ZoneAffichageJoueur extends Canvas {
	GraphicsContext gc;
	
	public ZoneAffichageJoueur() {
		super(300, 150);
		
		gc = getGraphicsContext2D();
		 
		gc.setFill(Color.RED);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

}
