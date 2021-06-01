package controleur;

import javafx.event.ActionEvent;
import vue.APropos;

public class EcouteurMenu {
	private APropos apropos;
	private Controleur controleur;
	
	public EcouteurMenu() {
		this.apropos = new APropos();
	}
	
	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}
	
	public void commencer(ActionEvent actionEvent) {
		controleur.hub();
	}
	
	public void sauvgarder(ActionEvent actionEvent) {
		controleur.sauvegarde();
	}
	
	public void charger(ActionEvent actionEvent) {
		controleur.charge();
	}
	
	public void quitter(ActionEvent actionEvent) {
		System.exit(0);
	}
	
	public void aPropos(ActionEvent actionEvent) {
		apropos.showAndWait();
	}

}
