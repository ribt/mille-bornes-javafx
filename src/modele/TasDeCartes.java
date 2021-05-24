package modele;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modele.cartes.Borne;
import modele.cartes.Carte;
import modele.cartes.attaques.*;
import modele.cartes.bottes.AsDuVolant;
import modele.cartes.bottes.Citerne;
import modele.cartes.bottes.Increvable;
import modele.cartes.bottes.VehiculePrioritaire;
import modele.cartes.parades.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Implante une pile de cartes, où seule la première est atteignable
 */
public class TasDeCartes implements Sauvegardable{
  /** Les cartes du tas */
  private final List<Carte> cartes;

  public TasDeCartes(JsonObject save) {
    JsonArray array = save.getAsJsonArray("cartes");
    cartes = new LinkedList<>();
    for (JsonElement carte : array)
      cartes.add(Carte.fromSave(carte));
  }

  @Override
  public JsonObject sauvegarde() {
    JsonObject res = new JsonObject();
    JsonArray tab = new JsonArray(cartes.size());
    for (Carte carte : cartes) tab.add(carte.sauvegarde());
    res.add("cartes", tab);
    return res;
  }

  /**
   * Crée un tas de cartes.
   * @param creerLesCartes si vrai, alors ajoute les 110 cartes du Mille-Bornes au tas. Si faux, laisse le tas vide.
   */
  public TasDeCartes(boolean creerLesCartes) {
    cartes = new ArrayList<>();
    if (creerLesCartes)
      creeLesCartes();
  }

  /**
   * Ajoute les 110 cartes du Mille-Bornes au tas de cartes.
   */
  private void creeLesCartes() {
    cartes.addAll(List.of(AsDuVolant.unique, Citerne.unique, Increvable.unique, VehiculePrioritaire.unique));
    for (int i = 0; i < 5; ++i) cartes.add(new FeuRouge());
    for (int i = 0; i < 4; ++i) cartes.add(new LimiteVitesse());
    for (int i = 0; i < 3; ++i) {
      cartes.add(new PanneEssence());
      cartes.add(new Crevaison());
      cartes.add(new Accident());
    }
    for (int i = 0; i < 14; ++i) cartes.add(new FeuVert());
    for (int i = 0; i < 6; ++i) {
      cartes.add(new FinDeLimite());
      cartes.add(new Essence());
      cartes.add(new RoueDeSecours());
      cartes.add(new Reparations());
    }
    for (int i = 0; i < 4; ++i) cartes.add(new Borne(200));
    for (int i = 0; i < 12; ++i) cartes.add(new Borne(100));
    for (int i = 0; i < 10; ++i) {
      cartes.add(new Borne(25));
      cartes.add(new Borne(50));
      cartes.add(new Borne(75));
    }
  }

  /**
   * Mélange le tas de cartes
   * @see Collections#shuffle(List) 
   */
  public void melangeCartes() {
    Collections.shuffle(cartes);
  }

  /** retourne le nombre de cartes présentes dans le tas. */
  public int getNbCartes() {
    return cartes.size();
  }

  /** Teste si le tas est vide. */
  public boolean estVide() {
    return cartes.isEmpty();
  }

  /** Montre la première carte du tas, sans la retirer. */
  public Carte regarde() {
    return cartes.get(cartes.size()-1);
  }

  /** Tire et retourne la première carte du tas. */
  public Carte prend() {
    return cartes.remove(cartes.size()-1);
  }

  /** Ajoute une carte au dessus du tas. (Elle devient la première) */
  public void pose(Carte carte) {
    cartes.add(carte);
  }

} // class TasDeCartes
