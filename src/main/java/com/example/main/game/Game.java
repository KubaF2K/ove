package com.example.main.game;

import com.example.main.DBConnection;
import com.example.main.editor.Editor;
import com.example.main.models.EnemyModel;
import com.example.main.models.ItemModel;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Game {
    private static GridPane mainGrid;
    private static HBox invGrid;
    private static Text hpText;
        private static Text itemText;
        private static Rectangle elementImg;
    public static LogBox logBox;

    public static StackPane mainStack = new StackPane();

    public static Entity[][] entityTable = new Entity[9][9]; //Główna tablica
    public static Player player; //Awatar
    public static int moveCounter;

    private static final List<EnemyModel> enemyList = DBConnection.getEnemies();
    private static final List<ItemModel> itemList = DBConnection.getItems();
    static void redrawGrid(){ //Rysowanie planszy
        mainGrid.getChildren().clear();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                Rectangle box = new Rectangle(32, 32);
                box.setFill(new ImagePattern(entityTable[j][i].getSprite()));
                mainGrid.add(box,j,i);
            }
        }
    }
    static void redrawInv(){ //Rysowanie paska eq
        if(player != null){
            invGrid.getChildren().clear();
            for (int i = 0; i < 10; i++) {
                Rectangle box = new Rectangle(32, 32);
                if (player.getInventory()[i] == null)
                    box.setFill(new ImagePattern(new Image("file:res/img/empty.png")));
                else box.setFill(new ImagePattern(player.getInventory()[i].getSprite()));
                if (i == player.getEquippedItemId()) {
                    box.setStrokeWidth(2);
                    box.setStroke(Color.WHITE);
                }
                invGrid.getChildren().add(box);
            }
        }
    }
    static void updateInfoBox(){
        if(player==null) return;
        hpText.setText("HP: "+player.getHp());
        if(player.getEquippedItem()!=null){
            if(player.getEquippedItem().getType()==Item.Type.Weapon){
                if(player.getEquippedItem().getElement() == null) {
                    itemText.setText(player.getEquippedItem().getName() + "\nObrażenia: " + player.getEquippedItem().getDmgMin() + "-" + player.getEquippedItem().getDmgMax());
                    elementImg.setFill(Color.TRANSPARENT);
                }
                else {
                    itemText.setText(player.getEquippedItem().getName() + "\nObrażenia: " + player.getEquippedItem().getDmgMin() + "-" + player.getEquippedItem().getDmgMax() + "\nElement:");
                    Image elementSprite = new Image(player.getEquippedItem().getElement().getSpriteURL());
                    elementImg.setFill(new ImagePattern(elementSprite));
                }
            }
            else if(player.getEquippedItem().getType()==Item.Type.Heal){
                itemText.setText(player.getEquippedItem().getName()+"\nLeczy "+player.getEquippedItem().getDmgMin()+" punktów zdrowia");
                elementImg.setFill(Color.TRANSPARENT);
            }
        }
        else itemText.setText("");
    }
    static void addEnemies() {
        if(player==null) return;
        Random random = new Random();
        int newEnemyX,newEnemyY;
        //Zabezpieczenie przed spawnem wroga na pozycji awatara
        do{
            newEnemyX = random.nextInt(9);
        } while(newEnemyX == player.getX());
        do{
            newEnemyY = random.nextInt(9);
        } while(newEnemyY == player.getY());
        EnemyModel enemy = enemyList.get(random.nextInt(enemyList.size()));
        new Enemy(enemy, newEnemyX, newEnemyY);
    }

    static void addItem() {
        if(player==null) return;
        Random random = new Random();
        int newItemX,newItemY;
        //Zabezpieczenie przed spawnem przedmiotu na pozycji awatara
        do{
            newItemX = random.nextInt(9);
        } while(newItemX == player.getX());
        do{
            newItemY = random.nextInt(9);
        } while(newItemY == player.getY());
        ItemModel item;
        do {
            item = itemList.get(random.nextInt(itemList.size()));
        } while(player.checkForWeapon(item));
        entityTable[newItemX][newItemY] = new Item(item);
    }

    static void moveEnemies(){
        if(player==null) return;
        List<Integer> moved = new ArrayList<>();
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(entityTable[i][j].getClass().getSimpleName().equals("Enemy") && player != null){
                    Enemy enemy = (Enemy) entityTable[i][j];
                    if(moved.contains(enemy.getId())) continue;
                    if (enemy.getX() < player.getX()) {
                        if(enemy.getY() < player.getY()) enemy.moveDownRight();
                        else if(enemy.getY() > player.getY()) enemy.moveUpRight();
                        else enemy.moveRight();
                    }
                    else if(enemy.getX() > player.getX()){
                        if(enemy.getY() < player.getY()) enemy.moveDownLeft();
                        else if(enemy.getY() > player.getY()) enemy.moveUpLeft();
                        else enemy.moveLeft();
                    }
                    else if(enemy.getY() > player.getY()) enemy.moveUp();
                    else enemy.moveDown();
                    moved.add(enemy.getId());
                }
            }
        }
        moveCounter++;
        if(moveCounter%12 == 0)
            addEnemies();
        if(moveCounter%15 == 0)
            addItem();
    }
    public static Scene getScene()  {
        BorderPane root = new BorderPane();
        mainGrid = new GridPane();
        mainGrid.setHgap(5);
        mainGrid.setVgap(5);
        invGrid = new HBox(5);
        VBox infoBox = new VBox(5);
        logBox = new LogBox(5);
        logBox.setWrapText(false);

        for (int i = 0; i < entityTable[0].length; i++) { //Wypełnienie tabeli pustymi obiektami
            for (int j = 0; j < entityTable[0].length; j++) {
                entityTable[i][j] = new Entity();
            }
        }
        player = new Player(new Image("file:res/img/player.png"), 100);
        Item startWpn = new Item("Miecz", new Image("file:res/img/sword.png"), 5, 10);
        player.pickup(startWpn);
        hpText = new Text("HP: "+player.getHp());
        hpText.setFill(Color.WHITE);
        itemText = new Text(player.getEquippedItem().getName()+"\nObrażenia: "+player.getEquippedItem().getDmgMin()+"-"+player.getEquippedItem().getDmgMax());
        itemText.setFill(Color.WHITE);
        itemText.setWrappingWidth(85);
        elementImg = new Rectangle(32, 32);
        elementImg.setFill(Color.TRANSPARENT);
        infoBox.getChildren().addAll(hpText, itemText, elementImg);

        addEnemies();
        addItem();

        redrawGrid();
        redrawInv();

        root.setCenter(mainGrid);
        root.setBottom(invGrid);
        root.setRight(infoBox);
        root.setTop(logBox);
        mainGrid.setAlignment(Pos.CENTER);
        invGrid.setAlignment(Pos.CENTER);

        mainStack.getChildren().add(root);

        Scene mainScene = new Scene(mainStack, 640, 480);
        mainScene.getStylesheets().add("file:res/styles/game.css");
        AtomicBoolean paused = new AtomicBoolean(false);
        VBox pauseScreen = new VBox();
        Text pauseTextMain = new Text("Paused\n");
        pauseTextMain.setFill(Color.WHITE);
        pauseTextMain.setStyle("-fx-font: 48 Minecraft;");

        Text pauseTextAdd = new Text("Press esc to continue");
        pauseTextAdd.setFill(Color.WHITE);
        pauseTextAdd.setStyle("-fx-font: 24 Minecraft;");

        pauseScreen.setAlignment(Pos.CENTER);
        pauseScreen.getChildren().addAll(pauseTextMain, pauseTextAdd);
        pauseScreen.getStyleClass().add("pause-screen");
        mainScene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode().equals(KeyCode.ESCAPE)){
                paused.set(!paused.get());
                if(paused.get() && player != null) {
                    mainStack.getChildren().add(pauseScreen);
                }
                else {
                    if(player == null)
                        Platform.exit();
                    mainStack.getChildren().remove(pauseScreen);
                }
            } else if(!paused.get() && player != null) {
                if (keyEvent.getCode().equals(KeyCode.UP) || keyEvent.getCode().equals(KeyCode.NUMPAD8)) {
                    player.moveUp();
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.DOWN) || keyEvent.getCode().equals(KeyCode.NUMPAD2)) {
                    player.moveDown();
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.LEFT) || keyEvent.getCode().equals(KeyCode.NUMPAD4)) {
                    player.moveLeft();
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.RIGHT) || keyEvent.getCode().equals(KeyCode.NUMPAD6)) {
                    player.moveRight();
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.HOME) || keyEvent.getCode().equals(KeyCode.NUMPAD7)) {
                    player.moveUpLeft();
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.PAGE_UP) || keyEvent.getCode().equals(KeyCode.NUMPAD9)) {
                    player.moveUpRight();
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.END) || keyEvent.getCode().equals(KeyCode.NUMPAD1)) {
                    player.moveDownLeft();
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.PAGE_DOWN) || keyEvent.getCode().equals(KeyCode.NUMPAD3)) {
                    player.moveDownRight();
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.SPACE)) {
                    moveEnemies();
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT1)) {
                    player.setEquippedItemId(0);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT2)) {
                    player.setEquippedItemId(1);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT3)) {
                    player.setEquippedItemId(2);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT4)) {
                    player.setEquippedItemId(3);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT5)) {
                    player.setEquippedItemId(4);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT6)) {
                    player.setEquippedItemId(5);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT7)) {
                    player.setEquippedItemId(6);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT8)) {
                    player.setEquippedItemId(7);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT9)) {
                    player.setEquippedItemId(8);
                } else if (keyEvent.getCode().equals(KeyCode.DIGIT0)) {
                    player.setEquippedItemId(9);
                } else if (keyEvent.getCode().equals(KeyCode.E)){
                    player.useItem();
                } else if (keyEvent.getCode().equals(KeyCode.Q)){
                    player.dropCurrentItem();
                } else if (keyEvent.getCode().equals(KeyCode.DELETE)){
                    player.deleteCurrentItem();
                }
                redrawGrid();
                redrawInv();
                updateInfoBox();
            }
        });
        root.getStyleClass().add("root");
        return mainScene;
    }

    public static void setGameOver() {
        VBox endScreen = new VBox();
        Text endTextMain = new Text("Death 💀\n");
        endTextMain.setFill(Color.WHITE);
        endTextMain.setStyle("-fx-font: 48 Minecraft;");


        Text endTextAdd = new Text("Press esc to close game");
        endTextAdd.setFill(Color.WHITE);
        endTextAdd.setStyle("-fx-font: 24 Minecraft;");

        endScreen.setAlignment(Pos.CENTER);
        endScreen.getChildren().add(endTextMain);
        endScreen.getChildren().add(endTextAdd);
        endScreen.getStyleClass().add("end-screen");
        mainStack.getChildren().add(endScreen);
    }

    public static String urlChooser(String url) {
        if(Editor.checkUrlValidation(url)) {
            return url;
        }
        else {
            return "file:res/img/placeHolder.png";
        }
    }
}