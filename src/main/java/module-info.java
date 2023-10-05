module com.aerocopias {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;


    opens com.aerocopias.controledeartes to javafx.fxml;
    exports com.aerocopias.controledeartes;
    exports com.aerocopias.controledeartes.controller;
    exports com.aerocopias.controledeartes.model;
    opens com.aerocopias.controledeartes.model to javafx.fxml;
    opens com.aerocopias.controledeartes.controller;
    opens com.aerocopias.controledeartes.autentificacao.login.controller;
    exports com.aerocopias.controledeartes.autentificacao.login.controller;
    opens com.aerocopias.controledeartes.autentificacao.login.model;
    exports com.aerocopias.controledeartes.autentificacao.login.model;
    exports com.aerocopias.controledeartes.painel.controller;
    opens com.aerocopias.controledeartes.painel.controller;
    exports com.aerocopias.controledeartes.painel.model;
    opens com.aerocopias.controledeartes.painel.model;
    opens com.aerocopias.controledeartes.autentificacao.cadastro.model;
    exports com.aerocopias.controledeartes.autentificacao.cadastro.model;
    opens com.aerocopias.controledeartes.autentificacao.cadastro.controller;
    exports com.aerocopias.controledeartes.autentificacao.cadastro.controller;
    opens com.aerocopias.controledeartes.producaoaroeira.controller;
    exports com.aerocopias.controledeartes.producaoaroeira.controller;
    opens com.aerocopias.controledeartes.producaoaroeira.model;
    exports com.aerocopias.controledeartes.producaoaroeira.model;
    opens com.aerocopias.controledeartes.adm.configuracao.model to javafx.fxml;
    exports com.aerocopias.controledeartes.adm.configuracao.model;
    opens com.aerocopias.controledeartes.adm.configuracao.controller;
    exports com.aerocopias.controledeartes.adm.configuracao.controller;



}
