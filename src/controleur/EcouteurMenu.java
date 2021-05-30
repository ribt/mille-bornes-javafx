package controleur;

import javafx.event.ActionEvent;
import vue.APropos;

public class EcouteurMenu {
	
	public void commencer(ActionEvent actionEvent) {
		//TODO
	}
	
	public void sauvgarder(ActionEvent actionEvent) {
		//TODO
	}
	
	public void charger(ActionEvent actionEvent) {
		//TODO
	}
	
	public void quitter(ActionEvent actionEvent) {
		System.exit(0);
	}
	
	public void aPropos(ActionEvent actionEvent) {
		APropos.getAPropos().showAndWait();
	}

}
