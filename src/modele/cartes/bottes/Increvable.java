package modele.cartes.bottes;

import modele.EtatJoueur;
import modele.Jeu;
import modele.cartes.Attaque;
import modele.cartes.Bataille;
import modele.cartes.Botte;

/**
 * Décrit la carte Increvable. (Botte, unique dans le jeu)
 * Elle contre les Crevaisons
 */
public final class Increvable extends Botte {
  /** La seule instance du jeu */
  public static final Increvable unique = new Increvable();

  private Increvable() {
    super("Increvable");
  }

  public boolean contre(Attaque carte) {
    return carte.estContreeParIncrevable();
  }

  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) {
    Bataille bataille = joueur.getBataille();
    if (bataille != null && bataille.estContreeParIncrevable())
      joueur.defausseBataille(jeu);
    joueur.addBotte(this);
    jeu.setProchainJoueur(jeu.getJoueurActif());
  }
}
