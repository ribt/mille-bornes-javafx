package modele.cartes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javafx.scene.image.Image;
import modele.EtatJoueur;
import modele.Jeu;
import modele.Sauvegardable;

import java.lang.reflect.InvocationTargetException;
import java.util.Locale;

/**
 * Décrit une carte du jeu
 */
public abstract class Carte implements Sauvegardable {
	/** Le nom  de la carte */
	public final String nom;
	/** La catégorie de la carte */
	public final Categorie categorie;
	
	public final static Image imageVide = new Image(Carte.class.getResource("/images/CarteVide.jpg").toString());
	public final static Image imageDos = new Image(Carte.class.getResource("/images/Dos.jpg").toString());

	private Image image;

	public Carte(String nom, Categorie categorie) {
		this.nom = nom;
		this.categorie = categorie;
		if (!(this instanceof Borne)) {
			this.image = new Image(Carte.class.getResource("/images/"+this.getClass().getSimpleName()+".jpg").toString());
		}
	}

	public static Carte fromSave(JsonElement save) {
		JsonObject obj = save.getAsJsonObject();
		if (obj.has("borne"))
			return new Borne(obj.get("borne").getAsInt());
		String classe = obj.get("carte").getAsString();
		String cat = obj.get("cat").getAsString();
		String pkg = String.format("mille_bornes.cartes.%ss.", cat);
		try {
			Class<?> klass = Class.forName(pkg + classe) ;
			if ("botte".equals(cat))
				return (Carte) klass.getField("unique").get(null);
			else
				return (Carte) klass.getDeclaredConstructor().newInstance();
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		throw new IllegalStateException("Carte inconnue");
	}

	@Override
	public JsonObject sauvegarde() {
		JsonObject res = new JsonObject();
		res.addProperty("cat", categorie.name().toLowerCase(Locale.ROOT));
		res.addProperty("carte", getClass().getSimpleName());
		return res;
	}

	/**
	 * Applique les effets de la carte au joueur ciblé.
	 * @param jeu le jeu
	 * @param joueur le joueur ciblé
	 * @throws IllegalStateException si la carte n'est pas applicable
	 */
	public abstract void appliqueEffet(Jeu jeu, EtatJoueur joueur) throws IllegalStateException;

	/**
	 * Décrit la carte.
	 * @return le nom de la carte
	 */
	@Override
	public String toString() {
		return nom;
	}

	public Image getImage() {
		return image;
	}

} // public class Carte
