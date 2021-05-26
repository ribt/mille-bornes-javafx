package controleur;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import modele.Jeu;
import vue.PanneauDeJeu;

public class EcouteurSouris {
	private Jeu jeu;
	private PanneauDeJeu panneau;

	public EcouteurSouris(Jeu jeu, PanneauDeJeu panneau) {
		this.jeu = jeu;
		this.panneau = panneau;
	}
	
	public void carteCliquee(MouseEvent event) {
		ImageView img = (ImageView) event.getTarget();
		panneau.getPremierPlan().setDragging(img.getImage(), GridPane.getColumnIndex(img));
		panneau.actualiserAffichage();
	}

}
