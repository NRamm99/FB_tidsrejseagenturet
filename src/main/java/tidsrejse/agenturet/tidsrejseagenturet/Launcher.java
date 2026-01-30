package tidsrejse.agenturet.tidsrejseagenturet;

import javafx.application.Application;
import repositories.CustomerRepository;
import systems.TimeTravelSystem;

import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) throws SQLException {
        TimeTravelSystem tts = new TimeTravelSystem();
        Application.launch(HelloApplication.class, args);
    }
}
