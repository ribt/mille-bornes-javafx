package mille_bornes.cartes.attaques;

import mille_bornes.cartes.Attaque;

/**
 * Décrit la carte Feu Rouge (5x dans le jeu)
 * Elle est contrée par le Véhicule Prioritaire (botte) et par le Feu Vert
 */
public class FeuRouge extends Attaque {

  public FeuRouge() {
    super("Feu Rouge");
  }

  @Override
  public boolean estContreeParFeuVert() {
    return true;
  }

  @Override
  public boolean estContreeParVehiculePrioritaire() {
    return true;
  }

}
