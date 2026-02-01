package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RunApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RunApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),830,445);
        stage.setTitle("Tidsrejseagenturet");
        stage.setScene(scene);
        stage.show();
    }
}
