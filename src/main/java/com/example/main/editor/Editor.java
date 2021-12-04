package com.example.main.editor;

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
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

public class Editor {
    private static ObservableList<EnemyModel> enemies;
    private static ObservableList<ItemModel> items;
    private static Configuration config = new Configuration().configure("file:res/hibernate.cfg.xml");
    private static ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
    private static SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
    private static void getEntitiesFromDb(){
        Session loadSesh = sessionFactory.openSession();
        Query enemyQuery = loadSesh.createQuery("from EnemyModel");
        Query itemQuery = loadSesh.createQuery("from ItemModel");
        enemies = FXCollections.observableList(enemyQuery.list());
        items = FXCollections.observableList(itemQuery.list());
        loadSesh.close();
    }
    private static void closeDbConnection(){
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
    }
    public static Scene getScene(){
        config.addAnnotatedClass(EnemyModel.class);
        config.addAnnotatedClass(ItemModel.class);
        getEntitiesFromDb();
        //TODO temp
        enemies.add(new EnemyModel("chłop", "file:res/img/enemy.png", 10, 3, 5, null));
        items.add(new ItemModel("Apteczka", "file:res/img/medkit.png", 50));
        items.add(new ItemModel("Miecz", "file:res/img/sword.png", 5, 10));

        BorderPane root = new BorderPane();
        TabPane tabPane = new TabPane();
        root.setCenter(tabPane);
        Scene scene = new Scene(root, 640, 480);

        MenuBar menuBar = new MenuBar();
        menuBar.prefWidthProperty().bind(scene.widthProperty());
            Menu mainMenu = new Menu("Menu");
                MenuItem mainMenuMenu = new MenuItem("Wróć do menu głównego");
                    mainMenuMenu.setOnAction(e -> {
                        closeDbConnection();
                        Main.resetScene();
                    });
                MenuItem mainMenuExit = new MenuItem("Wyjdź");
                    mainMenuExit.setOnAction(e -> {
                        closeDbConnection();
                        Platform.exit();
                    });
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
                            itemComboBox.setCellFactory(c -> {
                                ListCell<ItemModel> cell = new ListCell<>(){
                                    @Override
                                    protected void updateItem(ItemModel s, boolean b) {
                                        super.updateItem(s, b);
                                        if(s!=null)
                                            setText(s.getName());
                                        else setText("Brak");
                                    }
                                };
                                return cell;
                            });
                        itemBox.getChildren().addAll(itemLabel, itemComboBox);
                        HBox buttons = new HBox(10);
                            Button btnAdd = new Button("Dodaj");
                                btnAdd.setOnAction(b -> {
                                    EnemyModel enemy = new EnemyModel(nameText.getText(), spriteText.getText(), Integer.parseInt(healthText.getText()), Integer.parseInt(dmgMinText.getText()), Integer.parseInt(dmgMaxText.getText()), itemComboBox.getValue());
                                    Session saveSesh = sessionFactory.openSession();
                                    Transaction transaction = saveSesh.beginTransaction();
                                    saveSesh.save(enemy);
                                    transaction.commit();
                                    saveSesh.close();
                                });
                            Button btnCancel = new Button("Anuluj");
                                btnCancel.setOnAction(b -> root.setRight(null));
                        buttons.getChildren().addAll(btnAdd, btnCancel);
                    sidebar.getChildren().addAll(nameBox, spriteBox, healthBox, dmgBox, itemBox, buttons);

                    root.setRight(sidebar);
                });
                MenuItem addMenuWeapon = new MenuItem("Broń");
                MenuItem addMenuItem = new MenuItem("Przedmiot");
                MenuItem addMenuElement = new MenuItem("Element");
            addMenu.getItems().addAll(addMenuEnemy, addMenuWeapon, addMenuItem, addMenuElement);
        menuBar.getMenus().addAll(mainMenu, addMenu);
        root.setTop(menuBar);

        TableView<EnemyModel> enemiesTable = new TableView<>(enemies);
            TableColumn<EnemyModel, Integer> enemyIdCol = new TableColumn<>("ID");
                enemyIdCol.setCellValueFactory(new PropertyValueFactory<>("id_enemy"));
                enemyIdCol.setMinWidth(100);
            TableColumn<EnemyModel, String> enemyNameCol = new TableColumn<>("Nazwa");
                enemyNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
                enemyNameCol.setMinWidth(100);
            TableColumn<EnemyModel, Rectangle> enemySpriteCol = new TableColumn<>("Sprite");
                enemySpriteCol.setCellValueFactory(i -> {
                    Image sprite = new Image(i.getValue().getSpriteURL());
                    Rectangle spriteRect = new Rectangle(32,32);
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
            enemiesTable.getColumns().add(enemyIdCol);
            enemiesTable.getColumns().add(enemyNameCol);
            enemiesTable.getColumns().add(enemySpriteCol);
            enemiesTable.getColumns().add(enemyHPCol);
            enemiesTable.getColumns().add(enemyItemCol);
        TableView<ItemModel> itemsTable = new TableView<>(items);
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
            //TODO Elementcol
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
            itemsTable.getColumns().add(itemIdCol);
            itemsTable.getColumns().add(itemNameCol);
            itemsTable.getColumns().add(itemSpriteCol);
            itemsTable.getColumns().add(itemDmgCol);
            itemsTable.getColumns().add(itemTypeCol);

        Tab enemyTab = new Tab("Wrogowie", enemiesTable);
        Tab itemTab = new Tab("Przedmioty", itemsTable);
        enemyTab.setClosable(false);
        itemTab.setClosable(false);
        tabPane.getTabs().addAll(enemyTab, itemTab);
        return scene;
        //TODO dodawanie obiektów, itemy i elementy
    }
}
