module GameCode {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.rmi;
    requires java.datatransfer;
    requires java.desktop;
    requires java.sql;
    requires com.google.gson;
    exports org.project.Gui;
    exports org.project.Controller.Server;
    exports org.project.ClientPack;
    opens org.project.Gui to javafx.fxml, javafx.controls, javafx.graphics;
    opens org.project.Controller.Messages to com.google.gson;
    opens org.project.Controller.Control to com.google.gson;
    opens org.project.Model to com.google.gson;


}