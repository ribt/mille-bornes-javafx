package vue;

import java.io.IOException;

import controleur.Controleur;
import controleur.EcouteurMenu;
import controleur.EcouteurMenuDebut;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modele.Jeu;
import modele.joueurs.Humain;

public class PanneauDebut {
	Pane centre;
	BorderPane cadre;
	EcouteurMenuDebut ecouteur;
	EcouteurMenu ecouteurMenuBar;
	
	public PanneauDebut(Stage stage) {
		cadre = new BorderPane();
		
		FXMLLoader loader = new FXMLLoader(PanneauDebut.class.getResource("menuDebut.fxml"));
		this.ecouteur = loader.getController();
		try {
			centre = loader.load();
			ecouteur = loader.getController();
			cadre.setCenter(centre);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FXMLLoader loaderMenuBar = new FXMLLoader(PanneauDeJeu.class.getResource("menu.fxml"));
			MenuBar menus = loaderMenuBar.load();
			ecouteurMenuBar = loaderMenuBar.getController();
			cadre.setTop(new VBox(10, menus));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Jeu fauxJeu = new Jeu(new Humain("Joueur 1"), new Humain("Joueur 2"));
		fauxJeu.prepareJeu();
		
		ecouteurMenuBar.setControleur(new Controleur(fauxJeu, new PanneauDeJeu(fauxJeu, stage)));
		
	}
	
	public Pane getCentre() {
		return centre;
	}
	
	public EcouteurMenuDebut getEcouteur() {
		return ecouteur;
	}
	
	public EcouteurMenu getEcouteurMenuBar() {
		return ecouteurMenuBar;
	}

	public BorderPane getCadre() {
		return cadre;
	}
}
