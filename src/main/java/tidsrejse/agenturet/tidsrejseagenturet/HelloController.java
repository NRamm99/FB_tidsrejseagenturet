package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("This is a test! And its awesome!");
    }

    // This is supposed to stay
}
