package modele.joueurs;

import com.google.gson.JsonObject;

import modele.Joueur;
import modele.cartes.Attaque;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Humain extends Joueur {
  /** Un {@link java.util.Scanner} pour les entrées au clavier */
  //private final Scanner input;

  /**
   * Crée un joueur.
   * @param nom son nom
   */
  public Humain(String nom) {
    super(nom);
    //input = new Scanner(System.in);
  }

  public Humain(JsonObject save) {
    super(save);
    if (save.get("type").getAsCharacter() != 'H')
      throw new IllegalStateException("Ceci n'est pas la sauvegarde d'un Humain !");
    //input = new Scanner(System.in);
  }

  @Override
  public JsonObject sauvegarde() {
    JsonObject sauvegarde = super.sauvegarde();
    sauvegarde.addProperty("type", 'H');
    return sauvegarde;
  }
}
