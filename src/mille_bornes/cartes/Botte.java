package mille_bornes.cartes;

import mille_bornes.EtatJoueur;
import mille_bornes.Jeu;
import mille_bornes.Sauvegardable;

/**
 * Décrit une carte-botte.
 * Les bottes contrent les attaques de manière durable.
 * Permet de rejouer.
 * Si la botte est posée au moment de l'attaque, c'est un "coup-fourré". Le joueur peut alors jouer immédiatement.
 */
public abstract class Botte extends Carte implements Sauvegardable {
  public Botte(String nom) {
    super(nom, Categorie.Botte);
  }

  /**
   * Teste si cette carte contre l'attaque spécifiée.
   * @param carte l'attaque à contrer
   */
  public abstract boolean contre(Attaque carte);

  /**
   * Applique les effets de la carte au joueur cible, et lui transfère le tour de jeu.
   * @param jeu le jeu
   * @param joueur le joueur ciblé
   */
  @Override
  public abstract void appliqueEffet(Jeu jeu, EtatJoueur joueur);

}
