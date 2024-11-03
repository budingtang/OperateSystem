module slime.os {
    requires javafx.controls;
    requires javafx.fxml;
    opens slime.os to javafx.fxml;
    exports slime.os;
    exports slime.os.controller;
    opens slime.os.controller to javafx.fxml;

}