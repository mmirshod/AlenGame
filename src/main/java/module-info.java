module com.sunnydevs.alengame {
    requires javafx.controls;
    requires javafx.fxml;
        requires javafx.web;
            
        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
            requires net.synedra.validatorfx;
            requires org.kordamp.ikonli.javafx;
            requires org.kordamp.bootstrapfx.core;
            requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.sunnydevs.alengame to javafx.fxml;
    exports com.sunnydevs.alengame;
    exports com.sunnydevs.alengame.db;
    opens com.sunnydevs.alengame.db to javafx.fxml;
}