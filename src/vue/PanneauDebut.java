package vue;

import java.io.IOException;

import controleur.Controleur;
import controleur.EcouteurMenu;
import controleur.EcouteurMenuDebut;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.Jeu;
import modele.joueurs.Humain;

public class PanneauDebut extends BorderPane {
	private Pane centre;
	private EcouteurMenuDebut ecouteur;
	private EcouteurMenu ecouteurMenuBar;
	
	public PanneauDebut(Stage stage) {
		FXMLLoader loader = new FXMLLoader(PanneauDebut.class.getResource("menuDebut.fxml"));
		try {
			this.centre = loader.load();
			this.ecouteur = loader.getController();
			setCenter(centre);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			FXMLLoader loaderMenuBar = new FXMLLoader(PanneauDeJeu.class.getResource("menu.fxml"));
			MenuBar menus = loaderMenuBar.load();
			this.ecouteurMenuBar = loaderMenuBar.getController();
			setTop(menus);
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
}
