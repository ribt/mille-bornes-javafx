package vue;

import java.io.IOException;

import controleur.Controleur;
import controleur.EcouteurMenu;
import controleur.EcouteurMenuDebut;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class PanneauDebut extends BorderPane {
	private Pane centre;
	private EcouteurMenuDebut ecouteur;
	private EcouteurMenu ecouteurMenuBar;
	
	public PanneauDebut(Controleur controleur) {
		try {
			FXMLLoader loader = new FXMLLoader(PanneauDebut.class.getResource("menuDebut.fxml"));
			this.centre = loader.load();
			this.ecouteur = loader.getController();
			setCenter(centre);
			ecouteur.setControleur(controleur);
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
		
		ecouteurMenuBar.setControleur(controleur);
		
	}
}
