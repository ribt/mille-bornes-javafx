package vue;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import modele.Jeu;

public class ZoneMilieu extends Canvas {
	GraphicsContext gc;
	private Jeu jeu;
	
	public ZoneMilieu(Jeu jeu) {
		super(400, 300);
		this.jeu = jeu;
		
		gc = getGraphicsContext2D();
		 
		gc.setFill(Color.GREEN);
		gc.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void afficherPioche() {
		System.out.println(jeu.regardePioche());
		gc.drawImage(jeu.regardePioche().getImage(), 0, 0);
	}

}
