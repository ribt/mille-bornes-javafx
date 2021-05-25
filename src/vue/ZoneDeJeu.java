package vue;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import modele.Joueur;
import modele.cartes.Carte;

public class ZoneDeJeu extends ZoneAffichageJoueur {
	ImageView[] cartes =  new ImageView[7];
	
	public ZoneDeJeu() {
		super();
		
		//setAlignment(Pos.CENTER);
		setPadding(new Insets(0, 20, 10, 20)); // top, right, bottom, left
		
		setColumnSpan(nom, 7);
		
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
		
		cartes[6].setImage(null); // au minimum le joueur a 6 cartes
		
		for (int i = 0; i < main.size(); i++) {
			cartes[i].setImage(main.get(i).getImage());
		}
	}

}
