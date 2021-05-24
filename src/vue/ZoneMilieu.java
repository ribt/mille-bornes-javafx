package vue;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import modele.Jeu;

public class ZoneMilieu extends HBox {
	private Jeu jeu;
	private ImageView pioche;
	private ImageView defausse;
	
	public ZoneMilieu(Jeu jeu) {
		super(10);
		this.jeu = jeu;
		this.pioche = new ImageView();
		this.defausse = new ImageView();
		
		pioche.setFitHeight(180);
        pioche.setPreserveRatio(true);
        pioche.setSmooth(true);
        pioche.setCache(true);

		defausse.setFitHeight(180);
		defausse.setPreserveRatio(true);
		defausse.setSmooth(true);
        defausse.setCache(true);
		
		getChildren().addAll(pioche, defausse);
	}
	
	public void actualiserAffichage() {
		if (jeu.getNbCartesSabot() > 0)
			pioche.setImage(jeu.regardePioche().getImage());
		if (jeu.regardeDefausse() != null)
			defausse.setImage(jeu.regardeDefausse().getImage());
	}

}
