package modele;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modele.cartes.Carte;
import java.util.*;

/**
 * Modélise le jeu de Mille-Bornes.
 * https://www.jeuxdujardin.fr/files/regles/rdj-mb-classique-poche-et-pegbo---ok.pdf
 */
public class Jeu implements Sauvegardable {
  /** La liste des joueurs dans leur ordre de jeu */
  private final List<Joueur> joueurs = new ArrayList<>();
  /**
   * Le joueur actif : celui en train de jouer.
   * null si la partie n'est pas lancée.
   */
  private Joueur joueurActif;
  /** Le prochain joueur. Normalement, joueurActif.getProchainJoueur(), sauf botte... */
  private Joueur prochainJoueur;
  /**
   * Le sabot où sont piochées les cartes, face cachée.
   * La partie se termine quand il est vide.
   */
  private TasDeCartes sabot;
  /** La défausse : stocke les cartes éliminées du jeu, face visible. */
  private TasDeCartes defausse;

  @Override
  public JsonObject sauvegarde() {
    JsonObject res = new JsonObject();
    res.add("sabot", sabot.sauvegarde());
    res.add("défausse", defausse.sauvegarde());
    JsonArray tab = new JsonArray();
    Joueur actif = joueurActif;
    do {
      tab.add(actif.sauvegarde());
      actif = actif.getProchainJoueur();
    } while (actif != joueurActif);
    res.add("joueurs", tab);
    return res;
  }

  /**
   * Crée un jeu sans joueur.
   */
  public Jeu() {
  }

  /**
   * Crée un jeu à partir d'une sauvegarde
   */
  public Jeu(JsonObject sauvegarde) {
    sabot = new TasDeCartes(sauvegarde.getAsJsonObject("sabot"));
    defausse = new TasDeCartes(sauvegarde.getAsJsonObject("défausse"));
    for (JsonElement save : sauvegarde.getAsJsonArray("joueurs")) {
      joueurs.add(Joueur.restaure((JsonObject)save));
    }
    int nb = joueurs.size();
    for (int i = 0; i < nb; i++) {
      Joueur joueur = joueurs.get(i);
      joueur.setProchainJoueur(joueurs.get((i+1) % nb));
    }
    joueurActif = joueurs.get(0);
    prochainJoueur = joueurActif.getProchainJoueur();
  }
/*

 */
  /**
   * Crée un jeu avec les joueurs spécifiés.
   * @param joueurs le ou les joueurs à ajouter à la partie
   */
  public Jeu(Joueur... joueurs) {
    this.joueurs.addAll(Arrays.asList(joueurs));
  }

  /**
   * Ajoute les joueurs spécifiés à la partie.
   * @param autreJoueurs le ou les joueurs à ajouter
   * @throws IllegalStateException si la partie a déjà commencé
   */
  public void ajouteJoueurs(Joueur... autreJoueurs) throws IllegalStateException {
    if (joueurActif != null)
      throw new IllegalStateException("La partie est déjà commencée !");
    this.joueurs.addAll(Arrays.asList(autreJoueurs));
  }

  /**
   * Lance le jeu.
   * <ul>
   * <li> randomise l'ordre de jeu des joueurs </li>
   * <li> crée un sabot avec 110 cartes </li>
   * <li> mélange les cartes aléatoirement </li>
   * <li> crée une défausse vide </li>
   * <li> distribue 6 cartes à chaque joueur, une par une, à tour de rôle </li>
   * <li> choisit le prochain joueur </li>
   * </ul>
   */
  public void prepareJeu() {
    if (joueurActif == null) {
      Collections.shuffle(joueurs);
      sabot = new TasDeCartes(true);
      sabot.melangeCartes();
      defausse = new TasDeCartes(false);
      Joueur prev = joueurs.get(joueurs.size() - 1);
      for (Joueur joueur : joueurs) {
        prev.setProchainJoueur(joueur);
        prev = joueur;
      }

      for (int i = 0; i < 6; ++i) {
        for (Joueur joueur : joueurs)
          joueur.prendCarte(sabot.prend());
      }

      joueurActif = prochainJoueur = joueurs.get(0);
    }
  }

  /**
   * Décrit le jeu et indique le joueur actif.
   * Par exemple :<pre>
   *  lui :   50 km (50) [....], Feu Vert
   * &gt;toi :  125 km (50) [A...], Feu Rouge
   *  moi :  200 km  [....], Feu Vert
   *  nous :   25 km  [....], Feu Vert
   * Pioche :  67 cartes; Défausse : vide
   * </pre>
   * @return une chaîne contenant une ligne par joueur, indiquant son nom et son jeu visible, et une ligne indiquant le nombre de carte restant au sabot et la carte visible de la défausse.
   */
  @Override
  public String toString() {
    StringJoiner joiner = new StringJoiner("\n");
    for (Joueur joueur : joueurs) {
      String s = String.format("%s%s", (joueur == joueurActif) ? ">" : " ", joueur.toString());
      joiner.add(s);
    }
    joiner.add(String.format("Pioche : %3d cartes; Défausse : %s", sabot.getNbCartes(), defausse.estVide()?"vide":defausse.regarde().toString()));
    return joiner.toString();
  }

  /**
   * Active le joueur suivant de la partie et lui fait tirer une carte si la partie n'est pas terminée.
   */
  public void activeProchainJoueurEtTireCarte() {
    if (joueurActif.getKm() < 1000 && ! sabot.estVide()) {
      joueurActif = prochainJoueur;
      prochainJoueur = joueurActif.getProchainJoueur();
      joueurActif.prendCarte(sabot.prend());
    }
  }

  /**
   * Teste si la partie est finie.
   */
  public boolean estPartieFinie() {
    return getGagnant().get(0).getKm() == 1000 || (sabot.estVide() && joueurActif.getMain().size()<7);
  }

  /**
   * Modifie le prochain joueur. Utile uniquement pour les bottes, qui font rejouer celui qui expose la botte.
   * @param prochainJoueurActif le prochain joueur à jouer
   */
  public void setProchainJoueur(Joueur prochainJoueurActif) {
    prochainJoueur = prochainJoueurActif;
  }

  /**
   * Retourne le joueur actif : celui qui joue en ce moment.
   */
  public Joueur getJoueurActif() {
    return joueurActif;
  }

  /**
   * Retourne le gagnant de la partie : le premier à atteindre 1000km.
   * Si le sabot est épuisé, celui qui a le plus de kilomètres parcourus est le gagnant.
   * @return La liste des joueurs ayant le plus de kilomètres. ( les gagnants ex-aequo )
   */
  public List<Joueur> getGagnant() {
    ArrayList<Joueur> liste = new ArrayList<>(joueurs);
    liste.sort(Comparator.comparingInt(Joueur::getKm).reversed());
    Joueur best = liste.get(0);
    LinkedList<Joueur> resultat = new LinkedList<>();
    for (Joueur joueur : liste) {
      if (joueur.getKm() < best.getKm())
        break;
      resultat.add(joueur);
    }
    return resultat;
  }

  /**
   * Tire une carte du sabot.
   * @return la carte à ajouter à sa main
   */
  public Carte pioche() {
    return sabot.prend();
  }

  /**
   * Empile une carte sur la défausse.
   * @param carte la carte à défausser
   */
  public void defausse(Carte carte) {
    defausse.pose(carte);
  }

  /**
   * Retourne le nombre de cartes restant au sabot.
   */
  public int getNbCartesSabot() {
    return sabot.getNbCartes();
  }

  /** Consulte la carte au somment de la défausse, sans l'en retirer.
   * @return null si la défausse est vide, la carte du dessus sinon
   */
  public Carte regardeDefausse() {
    if (defausse == null || defausse.estVide())
      return null;
    return defausse.regarde();
  }
  
  /** Consulte la carte au somment de la pioche, sans l'en retirer.
   * @return null si la pioche est vide, la carte du dessus sinon
   */
  public Carte regardePioche() {
    if (sabot == null || sabot.estVide())
      return null;
    return sabot.regarde();
  }

  public int getNbJoueurs() {
	  return joueurs.size();
  }
} // class Jeu
