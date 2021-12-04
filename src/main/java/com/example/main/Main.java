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
    private static Scene mainScene;
    private static Stage mainStage;
    @Override
    public void start(Stage stage) {
        mainStage = stage;
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        Label title = new Label("Gra");
        Button btnGame = new Button("Gra");
        btnGame.setOnAction(actionEvent -> {
            Scene gameScene = Game.getScene();
            mainStage.centerOnScreen();
            mainStage.setResizable(false);
            mainStage.setTitle("Waow");
            mainStage.setScene(gameScene);
        });
        Button btnEditor = new Button("Edytor");
        btnEditor.setOnAction(actionEvent -> {
            Scene editorScene = Editor.getScene();
            mainStage.setTitle("Edytor");
            mainStage.setScene(editorScene);
        });
        Button btnExit = new Button("Wyjście");
        btnExit.setOnAction(actionEvent -> Platform.exit());
        root.getChildren().addAll(title, btnGame, btnEditor, btnExit);
        mainScene = new Scene(root, 640, 480);
        mainStage.setScene(mainScene);
        mainStage.show();
    }

    public static void resetScene(){
        mainStage.setScene(mainScene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
