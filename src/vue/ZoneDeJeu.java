package vue;

import java.util.List;

import controleur.Controleur;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import modele.Joueur;
import modele.cartes.Carte;

public class ZoneDeJeu extends ZoneAffichageJoueur {
	private ImageView[] cartes = new ImageView[7];
	
	public ZoneDeJeu(Controleur controleur) {
		super("bas");
		
		setAlignment(Pos.CENTER);
		setPadding(new Insets(0, 20, 10, 20)); // top, right, bottom, left
		
		setColumnSpan(nom, 7);
		
		for (int i = 0; i < 7; i++) {
			cartes[i] = new ImageView();
			cartes[i].setFitHeight(120);
			cartes[i].setPreserveRatio(true);
			setConstraints(cartes[i], i, 2);
			getChildren().add(cartes[i]);
			
			cartes[i].setOnMousePressed(controleur::carteCliquee);
			cartes[i].setOnMouseReleased(controleur::carteRelachee);
		}
	}
	
	@Override
	public void actualiserAffichage(Joueur joueur) {
		super.actualiserAffichage(joueur);
		
		List<Carte> main = joueur.getMain();
		
		for (int i = 0; i < 7; i++) {
			if (i < main.size())
				cartes[i].setImage(main.get(i).getImage());
			else
				cartes[i].setImage(null);
		}
	}
	
	public Point2D getPositionDernierecarte() {
		Bounds bounds = cartes[6].localToScene(cartes[6].getBoundsInLocal());
		return new Point2D(bounds.getMaxX(), bounds.getMinY());
	}

}
