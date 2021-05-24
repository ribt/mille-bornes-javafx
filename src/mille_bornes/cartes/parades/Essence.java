package mille_bornes.cartes.parades;

import mille_bornes.cartes.Attaque;
import mille_bornes.cartes.Parade;

/**
 * Décrit la carte Essence. (6x dans le jeu)
 * Elle contre les Pannes d'Essence
 */
public class Essence extends Parade {
  public Essence() {
    super("Essence");
  }

  @Override
  public boolean contre(Attaque carte) {
    return carte.estContreeParEssence();
  }

}
