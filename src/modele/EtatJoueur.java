package modele;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import modele.cartes.*;
import modele.cartes.bottes.AsDuVolant;
import modele.cartes.bottes.Citerne;
import modele.cartes.bottes.Increvable;
import modele.cartes.bottes.VehiculePrioritaire;

import java.util.*;

/**
 * Implante l'état d'un joueur : son avancée dans la partie
 */
public class EtatJoueur implements Sauvegardable{
	/** Le joueur concerné */
	private final Joueur joueur;
	/** La pile de bataille.
	 * On empile ici toutes les attaques et leurs parades, à l'exception des (fin de) limites de vitesse.
	 * Seule la dernière carte est accessible.
	 */
	private final Stack<Bataille> pileBataille;
	/** La main du joueur : les 6 ou 7 cartes qu'il peut jouer */
	private final List<Carte> main;
	/** Les bottes que le joueur a exposées */
	private final List<Botte> bottes;
	/** Le compteur de kilomètres. Ne peut dépasser 1000. */
	private int km;
	/** Indique si la vitesse du joueur est limitée.
	 * Si vrai, le joueur ne pourra pas avancer plus de 50 kilomètres par tour de jeu.
	 */
	private boolean limiteVitesse;

	public EtatJoueur(JsonObject save, Joueur possesseur) {
		joueur = possesseur;
		JsonArray array = save.getAsJsonArray("bataille");
		pileBataille = new Stack<>();
		for (JsonElement carte : array)
			pileBataille.add(pileBataille.size(), (Bataille) Carte.fromSave(carte));
		JsonArray array2 = save.getAsJsonArray("main");
		main = new LinkedList<>();
		for (JsonElement carte : array2)
			main.add(Carte.fromSave(carte));
		JsonArray array3 = save.getAsJsonArray("bottes");
		bottes = new LinkedList<>();
		for (JsonElement carte : array3)
			bottes.add((Botte)Carte.fromSave(carte));
		km = save.get("km").getAsInt();
		limiteVitesse = save.get("limiteVitesse").getAsBoolean();
	}

	@Override
	public JsonObject sauvegarde() {
		JsonObject res = new JsonObject();
		JsonArray array = new JsonArray();
		for (Bataille bataille : pileBataille) array.add(bataille.sauvegarde());
		res.add("bataille", array);
		JsonArray array2 = new JsonArray();
		for (Carte carte : main) array2.add(carte.sauvegarde());
		res.add("main", array2);
		JsonArray array3 = new JsonArray();
		for (Carte botte : bottes) array3.add(botte.sauvegarde());
		res.add("bottes", array3);
		res.addProperty("km", km);
		res.addProperty("limiteVitesse", limiteVitesse);
		return res;
	}

	/**
	 * Crée un état vide pour un joueur.
	 * @param joueur le joueur
	 */
	EtatJoueur(Joueur joueur) {
		this.joueur = joueur;
		km = 0;
		limiteVitesse = false;
		pileBataille = new Stack<>();
		main = new LinkedList<>();
		bottes = new ArrayList<>();
	}

	/** Retourne le nombre de kilomètres parcourus. */
	public int getKm() {
		return km;
	}

	/**
	 * Ajoute des kilomètres parcourus au compteur.
	 * @param distance le nombre de kilomètres à ajouter
	 * @throws IllegalStateException si le joueur ne peut pas avancer, ou s'il essaye d'avancer plus que la limite.
	 */
	public void ajouteKm(int distance) throws IllegalStateException {
		String msg = ditPourquoiPeutPasAvancer();
		if (msg != null) throw new IllegalStateException(msg);
		if (limiteVitesse && distance > 50) throw new IllegalStateException("Vous êtes limités à 50 !");
		if (this.km + distance > 1000) throw new IllegalStateException("Vous dépasseriez les 1000 bornes !");
		this.km += distance;
	}

	/**
	 * Teste et décrit les raisons pour lesquelles le joueur est incapable d'avancer.
	 * @return un texte explicatif, ou null si le joueur peut avancer
	 */
	public String ditPourquoiPeutPasAvancer() {
		Bataille bataille = getBataille();
		if (bataille == null && !bottes.contains(VehiculePrioritaire.unique))
			return "Vous n'avez pas encore de feu vert !";
		if (bataille != null && bataille.categorie == Categorie.Attaque)
			return String.format("Vous souffrez actuellement de %s", bataille);
		return null;
	}

	/** Teste si la vitesse est actuellement limitée. */
	public boolean getLimiteVitesse() {
		return limiteVitesse;
	}

	/**
	 * Change la limite de vitesse.
	 * @param limiteVitesse si vrai, le joueur ne pourra plus avancer que de 50 km par tour de jeu.
	 */
	public void setLimiteVitesse(boolean limiteVitesse) {
		this.limiteVitesse = limiteVitesse;
	}

	/**
	 * Consulte la carte au sommet de la pile de bataille. (la seule visible)
	 * @return la carte au sommet de la pile de bataille, ou null si elle est vide.
	 */
	public Bataille getBataille() {
		if (pileBataille.empty())
			return null;
		else
			return pileBataille.peek();
	}

	/**
	 * Ajoute la carte spécifiée au sommet de la pile de bataille.
	 * Ne teste pas si le coup est légal !
	 * @param carte la carte à ajouter
	 */
	public void setBataille(Bataille carte) {
		pileBataille.push(carte);
	}

	/**
	 * Retire la carte au sommet de la pile de bataille et la défausse.
	 * @param jeu le jeu (et sa défausse)
	 */
	public void defausseBataille(Jeu jeu) {
		jeu.defausse(pileBataille.pop());
	}

	/**
	 * Retourne la main du joueur.
	 * @see Collections#unmodifiableList(List)
	 * @return une copie non modifiable de la main du joueur
	 */
	public List<Carte> getMain() {
		return Collections.unmodifiableList(main);
	}

	/**
	 * Ajoute une botte sur le jeu.
	 * Les effets de la botte ne sont pas appliqués.
	 * @param carte la botte à jouer
	 */
	public void addBotte(Botte carte) {
		bottes.add(carte);
	}

	/**
	 * Applique une attaque à ce joueur.
	 * Résoud le coup-fourré si possible.
	 * @param jeu le jeu
	 * @param carte l'attaque à apliquer
	 * @throws IllegalStateException si l'attque n'est pas applicable
	 */
	public void attaque(Jeu jeu, Attaque carte) throws IllegalStateException {
		for (Botte botte : bottes)
			if (botte.contre(carte))
				throw new IllegalStateException(String.format("Le joueur est protégé par %s", botte.nom));

		carte.appliqueEffet(jeu, this);

		for (Carte cb : main)
			if (cb.categorie == Categorie.Botte && ((Botte) cb).contre(carte)) {
				System.out.println("*** Coup-fourré ! ***");
				cb.appliqueEffet(jeu, this);
				jeu.defausse(carte);
				jeu.setProchainJoueur(joueur);
				main.remove(cb);
				main.add(jeu.pioche());
				break;
			}
	}

	/**
	 * Décrit l'état du jeu.
	 * Par exemple " 125 km (50) [A...], Feu Rouge"
	 * @return une ligne de texte contenant la distance parcourue, la limite de vitesse en cours, les bottes exposées et la carte au sommet de la pile de bataille.
	 */
	@Override
	public String toString() {
		final Bataille bataille = getBataille();
		return String.format("% 4d km %s[%c%c%c%c], %s",
				km,
				limiteVitesse ? "(50) " : "",
						bottes.contains(AsDuVolant.unique) ? 'A' : '.',
								bottes.contains(Citerne.unique) ? 'C' : '.',
										bottes.contains(Increvable.unique) ? 'I' : '.',
												bottes.contains(VehiculePrioritaire.unique) ? 'V' : '.',
														bataille == null ? "vide" : bataille.toString()
				);
	}

	/**
	 * Ajoute une carte à la main du joueur.
	 * @param carte la carte à ajouter.
	 * @throws IllegalStateException si le joueur a déjà plus de 6 cartes
	 */
	public void prendCarte(Carte carte) throws IllegalStateException {
		if (main.size() > 6) throw new IllegalStateException("Vous avez déjà plus de 6 cartes en main !");
		main.add(carte);
	}

	/**
	 * Défausse la carte spéficiée
	 * @param jeu le jeu (et sa défausse)
	 * @param numero l'index de la carte dans la main (de 0 à 6)
	 */
	public void defausseCarte(Jeu jeu, int numero) {
		jeu.defausse(main.remove(numero));
	}

	/**
	 * Joue la carte spécifiée.
	 * Si c'est une attaque, inclut la sélection d'un adversaire.
	 * @param jeu le jeu (et ses joueurs)
	 * @param numero l'index de la carte dans la main (de 0 à 6)
	 * @throws IllegalStateException si la carte n'est pas jouable
	 */
	public void joueCarte(Jeu jeu, int numero) throws IllegalStateException {
		Carte carte = null;
		try {
			carte = main.remove(numero);
			if (carte.categorie == Categorie.Attaque) {
				throw new IllegalStateException("Une attaque doit être appliquée à un joueur");
			} else {
				carte.appliqueEffet(jeu, this);
			}
		} catch (IllegalStateException ex) {
			main.add(numero, carte);
			throw ex;
		}
	} // joueCarte(...)
	/**
	 * Joue la carte spécifiée.
	 * Si c'est une attaque, inclut la sélection d'un adversaire.
	 * @param jeu le jeu (et ses joueurs)
	 * @param numero l'index de la carte dans la main (de 0 à 6)
	 * @param adversaire le joueur a attaquer le cas échéant
	 * @throws IllegalStateException si la carte n'est pas jouable
	 */
	public void joueCarte(Jeu jeu, int numero, Joueur adversaire) throws IllegalStateException {
		Carte carte = null;
		try {
			carte = main.remove(numero);
			if (carte.categorie == Categorie.Attaque) {
				adversaire.attaque(jeu, (Attaque) carte);
			} else {
				throw new IllegalStateException("Seules les attaques peuvent être appliquées à un autre joueur");
			}
		} catch (IllegalStateException ex) {
			main.add(numero, carte);
			throw ex;
		}
	} // joueCarte(...)

	/** Retourne la liste des bottes jouées
	 * @see Collections#unmodifiableList(List)
	 * @return une copie non modifiable des bottes du joueur
	 */
	public List<Botte> getBottes() {
		return Collections.unmodifiableList(bottes);
	}

} // public class EtatJoueur
