package mille_bornes.cartes.parades;

import mille_bornes.EtatJoueur;
import mille_bornes.Jeu;
import mille_bornes.cartes.Attaque;
import mille_bornes.cartes.Parade;

/**
 * Décrit la carte Feu Vert. (14x dans le jeu)
 * Elle contre les Feux Rouges et est nécessaire pour démarrer
 */
public class FeuVert extends Parade {
  public FeuVert() {
    super("Feu Vert");
  }

  @Override
  public boolean contre(Attaque carte) {
    return carte.estContreeParFeuVert();
  }

  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
    if (joueur.getBataille() == null)
      joueur.setBataille(this);
    else
      super.appliqueEffet(jeu, joueur);
  }

}
