package com.example.main.game;

import com.example.main.models.ItemModel;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class Item extends Entity{
    static int counter = 0;
    private final int itemId = counter++;
    public enum Type{
        Heal, Weapon
    }
    private final Type type;
    private final String name;
    //TODO private Element element;
    private final int dmgMin, dmgMax;

    public String getName() {
        return name;
    }
    public Type getType() {
        return type;
    }
    public int getDmgMin() {
        return dmgMin;
    }
    public int getDmgMax() {
        return dmgMax;
    }

    //Konstruktor dla itemów leczących
    public Item(String name, Image sprite, int healAmount){
        super(sprite);
        this.name = name;
        dmgMin = dmgMax = healAmount;
        type = Type.Heal;
    }
    //Konstruktor dla broni
    public Item(String name, Image sprite, int dmgMin, int dmgMax){//TODO Element element
        super(sprite);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        type = Type.Weapon;
    }
    public Item(ItemModel model){
        super(new Image(model.getSpriteURL()));
        name = model.getName();
        dmgMin = model.getDmg_min();
        dmgMax = model.getDmg_max();
        type = model.getType();
    }

    public int getDmg(){
        Random random = new Random();
        return random.nextInt(dmgMax-dmgMin)+dmgMin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemId == item.itemId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId);
    }
}
