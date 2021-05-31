package controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modele.Jeu;
import modele.Joueur;
import modele.joueurs.Gentil;
import modele.joueurs.Humain;
import vue.PanneauDeJeu;

public class EcouteurMenuDebut {
	
	private Jeu jeu;
	private Stage stage;
	
	@FXML
    private TextField namePlayerOne;

    @FXML
    private TextField namePlayerTwo;

    @FXML
    private TextField namePlayerThree;

    @FXML
    private TextField namePlayerFour;

    @FXML
    private CheckBox Joueur1Bot;

    @FXML
    private CheckBox Joueur2Bot;

    @FXML
    private CheckBox Joueur3Bot;

    @FXML
    private CheckBox Joueur4Bot;

    @FXML
    void ParametrerLaGame(ActionEvent event) {
    	
    	System.out.println("Joueur 1 :"+namePlayerOne.getText()+" "+Joueur1Bot.isSelected()+"\nJoueur 2 :"+namePlayerTwo.getText()+" "+Joueur2Bot.isSelected()+"\nJoueur 3 :"+namePlayerThree.getText()+" "+Joueur3Bot.isSelected()+"\nJoueur 4 :"+namePlayerFour.getText()+" "+Joueur4Bot.isSelected());
    }
    
    void LancerLaGame() {
		PanneauDeJeu panneau = new PanneauDeJeu(jeu, stage);
		Scene scene = new Scene(panneau);
		panneau.getControleur().setScene(scene);


		Jeu jeu = new Jeu();
		stage.setScene(scene);
		stage.sizeToScene();
		stage.setTitle("1000 bornes");
		stage.setResizable(false);
		stage.show();

		Joueur j1 = new Humain("Pierre");
		Joueur j2 = new Gentil("Bot 1");
		Joueur j3 = new Gentil("Bot 2");

		jeu.ajouteJoueurs(j1, j2, j3);
		jeu.prepareJeu();
		panneau.getControleur().tourSuivant();
    }

}
