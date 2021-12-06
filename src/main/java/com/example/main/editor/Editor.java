package com.example.main.editor;

import com.example.main.DBConnection;
import com.example.main.Main;
import com.example.main.game.Item;
import com.example.main.models.EnemyModel;
import com.example.main.models.ItemModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Editor {
    private static ObservableList<EnemyModel> enemies;
    private static ObservableList<ItemModel> items;
    public static Scene getScene(){
        enemies = FXCollections.observableList(DBConnection.getEnemiesFromDb());
        items = FXCollections.observableList(DBConnection.getItemsFromDb());

        BorderPane root = new BorderPane();
        TabPane tabPane = new TabPane();
            TableView<EnemyModel> enemiesTable = new TableView<>(enemies);
            TableView<ItemModel> itemsTable = new TableView<>(items);
        root.setCenter(tabPane);
        Scene scene = new Scene(root, 640, 480);

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(scene.widthProperty());
            Menu mainMenu = new Menu("Menu");
                MenuItem mainMenuMenu = new MenuItem("Wróć do menu głównego");
                    mainMenuMenu.setOnAction(e -> Main.resetScene());
                MenuItem mainMenuExit = new MenuItem("Wyjdź");
                    mainMenuExit.setOnAction(e -> Platform.exit());
            mainMenu.getItems().addAll(mainMenuMenu, mainMenuExit);
            Menu addMenu = new Menu("Dodaj");
                MenuItem addMenuEnemy = new MenuItem("Wróg");
                addMenuEnemy.setOnAction(e -> {
                    VBox sidebar = new VBox(10);
                        HBox nameBox = new HBox(10);
                            Label nameLabel = new Label("Nazwa:");
                            TextField nameText = new TextField();
                        nameBox.getChildren().addAll(nameLabel, nameText);
                        HBox spriteBox = new HBox(10);
                            Label spriteLabel = new Label("URL do grafiki:");
                            TextField spriteText = new TextField();
                        spriteBox.getChildren().addAll(spriteLabel, spriteText);
                        HBox healthBox = new HBox(10);
                            Label healthLabel = new Label("Zdrowie:");
                            TextField healthText = new TextField();
                        healthBox.getChildren().addAll(healthLabel, healthText);
                        HBox dmgBox = new HBox(10);
                            Label dmgLabel = new Label("Obrażenia:");
                            TextField dmgMinText = new TextField();
                            Label dmgLine = new Label("-");
                            TextField dmgMaxText = new TextField();
                        dmgBox.getChildren().addAll(dmgLabel, dmgMinText, dmgLine, dmgMaxText);
                        HBox itemBox = new HBox(10);
                            Label itemLabel = new Label("Upuszczany przedmiot:");
                            ComboBox<ItemModel> itemComboBox = new ComboBox<>(items);
                            itemComboBox.setCellFactory(c -> new ListCell<>() {
                                @Override
                                protected void updateItem(ItemModel s, boolean b) {
                                    super.updateItem(s, b);
                                    if (s != null)
                                        setText(s.getName());
                                    else setText("Brak");
                                }
                            });
                        itemBox.getChildren().addAll(itemLabel, itemComboBox);
                        HBox buttons = new HBox(10);
                            Button btnAdd = new Button("Dodaj");
                                btnAdd.setOnAction(b -> {
                                    EnemyModel enemy = new EnemyModel(nameText.getText(), spriteText.getText(), Integer.parseInt(healthText.getText()), Integer.parseInt(dmgMinText.getText()), Integer.parseInt(dmgMaxText.getText()), itemComboBox.getValue());
                                    DBConnection.addEnemy(enemy);
                                    enemies = FXCollections.observableList(DBConnection.getEnemiesFromDb());
                                    enemiesTable.setItems(enemies);
                                    root.setRight(null);
                                });
                            Button btnCancel = new Button("Anuluj");
                                btnCancel.setOnAction(b -> root.setRight(null));
                        buttons.getChildren().addAll(btnAdd, btnCancel);
                    sidebar.getChildren().addAll(nameBox, spriteBox, healthBox, dmgBox, itemBox, buttons);

                    root.setRight(sidebar);
                });
                MenuItem addMenuWeapon = new MenuItem("Broń");
                addMenuWeapon.setOnAction(a -> {
                    VBox sidebar = new VBox(10);
                        HBox nameBox = new HBox(10);
                            Label nameLabel = new Label("Nazwa:");
                            TextField nameText = new TextField();
                        nameBox.getChildren().addAll(nameLabel, nameText);
                        HBox spriteBox = new HBox(10);
                            Label spriteLabel = new Label("URL do grafiki:");
                            TextField spriteText = new TextField();
                        spriteBox.getChildren().addAll(spriteLabel, spriteText);
                        HBox dmgBox = new HBox(10);
                            Label dmgLabel = new Label("Obrażenia:");
                            TextField dmgMinText = new TextField();
                            Label dmgLine = new Label("-");
                            TextField dmgMaxText = new TextField();
                        dmgBox.getChildren().addAll(dmgLabel, dmgMinText, dmgLine, dmgMaxText);
                        HBox buttons = new HBox(10);
                            Button btnAdd = new Button("Dodaj");
                            btnAdd.setOnAction(b -> {
                                ItemModel item = new ItemModel(nameText.getText(), spriteText.getText(), Integer.parseInt(dmgMinText.getText()), Integer.parseInt(dmgMaxText.getText()));
                                DBConnection.addItem(item);
                                items = FXCollections.observableList(DBConnection.getItemsFromDb());
                                itemsTable.setItems(items);
                                root.setRight(null);
                            });
                            Button btnCancel = new Button("Anuluj");
                            btnCancel.setOnAction(b -> root.setRight(null));
                        buttons.getChildren().addAll(btnAdd, btnCancel);
                    sidebar.getChildren().addAll(nameBox, spriteBox, dmgBox, buttons);

                    root.setRight(sidebar);
                });
                MenuItem addMenuItem = new MenuItem("Przedmiot");
                addMenuItem.setOnAction(e -> {
                    VBox sidebar = new VBox(10);
                        HBox nameBox = new HBox(10);
                            Label nameLabel = new Label("Nazwa:");
                            TextField nameText = new TextField();
                        nameBox.getChildren().addAll(nameLabel, nameText);
                        HBox spriteBox = new HBox(10);
                            Label spriteLabel = new Label("URL do grafiki:");
                            TextField spriteText = new TextField();
                        spriteBox.getChildren().addAll(spriteLabel, spriteText);
                        HBox hpBox = new HBox(10);
                            Label hpLabel = new Label("Leczenie:");
                            TextField hpText = new TextField();
                        hpBox.getChildren().addAll(hpLabel, hpText);
                        HBox buttons = new HBox(10);
                            Button btnAdd = new Button("Dodaj");
                            btnAdd.setOnAction(b -> {
                                ItemModel item = new ItemModel(nameText.getText(), spriteText.getText(), Integer.parseInt(hpText.getText()));
                                DBConnection.addItem(item);
                                items = FXCollections.observableList(DBConnection.getItemsFromDb());
                                itemsTable.setItems(items);
                                root.setRight(null);
                            });
                            Button btnCancel = new Button("Anuluj");
                            btnCancel.setOnAction(b -> root.setRight(null));
                        buttons.getChildren().addAll(btnAdd, btnCancel);
                    sidebar.getChildren().addAll(nameBox, spriteBox, hpBox, buttons);

                    root.setRight(sidebar);
                });
                MenuItem addMenuElement = new MenuItem("Element");
            addMenu.getItems().addAll(addMenuEnemy, addMenuWeapon, addMenuItem, addMenuElement);
        menuBar.getMenus().addAll(mainMenu, addMenu);
        root.setTop(menuBar);

            TableColumn<EnemyModel, Integer> enemyIdCol = new TableColumn<>("ID");
                enemyIdCol.setCellValueFactory(new PropertyValueFactory<>("id_enemy"));
                enemyIdCol.setMinWidth(100);
            TableColumn<EnemyModel, String> enemyNameCol = new TableColumn<>("Nazwa");
                enemyNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                enemyNameCol.setMinWidth(100);
            TableColumn<EnemyModel, Rectangle> enemySpriteCol = new TableColumn<>("Sprite");
                enemySpriteCol.setCellValueFactory(i -> {
                    Rectangle spriteRect = new Rectangle(32,32);
                    Image sprite = new Image(i.getValue().getSpriteURL());
                    spriteRect.setFill(new ImagePattern(sprite));
                    return Bindings.createObjectBinding(() -> spriteRect);
                });
                enemySpriteCol.setMinWidth(100);
            //TODO enemyElementCol
            TableColumn<EnemyModel, Integer> enemyHPCol = new TableColumn<>("Zdrowie");
                enemyHPCol.setCellValueFactory(new PropertyValueFactory<>("health"));
                enemyHPCol.setMinWidth(100);
            TableColumn<EnemyModel, String> enemyDmgCol = new TableColumn<>("Obrażenia");
                enemyDmgCol.setCellValueFactory(i -> {
                    String dmg = i.getValue().getDmgMin()+"-"+i.getValue().getDmgMax();
                    return Bindings.createObjectBinding(() -> dmg);
                });
                enemyDmgCol.setMinWidth(100);
            TableColumn<EnemyModel, Node> enemyItemCol = new TableColumn<>("Przedmiot");
                enemyItemCol.setCellValueFactory(i -> {
                    Node val = new Label("Brak");
                    if(i.getValue().getItemModel()!=null){
                        Image sprite = new Image(i.getValue().getItemModel().getSpriteURL());
                        Rectangle spriteRect = new Rectangle(32,32);
                        spriteRect.setFill(new ImagePattern(sprite));
                    }
                    return Bindings.createObjectBinding(() -> val);
                });
                enemyItemCol.setMinWidth(100);
            TableColumn<EnemyModel, HBox> enemyEditCol = new TableColumn<>("Edycja");
                enemyEditCol.setCellFactory(e -> {
                    HBox editBtns = new HBox(10);
                    TableCell<EnemyModel, HBox> editCell = new TableCell<>(){
                        @Override
                        protected void updateItem(HBox node, boolean b) {
                            super.updateItem(node, b);
                            if(b) setGraphic(null);
                            else setGraphic(editBtns);
                        }
                    };
                    Button editBtn = new Button("Edytuj");
                    editBtn.setOnAction(a -> {
                        VBox sidebar = new VBox(10);
                        HBox nameBox = new HBox(10);
                        Label nameLabel = new Label("Nazwa:");
                        TextField nameText = new TextField(editCell.getTableRow().getItem().getName());
                        nameBox.getChildren().addAll(nameLabel, nameText);
                        HBox spriteBox = new HBox(10);
                        Label spriteLabel = new Label("URL do grafiki:");
                        TextField spriteText = new TextField(editCell.getTableRow().getItem().getSpriteURL());
                        spriteBox.getChildren().addAll(spriteLabel, spriteText);
                        HBox healthBox = new HBox(10);
                        Label healthLabel = new Label("Zdrowie:");
                        TextField healthText = new TextField(String.valueOf(editCell.getTableRow().getItem().getHealth()));
                        healthBox.getChildren().addAll(healthLabel, healthText);
                        HBox dmgBox = new HBox(10);
                        Label dmgLabel = new Label("Obrażenia:");
                        TextField dmgMinText = new TextField(String.valueOf(editCell.getTableRow().getItem().getDmgMin()));
                        Label dmgLine = new Label("-");
                        TextField dmgMaxText = new TextField(String.valueOf(editCell.getTableRow().getItem().getDmgMax()));
                        dmgBox.getChildren().addAll(dmgLabel, dmgMinText, dmgLine, dmgMaxText);
                        HBox itemBox = new HBox(10);
                        Label itemLabel = new Label("Upuszczany przedmiot:");
                        ComboBox<ItemModel> itemComboBox = new ComboBox<>(items);
                        itemComboBox.setCellFactory(c -> new ListCell<>() {
                            @Override
                            protected void updateItem(ItemModel s, boolean b) {
                                super.updateItem(s, b);
                                if (s != null)
                                    setText(s.getName());
                                else setText("Brak");
                            }
                        });
                        itemComboBox.getSelectionModel().select(editCell.getTableRow().getItem().getItemModel());
                        itemBox.getChildren().addAll(itemLabel, itemComboBox);
                        HBox buttons = new HBox(10);
                        Button btnAdd = new Button("Edytuj");
                        btnAdd.setOnAction(b -> {
                            EnemyModel enemy = new EnemyModel(nameText.getText(), spriteText.getText(), Integer.parseInt(healthText.getText()), Integer.parseInt(dmgMinText.getText()), Integer.parseInt(dmgMaxText.getText()), itemComboBox.getValue());
                            DBConnection.editEnemy(editCell.getTableRow().getItem().getId_enemy(), enemy);
                            enemies = FXCollections.observableList(DBConnection.getEnemiesFromDb());
                            enemiesTable.setItems(enemies);
                            root.setRight(null);
                        });
                        Button btnCancel = new Button("Anuluj");
                        btnCancel.setOnAction(b -> root.setRight(null));
                        buttons.getChildren().addAll(btnAdd, btnCancel);
                        sidebar.getChildren().addAll(nameBox, spriteBox, healthBox, dmgBox, itemBox, buttons);
                        root.setRight(sidebar);
                    });
                    Button delBtn = new Button("Usuń");
                    delBtn.setOnAction(a -> {
                        DBConnection.deleteEnemy(editCell.getTableRow().getItem().getId_enemy());
                        enemies = FXCollections.observableList(DBConnection.getEnemiesFromDb());
                        enemiesTable.setItems(enemies);
                    });
                    editBtns.getChildren().addAll(editBtn, delBtn);
                    return editCell;
                });
                enemyEditCol.setMinWidth(150);
            enemiesTable.getColumns().add(enemyIdCol);
            enemiesTable.getColumns().add(enemyNameCol);
            enemiesTable.getColumns().add(enemySpriteCol);
            enemiesTable.getColumns().add(enemyHPCol);
            enemiesTable.getColumns().add(enemyDmgCol);
            enemiesTable.getColumns().add(enemyItemCol);
            enemiesTable.getColumns().add(enemyEditCol);
            TableColumn<ItemModel, Integer> itemIdCol = new TableColumn<>("ID");
                itemIdCol.setCellValueFactory(new PropertyValueFactory<>("itemId"));
                itemIdCol.setMinWidth(100);
            TableColumn<ItemModel, String> itemNameCol = new TableColumn<>("Nazwa");
                itemNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                itemNameCol.setMinWidth(100);
            TableColumn<ItemModel, Rectangle> itemSpriteCol = new TableColumn<>("Sprite");
                itemSpriteCol.setCellValueFactory(e -> {
                    Image sprite = new Image(e.getValue().getSpriteURL());
                    Rectangle rect = new Rectangle(32,32);
                    rect.setFill(new ImagePattern(sprite));
                    return Bindings.createObjectBinding(() -> rect);
                });
                itemSpriteCol.setMinWidth(100);
            //TODO ElementCol
            TableColumn<ItemModel, String> itemDmgCol = new TableColumn<>("HP/Obrażenia");
                itemDmgCol.setCellValueFactory(e -> {
                    String text = e.getValue().getType() == Item.Type.Heal ? String.valueOf(e.getValue().getDmg_max()) : e.getValue().getDmg_min()+"-"+e.getValue().getDmg_max();
                    return Bindings.createObjectBinding(() -> text);
                });
                itemDmgCol.setMinWidth(100);
            TableColumn<ItemModel, String> itemTypeCol = new TableColumn<>("Typ");
                itemTypeCol.setCellValueFactory(e -> {
                    String text = "";
                    switch (e.getValue().getType()){
                        case Heal -> text = "Leczenie";
                        case Weapon -> text = "Broń";
                    }
                    String finalText = text;
                    return Bindings.createObjectBinding(() -> finalText);
                });
                itemTypeCol.setMinWidth(100);
            TableColumn<ItemModel, HBox> itemEditCol = new TableColumn<>("Edycja");
                itemEditCol.setCellFactory(e -> {
                    HBox editBtns = new HBox(10);
                    TableCell<ItemModel, HBox> editCell = new TableCell<>(){
                        @Override
                        protected void updateItem(HBox hBox, boolean b) {
                            super.updateItem(hBox, b);
                            if(b) setGraphic(null);
                            else setGraphic(editBtns);
                        }
                    };
                    Button editBtn = new Button("Edytuj");
                    editBtn.setOnAction(a -> {
                        VBox sidebar = new VBox(10);
                        switch (editCell.getTableRow().getItem().getType()){
                            case Weapon -> {
                                HBox nameBox = new HBox(10);
                                Label nameLabel = new Label("Nazwa:");
                                TextField nameText = new TextField(editCell.getTableRow().getItem().getName());
                                nameBox.getChildren().addAll(nameLabel, nameText);
                                HBox spriteBox = new HBox(10);
                                Label spriteLabel = new Label("URL do grafiki:");
                                TextField spriteText = new TextField(editCell.getTableRow().getItem().getSpriteURL());
                                spriteBox.getChildren().addAll(spriteLabel, spriteText);
                                HBox dmgBox = new HBox(10);
                                Label dmgLabel = new Label("Obrażenia:");
                                TextField dmgMinText = new TextField(String.valueOf(editCell.getTableRow().getItem().getDmg_min()));
                                Label dmgLine = new Label("-");
                                TextField dmgMaxText = new TextField(String.valueOf(editCell.getTableRow().getItem().getDmg_max()));
                                dmgBox.getChildren().addAll(dmgLabel, dmgMinText, dmgLine, dmgMaxText);
                                HBox buttons = new HBox(10);
                                Button btnAdd = new Button("Edytuj");
                                btnAdd.setOnAction(b -> {
                                    ItemModel item = new ItemModel(nameText.getText(), spriteText.getText(), Integer.parseInt(dmgMinText.getText()), Integer.parseInt(dmgMaxText.getText()));
                                    DBConnection.editItem(editCell.getTableRow().getItem().getItemId(), item);
                                    items = FXCollections.observableList(DBConnection.getItemsFromDb());
                                    itemsTable.setItems(items);
                                    root.setRight(null);
                                });
                                Button btnCancel = new Button("Anuluj");
                                btnCancel.setOnAction(b -> root.setRight(null));
                                buttons.getChildren().addAll(btnAdd, btnCancel);
                                sidebar.getChildren().addAll(nameBox, spriteBox, dmgBox, buttons);
                            }
                            case Heal -> {
                                HBox nameBox = new HBox(10);
                                Label nameLabel = new Label("Nazwa:");
                                TextField nameText = new TextField(editCell.getTableRow().getItem().getName());
                                nameBox.getChildren().addAll(nameLabel, nameText);
                                HBox spriteBox = new HBox(10);
                                Label spriteLabel = new Label("URL do grafiki:");
                                TextField spriteText = new TextField(editCell.getTableRow().getItem().getSpriteURL());
                                spriteBox.getChildren().addAll(spriteLabel, spriteText);
                                HBox hpBox = new HBox(10);
                                Label hpLabel = new Label("Leczenie:");
                                TextField hpText = new TextField(String.valueOf(editCell.getTableRow().getItem().getDmg_max()));
                                hpBox.getChildren().addAll(hpLabel, hpText);
                                HBox buttons = new HBox(10);
                                Button btnAdd = new Button("Dodaj");
                                btnAdd.setOnAction(b -> {
                                    ItemModel item = new ItemModel(nameText.getText(), spriteText.getText(), Integer.parseInt(hpText.getText()));
                                    DBConnection.editItem(editCell.getTableRow().getItem().getItemId(), item);
                                    items = FXCollections.observableList(DBConnection.getItemsFromDb());
                                    itemsTable.setItems(items);
                                    root.setRight(null);
                                });
                                Button btnCancel = new Button("Anuluj");
                                btnCancel.setOnAction(b -> root.setRight(null));
                                buttons.getChildren().addAll(btnAdd, btnCancel);
                                sidebar.getChildren().addAll(nameBox, spriteBox, hpBox, buttons);
                            }
                        }
                        root.setRight(sidebar);
                    });
                    Button deleteBtn = new Button("Usuń");
                    deleteBtn.setOnAction(a -> {
                        DBConnection.deleteItem(editCell.getTableRow().getItem().getItemId());
                        items = FXCollections.observableList(DBConnection.getItemsFromDb());
                        itemsTable.setItems(items);
                        root.setRight(null);
                    });
                    editBtns.getChildren().addAll(editBtn, deleteBtn);
                    return editCell;
                });
            itemsTable.getColumns().add(itemIdCol);
            itemsTable.getColumns().add(itemNameCol);
            itemsTable.getColumns().add(itemSpriteCol);
            itemsTable.getColumns().add(itemDmgCol);
            itemsTable.getColumns().add(itemTypeCol);
            itemsTable.getColumns().add(itemEditCol);

        Tab enemyTab = new Tab("Wrogowie", enemiesTable);
        Tab itemTab = new Tab("Przedmioty", itemsTable);
        enemyTab.setClosable(false);
        itemTab.setClosable(false);
        tabPane.getTabs().addAll(enemyTab, itemTab);
        return scene;
        //TODO
        // elementy
        // edytowanie
        // usuwanie
        // zabezpieczenia (parseInt, błędny url)
    }
}
