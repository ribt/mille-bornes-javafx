package controleur;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import modele.Jeu;
import modele.Joueur;
import vue.Defausse;
import vue.PanneauDeJeu;
import vue.ZoneAffichageJoueur;

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
		
		try {
			if (cible != null) {
				Joueur joueurVise = null;
				if (cible instanceof ZoneAffichageJoueur)
					joueurVise = ((ZoneAffichageJoueur) cible).getJoueur();
				if (cible.getParent() instanceof ZoneAffichageJoueur)
					joueurVise = ((ZoneAffichageJoueur) cible.getParent()).getJoueur();
				
				if (joueurVise != null) {
					if (joueurVise == jeu.getJoueurActif())
						jeu.getJoueurActif().joueCarte(jeu, carteSelectionne);
					else
						jeu.getJoueurActif().joueCarte(jeu, carteSelectionne, joueurVise);
				}
				
				if (cible instanceof Defausse)
					jeu.getJoueurActif().defausseCarte(jeu, carteSelectionne);
			}
		} catch (IllegalStateException e) {
			Alert msg = new Alert(AlertType.ERROR, e.getMessage());
			msg.show();
		}
		this.carteSelectionne = -1;
		panneau.actualiserAffichage();
	}
	

}
