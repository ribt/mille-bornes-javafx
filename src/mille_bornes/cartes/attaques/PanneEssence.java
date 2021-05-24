package mille_bornes.cartes.attaques;

import mille_bornes.cartes.Attaque;

/**
 * Décrit la carte Panne d'Essence (3x dans le jeu)
 * Elle est contrée par la Citerne (botte) et par l'Essence
 */
public class PanneEssence extends Attaque {
  public PanneEssence() {
    super("Panne d'essence");
  }

  @Override
  public boolean estContreeParCiterne() {
    return true;
  }

  @Override
  public boolean estContreeParEssence() {
    return true;
  }

}
