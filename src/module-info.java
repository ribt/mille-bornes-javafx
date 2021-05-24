module millebornesGUI {
	requires javafx.controls;
	requires com.google.gson;
	requires javafx.graphics;
	requires javafx.fxml;
	
	opens application to javafx.graphics, javafx.fxml;
	
	exports controleur;
}
