package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import java.io.IOException;
import java.net.URL;

public class FxmlLoader {

    public Pane getPage(String fileName) {
        try {
            URL fileUrl = RunApplication.class.getResource("/tidsrejse/agenturet/tidsrejseagenturet/" + fileName + ".fxml");

            if (fileUrl == null) {
                throw new IOException("FXML file can't be found" + fileName);
            }
            return FXMLLoader.load(fileUrl);

        } catch (IOException e) {
            return null;
        }
    }
}


