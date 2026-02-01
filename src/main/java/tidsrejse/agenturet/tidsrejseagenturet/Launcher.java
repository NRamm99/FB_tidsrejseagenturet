package tidsrejse.agenturet.tidsrejseagenturet;

import config.DatabaseConfig;
import javafx.application.Application;


public class Launcher {
     static void main(String[] args)  {
        DatabaseConfig dc = new DatabaseConfig();

        dc.dbInit();


        Application.launch(RunApplication.class, args);
    }
}
