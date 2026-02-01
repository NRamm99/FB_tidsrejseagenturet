package tidsrejse.agenturet.tidsrejseagenturet;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FrontPageController {

    @FXML
    private ImageView frontImage;

    @FXML
    private void initialize() {
        frontImage.setImage(
                new Image(getClass().getResourceAsStream("/images/TidsrejseBillede.png"))
        );
    }
}



