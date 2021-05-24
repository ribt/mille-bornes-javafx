package mille_bornes.cartes;

import mille_bornes.EtatJoueur;
import mille_bornes.Jeu;

/**
 * Décrit une carte pour attaquer un autre joueur.
 * Se pose sur la pile de bataille (Sauf la Limite de Vitesse)
 * Chaque attaque peut être contrée par une parade ou par un botte.
 */
public abstract class Attaque extends Bataille {
  public Attaque(String nom) {
    super(nom, Categorie.Attaque);
  }

  /**
   * Une attaque ne contre aucune carte
   * @param carte inutile
   * @return toujours faux
   */
  @Override
  public boolean contre(Attaque carte) {
    return false; // Une attaque ne contre aucune carte
  }

  /**
   * Si le joueur cible peut avancer, dépose la carte sur sa pile de bataille.
   * @param jeu le jeu
   * @param joueur le joueur ciblé
   * @throws IllegalStateException si la carte n'est pas applicable
   */
  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
    if (joueur.ditPourquoiPeutPasAvancer() != null) throw new IllegalStateException("Le joueur n'est pas attaquable !");
    joueur.setBataille(this);
  }
}
