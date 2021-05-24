module millebornesGUI {
	requires javafx.controls;
	requires com.google.gson;
	requires javafx.graphics;
	
	opens application to javafx.graphics, javafx.fxml;
}
