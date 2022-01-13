package com.example.main;

import com.example.main.models.Element;
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

    public static List<EnemyModel> getEnemies(){
        EntityManager em = getEntityManager();
        TypedQuery<EnemyModel> enemiesQuery = em.createQuery("SELECT e FROM EnemyModel e", EnemyModel.class);
        List<EnemyModel> enemies = enemiesQuery.getResultList();
        em.close();
        return enemies;
    }
    public static List<ItemModel> getItems(){
        EntityManager em = getEntityManager();
        TypedQuery<ItemModel> itemsQuery = em.createQuery("SELECT i FROM ItemModel i", ItemModel.class);
        List<ItemModel> items = itemsQuery.getResultList();
        em.close();
        return items;
    }
    public static List<Element> getElements(){
        EntityManager em = getEntityManager();
        TypedQuery<Element> elementsQuery = em.createQuery("SELECT e FROM Element e", Element.class);
        List<Element> elements = elementsQuery.getResultList();
        em.close();
        return elements;
    }

    public static EnemyModel getEnemy(int id){
        EntityManager em = getEntityManager();
        TypedQuery<EnemyModel> enemyQuery = em.createQuery("SELECT e FROM EnemyModel e WHERE e.enemyId="+id, EnemyModel.class);
        EnemyModel enemy = enemyQuery.getSingleResult();
        em.close();
        return enemy;
    }
    public static ItemModel getItem(int id){
        EntityManager em = getEntityManager();
        TypedQuery<ItemModel> itemQuery = em.createQuery("SELECT i FROM ItemModel i WHERE i.itemId="+id, ItemModel.class);
        ItemModel item = itemQuery.getSingleResult();
        em.close();
        return item;
    }
    public static Element getElement(int id){
        EntityManager em = getEntityManager();
        TypedQuery<Element> elementQuery = em.createQuery("SELECT e FROM Element e WHERE e.elementId="+id, Element.class);
        Element element = elementQuery.getSingleResult();
        em.close();
        return element;
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
    public static void addElement(Element element){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(element);
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
        enemy.setElement(newEnemy.getElement());
        em.getTransaction().commit();
        em.close();
    }
    public static void editItem(int id, ItemModel newItem){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ItemModel item = em.find(ItemModel.class, id);
        item.setName(newItem.getName());
        item.setSpriteURL(newItem.getSpriteURL());
        item.setDmgMin(newItem.getDmgMin());
        item.setDmgMax(newItem.getDmgMax());
        item.setType(newItem.getType());
        item.setElement(newItem.getElement());
        em.getTransaction().commit();
        em.close();
    }
    public static void editElement(int id, Element newElement){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Element element = em.find(Element.class, id);
        element.setName(newElement.getName());
        element.setSpriteURL(newElement.getSpriteURL());
        element.setWeakToId(newElement.getWeakToId());
        element.setStrongToId(newElement.getStrongToId());
        em.getTransaction().commit();
        em.close();
    }

    public static void deleteEnemy(int id){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(EnemyModel.class, id));
        em.getTransaction().commit();
        em.close();
    }
    public static void deleteItem(int id){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        ItemModel toDelete = em.find(ItemModel.class, id);
        TypedQuery<EnemyModel> enemyQuery = em.createQuery("SELECT e FROM EnemyModel e WHERE e.itemModel = :td", EnemyModel.class);
        enemyQuery.setParameter("td", toDelete);
        List<EnemyModel> enemiesToFix = enemyQuery.getResultList();
        for (EnemyModel enemy :
                enemiesToFix) {
            enemy.setItemModel(null);
        }
        em.remove(toDelete);
        em.getTransaction().commit();
        em.close();
    }
    public static void deleteElement(int id){
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Element toDelete = em.find(Element.class, id);
        TypedQuery<EnemyModel> enemyQuery = em.createQuery("SELECT e FROM EnemyModel e WHERE e.element = :td", EnemyModel.class);
        enemyQuery.setParameter("td", toDelete);
        List<EnemyModel> enemiesToFix = enemyQuery.getResultList();
        for (EnemyModel enemy :
                enemiesToFix) {
            enemy.setElement(null);
        }
        TypedQuery<ItemModel> itemQuery = em.createQuery("SELECT i FROM ItemModel i WHERE i.element = :td", ItemModel.class);
        itemQuery.setParameter("td", toDelete);
        List<ItemModel> itemsToFix = itemQuery.getResultList();
        for (ItemModel item :
                itemsToFix) {
            item.setElement(null);
        }
        TypedQuery<Element> weakElementQuery = em.createQuery("SELECT e FROM Element e WHERE e.weakToId = "+id, Element.class);
        List<Element> weakElementsToFix = weakElementQuery.getResultList();
        for (Element element :
                weakElementsToFix) {
            element.setWeakToId(-1);
        }
        TypedQuery<Element> strongElementQuery = em.createQuery("SELECT e FROM Element e WHERE e.strongToId = "+id, Element.class);
        List<Element> strongElementsToFix = strongElementQuery.getResultList();
        for (Element element :
                strongElementsToFix) {
            element.setStrongToId(-1);
        }
        em.remove(toDelete);
        em.getTransaction().commit();
        em.close();
    }
}
