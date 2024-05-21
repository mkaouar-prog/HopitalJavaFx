module com.projet.miniprojetfx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.desktop;

    opens com.projet.miniprojetfx to javafx.fxml;
    opens com.projet.miniprojetfx.Data.Model to javafx.base;

    exports com.projet.miniprojetfx;
    //opens com.projet.miniprojetfx to javafx.fxml;
    exports com.projet.miniprojetfx.Controller;
    opens com.projet.miniprojetfx.Controller to javafx.fxml;
}