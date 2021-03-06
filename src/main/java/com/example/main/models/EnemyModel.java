package com.example.main.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Enemy")
public class EnemyModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_enemy")
    private int enemyId;

    @Column(name = "name")
    private String name;

    @Column(name = "sprite")
    private String spriteURL;

    @ManyToOne
    @JoinColumn(name = "id_element")
    private Element element;

    @Column(name = "health")
    private int health;

    @Column(name = "dmg_min")
    private int dmgMin;

    @Column(name = "dmg_max")
    private int dmgMax;

    @ManyToOne
    @JoinColumn(name = "id_item")
    private ItemModel itemModel;

    public EnemyModel() {}

    public EnemyModel(String name, String spriteURL, int health, int dmgMin, int dmgMax, ItemModel item, Element element) {
        this.name = name;
        this.spriteURL = spriteURL;
        this.health = health;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        itemModel = item;
        this.element = element;
    }

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpriteURL() {
        return spriteURL;
    }

    public void setSpriteURL(String spriteURL) {
        this.spriteURL = spriteURL;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDmgMin() {
        return dmgMin;
    }

    public void setDmgMin(int dmgMin) {
        this.dmgMin = dmgMin;
    }

    public int getDmgMax() {
        return dmgMax;
    }

    public void setDmgMax(int dmgMax) {
        this.dmgMax = dmgMax;
    }

    public ItemModel getItemModel(){
        return itemModel;
    }

    public void setItemModel(ItemModel itemModel){
        this.itemModel = itemModel;
    }
}
