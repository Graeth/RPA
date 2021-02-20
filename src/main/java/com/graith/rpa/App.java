package com.graith.rpa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.graith.rpa.process.RPAProcess;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    private static final String APP_TITLE = "RPA Process Builder";
    private static final int APP_HEIGHT = 713;
    private static final int APP_WIDTH = 1084;
    
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("title"), APP_WIDTH, APP_HEIGHT);
        stage.setScene(scene);
        stage.setTitle(APP_TITLE);
        stage.show();
        
        
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}