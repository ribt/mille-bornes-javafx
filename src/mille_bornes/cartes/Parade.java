package mille_bornes.cartes;

import mille_bornes.EtatJoueur;
import mille_bornes.Jeu;

/**
 * Décrit une carte pour parer une attaque d'un autre joueur.
 * Se pose sur la pile de bataille (Sauf la Fin de Limite de Vitesse)
 * Chaque parade contre une attaque spécifique.
 */
public abstract class Parade extends Bataille {
  public Parade(String nom) {
    super(nom, Categorie.Parade);
  }

  /**
   * Si le joueur soufre de l'attaque parée, recouvre l'attaque et annule ses effets.
   * @param jeu le jeu
   * @param joueur le joueur ciblé
   * @throws IllegalStateException si la carte n'est pas applicable
   */
  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
    Bataille bataille = joueur.getBataille();
    if (bataille != null && bataille.categorie == Categorie.Attaque && contre((Attaque) bataille))
      joueur.setBataille(this);
    else
      throw new IllegalStateException("Il n'y a pas d'attaque contrable !");
  }

}
