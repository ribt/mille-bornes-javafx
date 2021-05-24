package modele.cartes;

import modele.EtatJoueur;
import modele.Jeu;

/**
 * Décrit une carte de la pile de bataille.
 * Chaque carte peut contrer ou être contrée par une autre carte.
 */
public abstract class Bataille extends Carte {
  public Bataille(String nom, Categorie categorie) {
    super(nom, categorie);
  }

  /**
   * Teste si cette carte est contrée par un Feu Vert
   */
  public boolean estContreeParFeuVert() {
    return false;
  }

  /**
   * Teste si cette carte est contrée par une Fin de Limite de Vitesse
   */
  public boolean estContreeParFinDeLimite() {
    return false;
  }

  /**
   * Teste si cette carte est contrée par une Essence
   */
  public boolean estContreeParEssence() {
    return false;
  }

  /**
   * Teste si cette carte est contrée par une Roue de Secours
   */
  public boolean estContreeParRoueDeSecours() {
    return false;
  }

  /**
   * Teste si cette carte est contrée par des Réparations
   */
  public boolean estContreeParReparations() {
    return false;
  }

  /**
   * Teste si cette carte est contrée par un Véhicule Prioritaire
   */
  public boolean estContreeParVehiculePrioritaire() {
    return false;
  }

  /**
   * Teste si cette carte est contrée par une Citerne
   */
  public boolean estContreeParCiterne() {
    return false;
  }

  /**
   * Teste si cette carte est contrée par un Increvable
   */
  public boolean estContreeParIncrevable() {
    return false;
  }

  /**
   * Teste si cette carte est contrée par un As du Volant
   */
  public boolean estContreeParAsDuVolant() {
    return false;
  }

  /**
   * Teste si cette carte contre l'attaque spécifiée.
   * @param carte l'attaque à contrer
   */
  public abstract boolean contre(Attaque carte);

  /**
   * Applique les effets de la carte au joueur cible.
   * @param jeu le jeu
   * @param joueur le joueur ciblé
   * @throws IllegalStateException si la carte n'est pas applicable
   */
  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
    joueur.setBataille(this);
  }
}
