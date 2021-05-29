package vue;

import java.util.List;

import controleur.EcouteurSouris;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import modele.Joueur;
import modele.cartes.Carte;

public class ZoneDeJeu extends ZoneAffichageJoueur {
	private ImageView[] cartes =  new ImageView[7];
	private final EcouteurSouris controleur;
	
	public ZoneDeJeu(EcouteurSouris controleur) {
		super();
		
		this.controleur = controleur;
		
		setAlignment(Pos.CENTER);
		setPadding(new Insets(0, 20, 10, 20)); // top, right, bottom, left
		
		setColumnSpan(nom, 7);
		
		for (int i = 0; i < 7; i++) {
			cartes[i] = new ImageView();
			cartes[i].setFitHeight(120);
			cartes[i].setPreserveRatio(true);
			setConstraints(cartes[i], i, 2);
			getChildren().add(cartes[i]);
			
			//cartes[i].setOnMouseReleased(controleur::carteRelachee);
//			cartes[i].setOnMouseDragReleased(controleur::relacheSurDefausse);
//			cartes[i].setOnDragDetected(controleur::dragDetected);
//			cartes[i].setOnDragDone(controleur::dragDone);
//			cartes[i].addEventHandler(EventType.ROOT, new EventHandler<Event>() {
//				public void handle(Event e) {
//					System.out.println(e.getEventType());
//					if (e.getEventType() == MouseEvent.DRAG_DETECTED)
//						((ImageView)e.getSource()).startFullDrag();
//				}
//			});
			cartes[i].setOnDragDetected(controleur::carteAtrapee);
			cartes[i].setOnDragDone(controleur::carteRelachee);
		}
	}
	
	@Override
	public void actualiserAffichage(Joueur joueur) {
		super.actualiserAffichage(joueur);
		
		List<Carte> main = joueur.getMain();
		
		cartes[6].setImage(null); // au minimum le joueur a 6 cartes
		
		for (int i = 0; i < main.size(); i++) {
			cartes[i].setImage(main.get(i).getImage());
		}
	}

}
