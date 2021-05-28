package controleur;

import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import modele.Jeu;
import vue.PanneauDeJeu;

public class EcouteurSouris {
	private Jeu jeu;
	private PanneauDeJeu panneau;
	private Scene scene;

	public EcouteurSouris(Jeu jeu, PanneauDeJeu panneau) {
		this.jeu = jeu;
		this.panneau = panneau;
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	
	public void dragging(MouseEvent event) {
		//System.out.println("press");
		scene.setCursor(Cursor.CLOSED_HAND);
	}
	
	public void stopDragging(MouseEvent event) {
		//System.out.println("stop drag");
		scene.setCursor(Cursor.DEFAULT);
	}
	
	public void relacheSurDefausse(MouseDragEvent event) {
		Object src = event.getSource();
		
		System.out.println("src : "+src);
	}

}
