package mille_bornes.cartes.parades;

import mille_bornes.cartes.Attaque;
import mille_bornes.cartes.Parade;

/**
 * Décrit la carte Roue de Secours. (6x dans le jeu)
 * Elle contre les Crevaisons
 */
public class RoueDeSecours extends Parade {
  public RoueDeSecours() {
    super("Roue de Secours");
  }

  @Override
  public boolean contre(Attaque carte) {
    return carte.estContreeParRoueDeSecours();
  }

}
