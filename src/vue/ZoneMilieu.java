package vue;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import modele.Jeu;
import modele.cartes.Carte;

public class ZoneMilieu extends GridPane {
	private Jeu jeu;
	private ImageView pioche;
	private ImageView defausse;
	private Label nbCartes;
	private Image vide = new Image(Carte.class.getResource("/images/vide.jpg").toString());
	
	public ZoneMilieu(Jeu jeu) {
		this.jeu = jeu;
		this.pioche = new ImageView();
		this.defausse = new ImageView();
		
		setAlignment(Pos.CENTER);
		setPrefSize(200, 100);
	     // never size the gridpane larger than its preferred size:
	     setMaxSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
		
		Label lab1 = new Label("Pioche");
		setConstraints(lab1, 0, 0);
		Label lab2 = new Label("Défausse");
		setConstraints(lab2, 1, 0);
		
		pioche.setFitHeight(180);
        pioche.setPreserveRatio(true);
        pioche.setSmooth(true);
        pioche.setCache(true);
        setConstraints(pioche, 0, 1);

		defausse.setFitHeight(180);
		defausse.setPreserveRatio(true);
		defausse.setSmooth(true);
        defausse.setCache(true);
        setConstraints(defausse, 1, 1);
        
        this.nbCartes = new Label("xx cartes");
        setConstraints(nbCartes, 0, 2);
		
		getChildren().addAll(lab1, lab2, pioche, defausse, nbCartes);   	 
		
		for (Node n: getChildren()) {
			setHalignment(n, HPos.CENTER);
			setValignment(n, VPos.CENTER);
		}
	}
	
	public void actualiserAffichage() {
		if (jeu.getNbCartesSabot() > 0)
			pioche.setImage(jeu.regardePioche().getImage());
		if (jeu.regardeDefausse() != null)
			defausse.setImage(jeu.regardeDefausse().getImage());
	}

}
