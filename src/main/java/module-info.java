module com.aerocopias.controledeartes {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.aerocopias.controledeartes to javafx.fxml;
    exports com.aerocopias.controledeartes;
    exports com.aerocopias.controledeartes.controller;
    opens com.aerocopias.controledeartes.controller to javafx.fxml;
    exports com.aerocopias.controledeartes.model;
    opens com.aerocopias.controledeartes.model to javafx.fxml;
}