package tidsrejse.agenturet.tidsrejseagenturet;

import config.DatabaseConfig;
import javafx.application.Application;
import systems.TimeTravelSystem;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class Launcher {
    public static void main(String[] args) throws SQLException {
        TimeTravelSystem tts = new TimeTravelSystem();
        DatabaseConfig dc = new DatabaseConfig();

        dc.dbInit();


        Application.launch(HelloApplication.class, args);
    }
}
