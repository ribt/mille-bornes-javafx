package controleur;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import modele.Jeu;
import vue.Defausse;
import vue.PanneauDeJeu;

public class EcouteurSouris {
	private Jeu jeu;
	private PanneauDeJeu panneau;
	private Scene scene;
	private int carteSelectionne = -1;

	public EcouteurSouris(Jeu jeu, PanneauDeJeu panneau) {
		this.jeu = jeu;
		this.panneau = panneau;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void carteCliquee(MouseEvent event) {
		scene.setCursor(Cursor.CLOSED_HAND);
		this.carteSelectionne = GridPane.getColumnIndex((Node) event.getSource());
	}
	
	public void carteRelachee(MouseEvent event) {
		scene.setCursor(Cursor.DEFAULT);
		Node cible = event.getPickResult().getIntersectedNode();
		
		if (cible != null) {
			if (cible instanceof Defausse)
				jeu.getJoueurActif().defausseCarte(jeu, carteSelectionne);
		}
		
		this.carteSelectionne = -1;
		panneau.actualiserAffichage();
	}
	

}
