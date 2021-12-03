package com.example.main.editor;

import com.example.main.models.EnemyModel;
import com.example.main.models.ItemModel;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import java.util.List;

public class Editor {
    public static Scene getScene(){
        Configuration config = new Configuration().configure("file:res/hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        SessionFactory sessionFactory = config.buildSessionFactory(serviceRegistry);
        Session loadSesh = sessionFactory.openSession();
            Query enemyQuery = loadSesh.createQuery("from "+EnemyModel.class.getName());
            Query itemQuery = loadSesh.createQuery("from "+ItemModel.class.getName());
            ObservableList<EnemyModel> enemies = FXCollections.observableList(enemyQuery.list());
            ObservableList<ItemModel> items = FXCollections.observableList(itemQuery.list());
        loadSesh.close();
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(serviceRegistry);
        BorderPane root = new BorderPane();

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

        root.setCenter(enemiesTable);
        return new Scene(root, 640, 480);
        //TODO dodawanie obiektów, itemy i elementy
    }
}
