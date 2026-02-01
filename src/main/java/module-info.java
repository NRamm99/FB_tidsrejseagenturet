module tidsrejse.agenturet.tidsrejseagenturet {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.base;


    opens tidsrejse.agenturet.tidsrejseagenturet to javafx.fxml;
    exports tidsrejse.agenturet.tidsrejseagenturet;
}