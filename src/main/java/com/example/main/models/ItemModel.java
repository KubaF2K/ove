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

    @ManyToOne
    @JoinColumn(name = "id_element")
    private Element element;

    @Column(name = "dmg_min")
    private int dmgMin;

    @Column(name = "dmg_max")
    private int dmgMax;

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

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public int getDmgMin() {
        return dmgMin;
    }

    public void setDmgMin(int dmg_min) {
        this.dmgMin = dmg_min;
    }

    public int getDmgMax() {
        return dmgMax;
    }

    public void setDmgMax(int dmg_max) {
        this.dmgMax = dmg_max;
    }

    public Item.Type getType() {
        return type;
    }

    public void setType(Item.Type type) {
        this.type = type;
    }

    public ItemModel(String name, String spriteURL, int dmgMin, int dmgMax, Element element) {
        this.name = name;
        this.spriteURL = spriteURL;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        this.element = element;
        type = Item.Type.Weapon;
    }
    public ItemModel(String name, String spriteURL, int healAmount){
        this.name = name;
        this.spriteURL = spriteURL;
        dmgMin = dmgMax = healAmount;
        type = Item.Type.Heal;
    }

    public ItemModel() {}
}
