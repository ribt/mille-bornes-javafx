package modele.joueurs;

import com.google.gson.JsonObject;

import modele.Joueur;
import modele.cartes.*;
import modele.cartes.parades.FeuVert;
import modele.cartes.parades.FinDeLimite;

import java.util.List;

/**
 * Une IA "gentille" qui n'attaque jamais...
 */
public class Gentil extends Joueur {

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

	/** Le numéro d'une carte Fin de limite de vitesse*/
	private int finLimiteIdx;
	/** Le numéro d'une carte inutile */
	private int aEcarterIdx;

	public int choisitCarte() {
		List<Carte> main = getMain();
		Bataille bat = getBataille();
		finLimiteIdx = 0;
		aEcarterIdx = 0;
		if (ditPourquoiPeutPasAvancer() == null) {
			Integer borneOuFinLimite = isPeutAvancer(main);
			if (borneOuFinLimite != null) return borneOuFinLimite;
		}

		if (getLimiteVitesse() && finLimiteIdx > 0 && getKm() < 950)
			return finLimiteIdx;

		if (aEcarterIdx < 0)
			return aEcarterIdx;

		Integer botteIdx = isPeutJouerBotte(main);
		if (botteIdx != null) return botteIdx;

		Integer paradeIdx = isPeutJouerParade(main, bat);
		if (paradeIdx != null) return paradeIdx;

		return defausse(main);
	}

	/** Défausse une carte : une attaque ou la plus petite borne */
	private int defausse(List<Carte> main) {
		Borne best = null;
		int bestIdx = -1; // au pire on jette la première carte
		int nb = main.size();
		for (int i = 0; i < nb; ++i) {
			Carte carte = main.get(i);
			if (carte.categorie == Categorie.Attaque)
				return -1 - i;
			if (carte.categorie == Categorie.Borne) {
				Borne borne = (Borne) carte;
				if (best == null || best.km > borne.km) {
					best = borne;
					bestIdx = -1 - i;
				}
			}
		}
		return bestIdx;
	}

	/** Si l'on est attaqué, retourne la parade correspondante si dans la main */
	private Integer isPeutJouerParade(List<Carte> main, Bataille bat) {
		int nb = main.size();
		for (int i = 0; i < nb; ++i) {
			final Carte carte = main.get(i);
			if (carte.categorie == Categorie.Parade) {
				final Parade parade = (Parade) carte;
				if (bat == null && parade instanceof FeuVert)
					return 1 + i;
				if (bat != null && bat.categorie == Categorie.Attaque && parade.contre((Attaque) bat))
					return 1 + i;
			}
		}
		return null;
	}

	/** Si l'on a une botte, joue pour rejouer */
	private Integer isPeutJouerBotte(List<Carte> main) {
		int nb = main.size();
		for (int i = 0; i < nb; ++i) {
			final Carte carte = main.get(i);
			if (carte.categorie == Categorie.Botte)
				return 1 + i;
		}
		return null;
	}

	/** Teste si une borne est intéressante à jouer maintenant */
	private Integer isPeutAvancer(List<Carte> main) {
		int better = 0;
		Borne best = null;
		int bestIdx = 0;
		aEcarterIdx = 0;
		int nb = main.size();
		for (int i = 0; i < nb; ++i) {
			final Carte carte = main.get(i);
			if (carte.categorie == Categorie.Borne) {
				final Borne borne = (Borne) carte;
				if (best == null || best.km < borne.km)
					if (borne.km + getKm() <= 1000) {
						if (!getLimiteVitesse()) {
							best = borne;
							bestIdx = 1 + i;
						} else if (borne.km <= 50) {
							best = borne;
							bestIdx = 1 + i;
						} else {
							better = borne.km;
						}
					} else aEcarterIdx = -1 - i;
			} else if (carte instanceof FinDeLimite) {
				finLimiteIdx = 1 + i;
			}
		}
		if (bestIdx > 0) {
			if (better > 75 && finLimiteIdx > 0)
				return finLimiteIdx;
			else
				return bestIdx;
		}
		return null;
	}

	public Joueur choisitAdversaire(Attaque carte) {
		return getProchainJoueur(); // Jamais utilisée
	}

} //class Stupide
