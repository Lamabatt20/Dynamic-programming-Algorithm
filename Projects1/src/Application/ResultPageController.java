package Application;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ResultPageController {

	public static ResultPageController r;

	@FXML
	public TextField costtext;

	@FXML
	public TextField pathtext;

	@FXML
	public TextArea tabelresult;

	@FXML
	public TextArea textarea;

	public void initialize() {
		r = this;
	}

}
