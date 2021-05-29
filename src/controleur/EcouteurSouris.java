package controleur;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
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
	
	public void carteAtrapee(MouseEvent event) {
		scene.setCursor(Cursor.CLOSED_HAND);
		ImageView img = (ImageView) event.getSource();
		img.startDragAndDrop(TransferMode.ANY);
		System.out.println("début du drag");
	}
	
	public void carteRelachee(DragEvent event) {
		scene.setCursor(Cursor.DEFAULT);
		System.out.println("dropped :"+event);
	}
	
	public void dragSurDefausse(MouseEvent event) {
		System.out.println(event);
		//((Node)event.getSource()).startDragAndDrop();
	}
	
	public void relacheSurDefausse(DragEvent event) {
		scene.setCursor(Cursor.DEFAULT);
		System.out.println(event);
	}
}
