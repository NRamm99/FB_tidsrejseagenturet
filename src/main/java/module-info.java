module tidsrejse.agenturet.tidsrejseagenturet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens tidsrejse.agenturet.tidsrejseagenturet to javafx.fxml;
    exports tidsrejse.agenturet.tidsrejseagenturet;
}