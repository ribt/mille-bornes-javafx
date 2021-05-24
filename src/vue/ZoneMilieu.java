package vue;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ZoneMilieu extends Canvas {
	GraphicsContext gc;
	
	public ZoneMilieu() {
		super(400, 300);
		
		gc = getGraphicsContext2D();
		 
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

}
