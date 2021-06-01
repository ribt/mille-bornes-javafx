package modele.joueurs;

import com.google.gson.JsonObject;

import modele.Joueur;
import modele.cartes.*;
import modele.cartes.bottes.VehiculePrioritaire;
import modele.cartes.parades.FeuVert;
import modele.cartes.parades.FinDeLimite;

import java.util.List;

/**
 * Une IA "gentille" qui avance mais n'attaque jamais...
 */
public class Gentil extends Bot {

	@Override
	public JsonObject sauvegarde() {
		JsonObject sauvegarde = super.sauvegarde();
		sauvegarde.addProperty("type", 'G');
		return sauvegarde;
	}

	/** Restaure une IA sauvegardée */
	public Gentil(JsonObject save) {
		super(save);
		if (save.get("type").getAsCharacter() != 'G')
			throw new IllegalStateException("Ceci n'est pas la sauvegarde d'une IA Gentille !");
	}

	/**
	 * Crée une IA gentille.
	 * @param nom son nom
	 */
	public Gentil(String nom) {
		super(nom);
	}

	@Override
	public int choisitCarte() {
		List<Carte> main = getMain();
		Carte carte;
		Borne borneMax = new Borne(0);
		
		for (int i=0; i < main.size(); i++) {
			carte = main.get(i);
			if (carte instanceof Botte)
				return i+1;
			if (getBataille() == null && !getBottes().contains(VehiculePrioritaire.unique) && carte instanceof FeuVert)
				return i+1;
			if (getLimiteVitesse() && carte instanceof FinDeLimite)
				return i+1;
			if (getBataille() instanceof Attaque && carte instanceof Parade && ((Parade) carte).contre((Attaque) getBataille()))
				return i+1;
			if (carte instanceof Borne) {
				if (((Borne) carte).km > borneMax.km && getKm()+((Borne) carte).km <= 1000 && (!getLimiteVitesse() || ((Borne) carte).km <= 50)) {
					borneMax = (Borne)carte;
				}
			}
		}
		
		if (ditPourquoiPeutPasAvancer() == null && borneMax.km > 0) {
			return main.indexOf(borneMax) + 1;
		}
		
		return -pireCarte();
	}
	
	@Override
	public Joueur choisitAdversaire(Attaque carte) { // jamais appelé
		Joueur choix = this.getProchainJoueur();
		while (choix == this || rand.nextFloat() > 0.5) {
			choix = choix.getProchainJoueur();
		}
		return choix;
	}
	
	private int pireCarte() {
		List<Carte> main = getMain();
		Carte carte;
		
		for (int i=0; i < main.size(); i++) {
			carte = main.get(i);
			if (carte instanceof Borne && getKm()+((Borne) carte).km > 1000)
				return i+1;
			if (carte instanceof Parade || carte instanceof Attaque)
				return i+1;
			if (carte instanceof Borne && ((Borne) carte).km <= 50 && !getLimiteVitesse())
				return i+1;
		}
		
		return 1;
	}

} //class Gentil
