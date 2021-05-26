package controleur;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import modele.Jeu;

public class EcouteurSouris {
	private Jeu jeu;

	public EcouteurSouris(Jeu jeu) {
		this.jeu = jeu;
	}
	
	public void carteCliquee(MouseEvent event) {
		System.out.println("carte cliquée !");
		System.out.println(GridPane.getColumnIndex((ImageView) event.getTarget())); // de la magie noire ?
	}

}
