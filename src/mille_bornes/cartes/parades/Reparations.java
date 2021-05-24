package mille_bornes.cartes.parades;

import mille_bornes.cartes.Attaque;
import mille_bornes.cartes.Parade;

/**
 * Décrit la carte Réparations. (6x dans le jeu)
 * Elle contre les Accidents
 */
public class Reparations extends Parade {
  public Reparations() {
    super("Réparations");
  }

  @Override
  public boolean contre(Attaque carte) {
    return carte.estContreeParReparations();
  }

}
