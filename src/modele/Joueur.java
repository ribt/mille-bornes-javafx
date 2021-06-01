package modele;

import com.google.gson.JsonObject;

import modele.cartes.Attaque;
import modele.cartes.Bataille;
import modele.cartes.Botte;
import modele.cartes.Carte;
import modele.joueurs.Gentil;
import modele.joueurs.Humain;
import modele.joueurs.Mechant;

import java.util.*;

/**
 * Modélise un joueur de mille-bornes
 */
public abstract class Joueur implements Sauvegardable {
  /** Le nom du joueur */
  public final String nom;
  /** L'état du joueur : ses kilomètres, sa main, ses bottes exposées et sa pile de bataille */
  private final EtatJoueur etat;
  /** Le joueur suivant, dans l'ordre de jeu */
  private Joueur prochainJoueur;

  public Joueur(JsonObject save) {
    nom = save.getAsJsonPrimitive("nom").getAsString();
    etat = new EtatJoueur(save.getAsJsonObject("état"), this);
  }

  public static Joueur restaure(JsonObject sauvegarde) {
    switch (sauvegarde.get("type").getAsCharacter()) {
      case 'G' : return new Gentil(sauvegarde);
      case 'M' : return new Mechant(sauvegarde);
      case 'H' : return new Humain(sauvegarde);
      default:
        throw new IllegalStateException(String.format("Le type %s de joueur est inconnu !", sauvegarde.get("type")));
    }
  }

  @Override
  public JsonObject sauvegarde() {
    JsonObject res = new JsonObject();
    res.addProperty("nom", nom);
    res.add("état", etat.sauvegarde());
    return res;
  }

  /**
   * Crée un joueur.
   * @param nom son nom
   */
  public Joueur(String nom) {
    this.nom = nom;
    etat = new EtatJoueur(this);
  }

  /** Récupère le prochain joueur */
  public Joueur getProchainJoueur() {
    return prochainJoueur;
  }

  /** Règle le prochain joueur : définit la position autour de la table... */
  public void setProchainJoueur(Joueur joueur) {
    prochainJoueur = joueur;
  }

  /** Retourne une copie non modifiable de la main */
  public List<Carte> getMain() {
    return etat.getMain();
  }

  /** Retourne le nombre de kilomètres parcourus par le joueur */
  public int getKm() {
    return etat.getKm();
  }

  /** Teste si la vitesse est actuellement limitée. */
  public boolean getLimiteVitesse() {
    return etat.getLimiteVitesse();
  }

  /**
   * Ajoute une carte à la main du joueur.
   * @param carte la carte à ajouter
   * @throws IllegalStateException si la main contient déjà plus de 6 cartes
   */
  public void prendCarte(Carte carte) throws IllegalStateException {
    etat.prendCarte(carte);
  }

  /**
   * Joue la carte spécifiée.
   * Si c'est une attaque, inclut la sélection d'un adversaire.
   * @param jeu le jeu (et ses joueurs)
   * @param numero l'index de la carte dans la main (de 0 à 6)
   * @throws IllegalStateException si la carte n'est pas jouable
   */
  public void joueCarte(Jeu jeu, int numero) throws IllegalStateException {
    etat.joueCarte(jeu, numero);
  }

  /**
   * Défausse la carte spéficiée
   * @param jeu le jeu (et sa défausse)
   * @param numero l'index de la carte dans la main (de 0 à 6)
   */
  public void defausseCarte(Jeu jeu, int numero) {
    etat.defausseCarte(jeu, numero);
  }

  /**
   * Applique une attaque à ce joueur.
   * Résoud le coup-fourré si possible.
   * @param jeu le jeu
   * @param carte l'attaque à apliquer
   * @throws IllegalStateException si l'attque n'est pas applicable
   */
  public void attaque(Jeu jeu, Attaque carte) throws IllegalStateException {
    etat.attaque(jeu, carte);
  }

  /**
   * Joue la carte spécifiée.
   * Si c'est une attaque, inclut la sélection d'un adversaire.
   * @param jeu le jeu (et ses joueurs)
   * @param numero l'index de la carte dans la main (de 0 à 6)
   * @param adversaire le joueur a attaquer le cas échéant
   * @throws IllegalStateException si la carte n'est pas jouable
   */
  public void joueCarte(Jeu jeu, int numero, Joueur adversaire) throws IllegalStateException {
    etat.joueCarte(jeu, numero, adversaire);
  }

  /**
   * Consulte la carte au sommet de la pile de bataille. (la seule visible)
   * @return la carte au sommet de la pile de bataille, ou null si elle est vide.
   */
  public Bataille getBataille() {
    return etat.getBataille();
  }

  /**
   * Teste et décrit les raisons pour lesquelles le joueur est incapable d'avancer.
   * @return un texte explicatif, ou null si le joueur peut avancer
   */
  public String ditPourquoiPeutPasAvancer() {
    return etat.ditPourquoiPeutPasAvancer();
  }

  /**
   * Décrit le joueur.
   * @return le nom et l'état interne du joueur sur la même ligne.
   */
  @Override
  public String toString() {
    return String.format("%s : %s", nom, etat.toString());
  }

  /** Retourne la liste des bottes jouées
   * @see Collections#unmodifiableList(List)
   * @return une copie non modifiable des bottes du joueur
   */
  public List<Botte> getBottes() {
    return etat.getBottes();
  }

} // class Joueur
