module musacode {
    requires javafx.controls;
    requires javafx.fxml;
    requires spire.doc.free;
    requires java.base;
    requires java.desktop;

    opens musacode to javafx.fxml;
    exports musacode;
}
