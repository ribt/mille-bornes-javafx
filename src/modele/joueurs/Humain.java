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

  /**
   * Choisit la carte à jouer.
   * Attention aux exceptions : {@link InputMismatchException} et {@link NoSuchElementException}
   * @return un entier entre -7 et +7 permettant de défausser (&lt;0) ou de jouer (&gt;0) la carte correspondante
   */
  public int choisitCarte() {
//    var main = getMain();
//    do {
//      try {
//        System.out.print("N° de la carte à jouer (-x pour défausser la carte N° x) :");
//        int choix = input.nextInt();
//        if (choix > main.size() || choix < -main.size()) {
//          System.err.printf("Vous n'avez que %d cartes en main !\n", main.size());
//        } else
//          return choix;
//      } catch (InputMismatchException ex) {
//        input.nextLine();
//      } catch (NoSuchElementException ex) {
//        ex.printStackTrace();
//        System.exit(1);
//      } catch (Throwable t) {
//        System.err.println(t.getMessage());
//      }
//    } while (true);
	  return 0;
  }

  /**
   * Choisit l'adversaire à attaquer
   * @param carte l'attaque qui sera portée sur cet adversaire
   * @return le {@link Joueur} choisi
   * @throws IllegalStateException si le joueur décide d'annuler son attaque
   */
  public Joueur choisitAdversaire(Attaque carte) throws  IllegalStateException {
//    System.out.printf("Qui voulez-vous attaquer avec %s ?\n0 - Annuler\n", carte.toString());
//    Joueur suivant = getProchainJoueur();
//    int no = 0;
//    ArrayList<Joueur> liste = new ArrayList<>();
//    while (suivant != this) {
//      ++no;
//      System.out.printf("%d - %s\n", no, suivant.toString());
//      liste.add(suivant);
//      suivant = suivant.getProchainJoueur();
//    }
//    do {
//      try {
//        System.out.print("N° du joueur à attaquer :");
//        int choix = input.nextInt();
//        if (choix > no || choix < 0) {
//          System.err.printf("Il n'y a que %d autres joueurs !\n", no);
//          continue;
//        }
//        if (choix > 0)
//          return liste.get(choix - 1);
//        else throw new IllegalStateException("annulé");
//      } catch (InputMismatchException ex) {
//        input.nextLine();
//      } catch (NoSuchElementException ex) {
//        ex.printStackTrace();
//        System.exit(1);
//      }
//    } while (true);
	  return null;
  } // choisitAdversaire(carte)
}
