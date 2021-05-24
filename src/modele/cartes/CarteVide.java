package modele.cartes;

import modele.EtatJoueur;
import modele.Jeu;

public class CarteVide extends Carte {
	public static final CarteVide unique = new CarteVide();

	public CarteVide() {
		super("carte nulle", Categorie.Borne);
	}

	@Override
	public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
	}

}
