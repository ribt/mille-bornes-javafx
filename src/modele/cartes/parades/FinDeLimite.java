package modele.cartes.parades;

import modele.EtatJoueur;
import modele.Jeu;
import modele.cartes.Attaque;
import modele.cartes.Parade;

/**
 * Décrit la carte Fin de Limite de Vitesse. (6x dans le jeu)
 * Elle contre les Limites de Vitesse
 */
public class FinDeLimite extends Parade {
  public FinDeLimite() {
    super("Fin de limite");
  }

  @Override
  public boolean contre(Attaque carte) {
    return carte.estContreeParFinDeLimite();
  }

  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
    if (!joueur.getLimiteVitesse()) throw new IllegalStateException("La vitesse n'est pas limitée !");
    joueur.setLimiteVitesse(false);
  }

}
