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
	private ImageView pioche;
	private ImageView defausse;
	private Label nbCartes;
	private Image carteDos = new Image(Carte.class.getResource("/images/Dos.jpg").toString());
	private Image carteVide = new Image(Carte.class.getResource("/images/CarteVide.jpg").toString());
	
	public ZoneMilieu() {
		this.pioche = new ImageView();
		this.defausse = new ImageView();
		
		setAlignment(Pos.CENTER);
		
		Label lab1 = new Label("Pioche");
		setConstraints(lab1, 0, 0);
		Label lab2 = new Label("Défausse");
		setConstraints(lab2, 1, 0);
		
		pioche.setFitHeight(120);
        pioche.setPreserveRatio(true);
        pioche.setSmooth(true);
        pioche.setCache(true);
        setConstraints(pioche, 0, 1);

		defausse.setFitHeight(120);
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
	
	public void actualiserAffichage(Jeu jeu) {
		nbCartes.setText(jeu.getNbCartesSabot()+" cartes");
		pioche.setImage(carteDos);
		if (jeu.regardeDefausse() == null)
			defausse.setImage(carteVide);
		else
			defausse.setImage(jeu.regardeDefausse().getImage());
	}
}
