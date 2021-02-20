package com.graith.rpa;

import java.io.IOException;
import javafx.fxml.FXML;

public class TitleController {

    @FXML
    private void switchToEditor() throws IOException {
        App.setRoot("editor");
    }
}
