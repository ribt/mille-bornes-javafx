package vue;

import java.io.IOException;

import controleur.EcouteurMenuDebut;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class PanneauDebut {
	Pane cadre;
	EcouteurMenuDebut ecouteur;
	public PanneauDebut(Stage stage){
		try {
			FXMLLoader loader = FXMLLoader.load(PanneauDebut.class.getResource("MenuDebut.fxml"));
			ecouteur = loader.getController();
//			cadre = (Pane) FXMLLoader.load(PanneauDebut.class.getResource("MenuDebut.fxml"));		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Pane getCadre() {
		return cadre;
	}
	public EcouteurMenuDebut getEcouteurMenuDebut() {
		return ecouteur;
	}
}
