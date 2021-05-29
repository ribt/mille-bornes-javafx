package vue;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import modele.Joueur;
import modele.cartes.Botte;
import modele.cartes.Carte;

public class ZoneAffichageJoueur extends GridPane {
	protected Label nom;
	private ImageView limiteVitesse;
	private ImageView bataille;
	private Label compteur;
	private Image carteVide = new Image(Carte.class.getResource("/images/CarteVide.jpg").toString());
	private Image carteLimite = new Image(Carte.class.getResource("/images/LimiteVitesse.jpg").toString());
	private Image carteFinLimite = new Image(Carte.class.getResource("/images/FinDeLimite.jpg").toString());
	private Joueur joueur;
	private ImageView[] bottes = new ImageView[4];
	
	public ZoneAffichageJoueur() {
		this.nom = new Label();
		this.limiteVitesse = new ImageView();
		this.bataille = new ImageView();
		this.compteur = new Label();
		
		setAlignment(Pos.CENTER);
		setHgap(5);
		setVgap(5);
		
		setConstraints(nom, 0, 0, 3, 1); // columnIndex, rowIndex, colspan, rowspan
		setHalignment(nom, HPos.CENTER);

		limiteVitesse.setFitHeight(100);
		limiteVitesse.setPreserveRatio(true);
        setConstraints(limiteVitesse, 0, 1);

        bataille.setFitHeight(100);
        bataille.setPreserveRatio(true);
        setConstraints(bataille, 1, 1);
        
        setConstraints(compteur, 2, 1);
        
		getChildren().addAll(nom, limiteVitesse, bataille, compteur);   
		
		for (int i = 0; i < bottes.length; i++) {
			bottes[i] = new ImageView();
			bottes[i].setFitHeight(100);
			bottes[i].setPreserveRatio(true);
	        setConstraints(bottes[i], 3+i, 1);
	        getChildren().add(bottes[i]);
		}
		
	}
	
	public void actualiserAffichage(Joueur joueur) {
		this.joueur = joueur;
		nom.setText(joueur.nom);
		if (joueur.getLimiteVitesse())
			limiteVitesse.setImage(carteLimite);
		else
			limiteVitesse.setImage(carteFinLimite);
			
		if (joueur.getBataille() == null)
			bataille.setImage(carteVide);
		else
			bataille.setImage(joueur.getBataille().getImage());
		
		compteur.setText(joueur.getKm()+" km");
		
		List<Botte> bottesJoueur = joueur.getBottes();
		
		for (int i = 0; i < this.bottes.length; i++) {
			if (i < bottesJoueur.size())
				this.bottes[i].setImage(bottesJoueur.get(i).getImage());
			else
				this.bottes[i].setImage(null);
		}
		
	}
	
	public Joueur getJoueur() {
		return joueur;
	}

}
