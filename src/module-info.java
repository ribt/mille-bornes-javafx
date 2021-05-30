module millebornesGUI {
	requires com.google.gson;
	
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
	opens modele;
	opens modele.cartes;
	
	exports controleur;
	exports modele;
	exports modele.cartes;
}
