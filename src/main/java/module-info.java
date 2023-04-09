module br.com.ulkiorra.registroplacas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens br.com.ulkiorra.registroplacas to javafx.fxml;
    exports br.com.ulkiorra.registroplacas;
    exports br.com.ulkiorra.registroplacas.controller;
    exports br.com.ulkiorra.registroplacas.util;
    exports br.com.ulkiorra.registroplacas.listeners;
    opens br.com.ulkiorra.registroplacas.controller to javafx.fxml;
}