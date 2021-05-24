package modele.cartes.bottes;

import modele.EtatJoueur;
import modele.Jeu;
import modele.cartes.Attaque;
import modele.cartes.Bataille;
import modele.cartes.Botte;

/**
 * Décrit la carte Citerne. (Botte, unique dans le jeu)
 * Elle contre les Pannes d'Essence
 */
public final class Citerne extends Botte {
  /** La seule instance du jeu */
  public static final Citerne unique = new Citerne();

  private Citerne() {
    super("Citerne");
  }

  public boolean contre(Attaque carte) {
    return carte.estContreeParCiterne();
  }

  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) {
    Bataille bataille = joueur.getBataille();
    if (bataille != null && bataille.estContreeParCiterne())
      joueur.defausseBataille(jeu);
    joueur.addBotte(this);
    jeu.setProchainJoueur(jeu.getJoueurActif());
  }
}
