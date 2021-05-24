package vue;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import modele.Jeu;

public class ZoneDeJeu extends Canvas {
	private Jeu jeu;
	GraphicsContext gc;

	public ZoneDeJeu(Jeu jeu) {
		super(1000, 300);
		this.jeu = jeu;
		
		gc = getGraphicsContext2D();
		 
		gc.setFill(Color.BLUE);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}

}
