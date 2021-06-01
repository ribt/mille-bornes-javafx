module millebornesGUI {
	requires com.google.gson;
	
	requires transitive javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens modele;
	opens modele.cartes;
	opens controleur;
	
	
	exports controleur;
	exports modele;
	exports modele.cartes;
	exports vue;
}
