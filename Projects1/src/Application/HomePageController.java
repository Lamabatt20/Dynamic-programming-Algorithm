package Application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.event.ActionEvent;

public class HomePageController {

	// Event Listener on Button.onAction
	@FXML
	public void TButton(ActionEvent event) {
		Parent root;
		try {
			root = FXMLLoader.load(getClass().getResource("FilePage.fxml"));
			Scene s=new Scene(root);
			Stage travel=new Stage();
			travel.setScene(s);
			travel.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
