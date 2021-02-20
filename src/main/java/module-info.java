module com.graith.rpa {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;

    opens com.graith.rpa to javafx.fxml;
    exports com.graith.rpa;
}