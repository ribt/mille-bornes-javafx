package vue;

import controleur.Controleur;
import javafx.geometry.Bounds;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import modele.Jeu;
import modele.cartes.Carte;

public class ZoneMilieu extends GridPane {
	private ImageView pioche;
	private ImageView defausse;
	private Label nbCartes;
	private Image carteDos = new Image(Carte.class.getResource("/images/Dos.jpg").toString());
	private Image carteVide = new Image(Carte.class.getResource("/images/CarteVide.jpg").toString());
	
	public ZoneMilieu(Controleur controleur) {
		this.pioche = new ImageView();
		this.defausse = new Defausse();
		
		setAlignment(Pos.CENTER);
		setPadding(new Insets(50, 20, 20, 20)); // top, right, bottom, left
		setHgap(5);
		
		Label lab1 = new Label("Pioche");
		setConstraints(lab1, 0, 0);
		Label lab2 = new Label("Défausse");
		setConstraints(lab2, 1, 0);
		
		pioche.setFitHeight(120);
        pioche.setPreserveRatio(true);
        pioche.setSmooth(true); // cosmétique
        pioche.setCache(true); // optimisation
        setConstraints(pioche, 0, 1);

		defausse.setFitHeight(120);
		defausse.setPreserveRatio(true);
		defausse.setSmooth(true);  // cosmétique
        defausse.setCache(true); // optimisation
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
		pioche.setImage(carteDos); // TODO : dans le constructeur
		if (jeu.regardeDefausse() == null)
			defausse.setImage(carteVide);
		else
			defausse.setImage(jeu.regardeDefausse().getImage());
	}
	
	public Point2D getPositionPioche() {
		Bounds bounds = pioche.localToScene(pioche.getBoundsInLocal());
		return new Point2D(bounds.getMinX(), bounds.getMinY());
	}
	
	public Point2D getPositionDefausse() {
		Bounds bounds = defausse.localToScene(defausse.getBoundsInLocal());
		return new Point2D(bounds.getMinX(), bounds.getMinY());
	}
}
