package mille_bornes.cartes.attaques;

import mille_bornes.cartes.Attaque;

/**
 * Décrit la carte Crevaison (3x dans le jeu)
 * Elle est contrée par l'Increvable (botte) et par la Roue de Secours
 */
public class Crevaison extends Attaque {
  public Crevaison() {
    super("Crevaison");
  }

  @Override
  public boolean estContreeParIncrevable() {
    return true;
  }

  @Override
  public boolean estContreeParRoueDeSecours() {
    return true;
  }

}
