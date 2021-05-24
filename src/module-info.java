module millebornesGUI {
	requires javafx.controls;
	requires com.google.gson;
	
	opens application to javafx.graphics, javafx.fxml;
}
