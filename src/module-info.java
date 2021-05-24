module millebornesGUI {
	requires com.google.gson;
	
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	
	exports controleur;
}
