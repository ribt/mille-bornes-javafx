package vue;

import java.io.IOException;

import controleur.EcouteurMenuDebut;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PanneauDebut {
	Pane cadre;
	EcouteurMenuDebut ecouteur;
	public PanneauDebut(Stage stage) {
		FXMLLoader loader = new FXMLLoader(PanneauDebut.class.getResource("MenuDebut.fxml"));
		System.out.println("On a demandé le controlleur :"+loader.getController());
		this.ecouteur = loader.getController();
		System.out.println("On a créé le controlleur :"+ecouteur);
		try {
			cadre = loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Pane getCadre() {
		return cadre;
	}
	public EcouteurMenuDebut getEcouteurMenuDebut() {
		System.out.println("On le demande :"+ecouteur);
		return ecouteur;
	}
}
