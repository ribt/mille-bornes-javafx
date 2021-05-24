package vue;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import modele.Jeu;
import modele.Joueur;
import modele.cartes.Carte;

public class ZoneDeJeu extends ZoneAffichageJoueur {
	ImageView[] cartes =  new ImageView[7];
	
	public ZoneDeJeu() {
		super();
		
		for (int i = 0; i < 7; i++) {
			cartes[i] = new ImageView();
			cartes[i].setFitHeight(120);
			cartes[i].setPreserveRatio(true);
			setConstraints(cartes[i], i, 2);
			getChildren().add(cartes[i]);
		}
	}
	
	@Override
	public void actualiserAffichage(Joueur joueur) {
		super.actualiserAffichage(joueur);
		
		List<Carte> main = joueur.getMain();
		
		for (int i = 0; i < main.size(); i++) {
			cartes[i].setImage(main.get(i).getImage());
		}
	}

}
