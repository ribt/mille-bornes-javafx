package vue;

import modele.EtatJoueur;
import modele.Jeu;
import modele.cartes.Carte;
import modele.cartes.Categorie;
import modele.cartes.bottes.AsDuVolant;

public class CarteVide extends Carte {
	public static final CarteVide unique = new CarteVide();

	public CarteVide() {
		super("carte nulle", null);
	}

	@Override
	public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
	}

}
