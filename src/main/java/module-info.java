module tidsrejse.agenturet.tidsrejseagenturet {
    requires javafx.controls;
    requires javafx.fxml;


    opens tidsrejse.agenturet.tidsrejseagenturet to javafx.fxml;
    exports tidsrejse.agenturet.tidsrejseagenturet;
}