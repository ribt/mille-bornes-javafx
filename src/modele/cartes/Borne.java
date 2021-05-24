package modele.cartes;

import com.google.gson.JsonObject;

import modele.EtatJoueur;
import modele.Jeu;

/**
 * Décrit une carte kilométrique.
 * Ajoute des kilomètres au compteur du joueur s'il n'est pas immobilisé
 */
public class Borne extends Carte {
  /** Le nombre de kilomètres que cette carte ajoute au compteur */
  public final int km;

  public Borne(int km) {
    super(String.format("%d km", km), Categorie.Borne);
    this.km = km;
  }

  /**
   * Ajoute des kilomètres au compteur du joueur.
   * @param jeu le jeu
   * @param joueur le joueur
   * @throws IllegalStateException si le joueur est immobilisé
   */
  @Override
  public void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException {
    joueur.ajouteKm(km);
  }

  @Override
  public JsonObject sauvegarde() {
    JsonObject res = new JsonObject();
    res.addProperty("borne", km);
    return res;
  }
}
