package mille_bornes;

import com.google.gson.JsonObject;

public interface Sauvegardable {
  /** Stocke le contenu de this dans un objet JSON
   * @return l'objet JSON contenant la sauvegarde
   */
  JsonObject sauvegarde();
}
