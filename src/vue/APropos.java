package vue;

import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class APropos {

	public static Alert getAPropos() {

		Alert alert= new Alert(Alert.AlertType.INFORMATION);
		Label header = new Label("Mille bornes");
		header.setFont(new Font("Ubuntu", 48));
		header.setAlignment(Pos.CENTER);
		header.setStyle("-fx-text-fill: red; -fx-font-weight: bolder;");
		alert.getDialogPane().setHeader(header);
		Label dequi = new Label("Créé par Ribt et Suake pour un projet à l'IUT Grand Ouest Normandie\nPour commencer une partie, allez dans Partie>Commencer ou chargez en une !\nBon jeu !");
		dequi.setStyle("-fx-font-size: 15; -fx-test-aligment:center;");
		HBox content = new HBox(dequi);
		alert.getDialogPane().setContent(content);
		return alert;
	}
}
