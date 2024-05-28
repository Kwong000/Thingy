module com.example.thingy {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.thingy to javafx.fxml;
    exports com.example.thingy;
    exports;
    opens to
}