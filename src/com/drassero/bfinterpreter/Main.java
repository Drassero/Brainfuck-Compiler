package com.drassero.bfinterpreter;

import com.drassero.bfinterpreter.controller.RootController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public static final String APP_NAME = "Brainfuck Compiler";
    public static final int BYTES_ARRAY_LENGTH = 30000;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene/RootScene.fxml"));
        AnchorPane root = loader.load();
        RootController controller = loader.getController();
        controller.setMain(this);
        primaryStage.setTitle(Main.APP_NAME);
        primaryStage.setScene(new Scene(root, root.getWidth(), root.getHeight()));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}