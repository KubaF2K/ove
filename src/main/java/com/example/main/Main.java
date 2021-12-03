package com.example.main;

import com.example.main.editor.Editor;
import com.example.main.game.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        Label title = new Label("Gra");
        Button btnGame = new Button("Gra");
        btnGame.setOnAction(actionEvent -> {
            Scene gameScene = Game.getScene();
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setTitle("Waow");
            stage.setScene(gameScene);
        });
        Button btnEditor = new Button("Edytor");
        btnEditor.setOnAction(actionEvent -> {
            Scene editorScene = Editor.getScene();
            stage.setTitle("Edytor");
            stage.setScene(editorScene);
        });
        Button btnExit = new Button("Wyjście");
        btnExit.setOnAction(actionEvent -> Platform.exit());
        root.getChildren().addAll(title, btnGame, btnEditor, btnExit);
        Scene scene = new Scene(root, 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
