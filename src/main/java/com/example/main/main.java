package com.example.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

public class main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        final int[] posCharX = {2}; // Ustanowienie pozycji startowej graxcza
        final int[] posCharY = {2};

        GridPane root = new GridPane();
        root.setHgap(5);
        root.setVgap(5);


        for (int i = 0; i < 5; i++) { //Zapelnianie GridPanelu "pustymi" kwadratami
            for(int j = 0; j < 5; j++) {
                Rectangle emptyBox = new Rectangle();
                emptyBox.setWidth(100);
                emptyBox.setHeight(100);
                emptyBox.setFill(Color.grayRgb(100));
                root.add(emptyBox,j,i);
            }
        }

        int posY = ThreadLocalRandom.current().nextInt(0, 4 + 1); // Losowanie pozycji przeciwnika
        int posX = ThreadLocalRandom.current().nextInt(0, 4 + 1);

        Rectangle enemyBox = new Rectangle(); // Tworzenie przeciwnika i wstawianei go na pozycje wylosowane
        enemyBox.setHeight(100);
        enemyBox.setWidth(100);
        enemyBox.setFill(Color.rgb(255,0,0));
        root.add(enemyBox,posX,posY);

        Rectangle mainChar = new Rectangle(); // tworzeie gracza i wstawianie go na pozycje startowa
        mainChar.setHeight(100.0);
        mainChar.setWidth(100.0);
        mainChar.setFill(Color.rgb(255,255,255));

        root.add(mainChar, posCharX[0],posCharY[0]);
        root.setAlignment(Pos.CENTER);
        Scene scene = new Scene(root, 900, 900);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() { //event handler odpowiedzialny za poruszanie sie gracza {do duzej poprawy!!}
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode().equals(KeyCode.W)){
                    Rectangle emptyBox = new Rectangle();
                    emptyBox.setWidth(100);
                    emptyBox.setHeight(100);
                    emptyBox.setFill(Color.grayRgb(100));
                    if(posCharY[0] > 0) {
                        //root.add(emptyBox, posCharX[0], posCharY[0]);
                        posCharY[0]--;
                        root.add(mainChar, posCharX[0], posCharY[0]);
                    }


                }
            }
        });

        stage.centerOnScreen(); // ustawianie stage
        scene.setFill(Color.rgb(0,0,0));
        stage.setTitle("Test run");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}