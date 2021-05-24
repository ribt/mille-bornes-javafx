package modele.cartes.parades;

import modele.cartes.Attaque;
import modele.cartes.Parade;

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
