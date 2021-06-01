package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import vue.APropos;

public class EcouteurMenu {
	private APropos apropos;
	private Controleur controleur;
	
	@FXML
    private MenuItem boutonEnregistrer;
	
	@FXML
    private MenuItem boutonAccueil;
	
	public EcouteurMenu() {
		this.apropos = new APropos();
	}

	public void setControleur(Controleur controleur) {
		this.controleur = controleur;
	}
	
	@FXML
	public void commencer(ActionEvent actionEvent) {
		controleur.hub();
	}
	
	@FXML
	public void sauvgarder(ActionEvent actionEvent) {
		controleur.sauvegarde();
	}
	
	@FXML
	public void charger(ActionEvent actionEvent) {
		controleur.charge();
	}
	
	@FXML
	public void quitter(ActionEvent actionEvent) {
		System.exit(0);
	}
	
	@FXML
	public void aPropos(ActionEvent actionEvent) {
		apropos.showAndWait();
	}
	
	public void cacherBoutonsAccueil() {
		boutonEnregistrer.setVisible(false);
		boutonAccueil.setVisible(false);
	}

}
