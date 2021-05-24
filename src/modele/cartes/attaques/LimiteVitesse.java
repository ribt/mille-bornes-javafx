package modele.cartes.attaques;

import modele.EtatJoueur;
import modele.Jeu;
import modele.cartes.Attaque;

/**
 * Décrit la carte Limite de Viesse (4x dans le jeu)
 * Elle est contrée par le Véhicule Prioritaire (botte) et par la Fin de Limite de Vitesse
 */
public class LimiteVitesse extends Attaque {

  public LimiteVitesse() {
    super("Limite de Vitesse");
  }

  @Override
  public boolean estContreeParFinDeLimite() {
    return true;
  }

  @Override
  public boolean estContreeParVehiculePrioritaire() {
    return true;
  }

  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
    if (joueur.getLimiteVitesse()) throw new IllegalStateException("La vitesse de ce joueur est déjà limitée !");
    joueur.setLimiteVitesse(true);
  }
}
