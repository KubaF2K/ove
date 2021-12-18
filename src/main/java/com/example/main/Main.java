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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
    private static Scene mainScene;
    private static Stage mainStage;
    @Override
    public void start(Stage stage) {
        mainStage = stage;
        VBox root = new VBox(10);
        root.getStyleClass().add("root");
        root.setAlignment(Pos.CENTER_LEFT);
        Label title = new Label("Gra");
        title.getStyleClass().add("title");
        Font.loadFont("file:res/fonts/Minecraft.ttf", 24);

        Button btnGame = new Button("Start");
        btnGame.setOnAction(actionEvent -> {
            Scene gameScene = Game.getScene();
            mainStage.centerOnScreen();
            mainStage.setTitle("Waow");
            mainStage.setScene(gameScene);
            mainStage.setResizable(false);
        });
        Button btnEditor = new Button("Edit");
        btnEditor.setOnAction(actionEvent -> {
            Scene editorScene = Editor.getScene();
            mainStage.setTitle("Edytor");
            mainStage.setScene(editorScene);
        });
        Button btnExit = new Button("Exit");
        btnExit.setOnAction(actionEvent -> Platform.exit());
        root.getChildren().addAll(title, btnGame, btnEditor, btnExit);
        mainScene = new Scene(root, 640, 480);
        mainScene.getStylesheets().add("file:res/styles/main.css");
        btnGame.setMinSize(320,40);

        btnEditor.setMinSize(320,40);

        btnExit.setMinSize(320,40);

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
