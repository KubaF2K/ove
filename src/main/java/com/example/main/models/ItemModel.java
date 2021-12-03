package com.example.main.models;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "Item")
public class ItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item")
    private int itemId;

    @Column(name = "name")
    private String name;

    @Column(name = "sprite")
    private String spriteLink;

//    @ManyToOne
//    @JoinColumn(name = "id_element")
//    private ElementModel elementModel;

    @Column(name = "dmg_min")
    private int dmg_min;

    @Column(name = "dmg_max")
    private int dmg_max;

    @Column(name = "type")
    private Enum type;

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

    public String getSpriteLink() {
        return spriteLink;
    }

    public void setSpriteLink(String spriteLink) {
        this.spriteLink = spriteLink;
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

    public Enum getType() {
        return type;
    }

    public void setType(Enum type) {
        this.type = type;
    }


}
