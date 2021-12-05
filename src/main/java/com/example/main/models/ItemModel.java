package com.example.main.models;

import com.example.main.game.Item;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Item")
public class ItemModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_item")
    private int itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "sprite")
    private String spriteURL;

//    @ManyToOne
//    @JoinColumn(name = "id_element")
//    private ElementModel elementModel;

    @Column(name = "dmg_min")
    private int dmg_min;

    @Column(name = "dmg_max")
    private int dmg_max;

    @Column(name = "type")
    private Item.Type type;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
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

//    public ElementModel getElementModel() {
//        return elementModel;
//    }
//
//    public void setElementModel(ElementModel elementModel) {
//        this.elementModel = elementModel;
//    }

    public int getDmg_min() {
        return dmg_min;
    }

    public void setDmg_min(int dmg_min) {
        this.dmg_min = dmg_min;
    }

    public int getDmg_max() {
        return dmg_max;
    }

    public void setDmg_max(int dmg_max) {
        this.dmg_max = dmg_max;
    }

    public Item.Type getType() {
        return type;
    }

    public void setType(Item.Type type) {
        this.type = type;
    }

    public ItemModel(String name, String spriteURL, int dmg_min, int dmg_max) {
        this.name = name;
        this.spriteURL = spriteURL;
        this.dmg_min = dmg_min;
        this.dmg_max = dmg_max;
        type = Item.Type.Weapon;
    }
    public ItemModel(String name, String spriteURL, int healAmount){
        this.name = name;
        this.spriteURL = spriteURL;
        dmg_min = dmg_max = healAmount;
        type = Item.Type.Heal;
    }

    public ItemModel() {}
}
