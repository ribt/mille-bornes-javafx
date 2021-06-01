package modele.joueurs;

import java.util.Random;

import com.google.gson.JsonObject;

import modele.Joueur;
import modele.cartes.Attaque;

public abstract class Bot extends Joueur {
	protected Random rand = new Random();
	
	public Bot(String nom) {
		super(nom);
	}

	public Bot(JsonObject save) {
		super(save);
	}
	
	/**
	 * Choisit la carte à jouer. Les cartes sont numérotées de 1 à 7. Un nombre négatif défausse la carte.
	 * @return un entier non nul entre -7 et 7
	 */
	public abstract int choisitCarte();
	
	/**
	 * Choisit l'adversaire à attaquer si la carte choisie est une Attaque.
	 * @param carte L'attaque à appliquer
	 * @return Le joueur choisi.
	 */
	public abstract Joueur choisitAdversaire(Attaque carte);

}
