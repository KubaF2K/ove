package com.example.main;

import com.example.main.models.EnemyModel;
import com.example.main.models.ItemModel;

import javax.persistence.*;
import java.util.List;

public class DBConnection {
    private static EntityManagerFactory emf;
    public static EntityManager getEntityManager(){
        if(emf == null) emf = Persistence.createEntityManagerFactory("gameBasePersistence");
        return emf.createEntityManager();
    }
    public static List<EnemyModel> getEnemiesFromDb(){
        EntityManager em = getEntityManager();
        TypedQuery<EnemyModel> enemiesQuery = em.createQuery("SELECT e FROM EnemyModel e", EnemyModel.class);
        List<EnemyModel> enemies = enemiesQuery.getResultList();
        em.close();
        return enemies;
    }
    public static List<ItemModel> getItemsFromDb(){
        EntityManager em = getEntityManager();
        TypedQuery<ItemModel> itemsQuery = em.createQuery("SELECT i FROM ItemModel i", ItemModel.class);
        List<ItemModel> items = itemsQuery.getResultList();
        em.close();
        return items;
    }
    public static void addEnemy(EnemyModel enemy){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(enemy);
        em.getTransaction().commit();
        em.close();
    }
    public static void addItem(ItemModel item){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(item);
        em.getTransaction().commit();
        em.close();
    }
    public static void editEnemy(int id, EnemyModel newEnemy){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        EnemyModel enemy = em.find(EnemyModel.class, id);
        enemy.setName(newEnemy.getName());
        enemy.setSpriteURL(newEnemy.getSpriteURL());
        enemy.setHealth(newEnemy.getHealth());
        enemy.setDmgMin(newEnemy.getDmgMin());
        enemy.setDmgMax(newEnemy.getDmgMax());
        enemy.setItemModel(newEnemy.getItemModel());
        em.getTransaction().commit();
        em.close();
    }
    public static void editItem(int id, ItemModel newItem){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ItemModel item = em.find(ItemModel.class, id);
        item.setName(newItem.getName());
        item.setSpriteURL(newItem.getSpriteURL());
        item.setDmg_min(newItem.getDmg_min());
        item.setDmg_max(newItem.getDmg_max());
        item.setType(newItem.getType());
        em.getTransaction().commit();
        em.close();
    }
}
