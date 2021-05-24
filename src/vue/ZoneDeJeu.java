package vue;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ZoneDeJeu extends Canvas {
	GraphicsContext gc;

	public ZoneDeJeu() {
		super(1000, 300);
		
		gc = getGraphicsContext2D();
		 
		gc.setFill(Color.BLUE);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

}
