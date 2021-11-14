package com.example.main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.HashSet;
import java.util.Random;

public class Main extends Application {
    BorderPane root;
    GridPane mainGrid;

    public static Entity[][] entityTable = new Entity[9][9]; //Główna tablica
    public static Player player; //Awatar
    public static HashSet<Enemy> enemies = new HashSet<>(); //Zbiór wrogów
    void redrawGrid(){ //Rysowanie planszy
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Rectangle emptyBox = new Rectangle();
                emptyBox.setHeight(32);
                emptyBox.setWidth(32);
                emptyBox.setFill(new ImagePattern(entityTable[j][i].getSprite()));
                mainGrid.add(emptyBox,j,i);
            }
        }
    }
    @Override
    public void start(Stage stage) throws Exception {
        root = new BorderPane();
        mainGrid = new GridPane();
        mainGrid.setHgap(5);
        mainGrid.setVgap(5);
        for (int i = 0; i < entityTable[0].length; i++) { //Wypełnienie tabeli pustymi obiektami TODO Entity
            for (int j = 0; j < entityTable[0].length; j++) {
                entityTable[i][j] = new Entity();
            }
        }
        player = new Player(new Image("file:res/img/player.png"), 100);

        Random random = new Random();
        int newEnemyX,newEnemyY;
        //Zabezpieczenie przed spawnem wroga na pozycji awatara
        do{
            newEnemyX = random.nextInt(9);
        } while(newEnemyX == player.getX());
        do{
            newEnemyY = random.nextInt(9);
        } while(newEnemyY == player.getY());
        enemies.add(new Enemy("chłop", new Image("file:res/img/enemy.png"), 10, 3, 5, newEnemyX, newEnemyY));

        redrawGrid();

        root.setCenter(mainGrid);
        mainGrid.setAlignment(Pos.CENTER);

        Scene mainScene = new Scene(root, 640, 480);

        mainScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.UP) || keyEvent.getCode().equals(KeyCode.NUMPAD8)){
                player.moveUp();
            }
            else if(keyEvent.getCode().equals(KeyCode.DOWN) || keyEvent.getCode().equals(KeyCode.NUMPAD2)){
                player.moveDown();
            }
            else if(keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.NUMPAD4)){
                player.moveLeft();
            }
            else if(keyEvent.getCode().equals(KeyCode.RIGHT) || keyEvent.getCode().equals(KeyCode.NUMPAD6)){
                player.moveRight();
            }
            else if(keyEvent.getCode().equals(KeyCode.HOME) || keyEvent.getCode().equals(KeyCode.NUMPAD7)){
                player.moveUpLeft();
            }
            else if(keyEvent.getCode().equals(KeyCode.PAGE_UP) || keyEvent.getCode().equals(KeyCode.NUMPAD9)){
                player.moveUpRight();
            }
            else if(keyEvent.getCode().equals(KeyCode.END) || keyEvent.getCode().equals(KeyCode.NUMPAD1)){
                player.moveDownLeft();
            }
            else if(keyEvent.getCode().equals(KeyCode.PAGE_DOWN) || keyEvent.getCode().equals(KeyCode.NUMPAD3)){
                player.moveDownRight();
            }
            redrawGrid();
        });

        mainScene.setFill(Color.grayRgb(0));
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.setTitle("Waow");
        stage.setScene(mainScene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
