package mille_bornes.cartes.bottes;

import mille_bornes.EtatJoueur;
import mille_bornes.Jeu;
import mille_bornes.cartes.Attaque;
import mille_bornes.cartes.Bataille;
import mille_bornes.cartes.Botte;

/**
 * Décrit la carte Véhicule Prioritaire. (Botte, unique dans le jeu)
 * Elle contre les Feux Rouges et les Limites de Vitesse et permet de démarrer
 */
public final class VehiculePrioritaire extends Botte {
  /** La seule instance du jeu */
  public static final VehiculePrioritaire unique = new VehiculePrioritaire();

  private VehiculePrioritaire() {
    super("Véhicule Prioritaire");
  }

  public boolean contre(Attaque carte) {
    return carte.estContreeParVehiculePrioritaire();
  }

  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) {
    if (joueur.getLimiteVitesse())
      joueur.setLimiteVitesse(false);
    Bataille bataille = joueur.getBataille();
    if (bataille != null && bataille.estContreeParVehiculePrioritaire())
      joueur.defausseBataille(jeu);
    joueur.addBotte(this);
    jeu.setProchainJoueur(jeu.getJoueurActif());
  }
}
