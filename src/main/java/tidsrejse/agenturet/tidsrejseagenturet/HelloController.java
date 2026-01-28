package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("This is not suppoesed to be here!");
    }

    // This is better now!
}
