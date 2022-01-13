package com.example.main.game;

import com.example.main.models.Element;
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
    private Element element;
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
    public Element getElement() {
        return element;
    }

    //Konstruktor dla itemów leczących
    public Item(String name, Image sprite, int healAmount){
        super(sprite);
        this.name = name;
        dmgMin = dmgMax = healAmount;
        type = Type.Heal;
    }
    //Konstruktor dla broni
    public Item(String name, Image sprite, int dmgMin, int dmgMax){
        super(sprite);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        type = Type.Weapon;
    }
    public Item(String name, Image sprite, int dmgMin, int dmgMax, Element element){
        super(sprite);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        this.element = element;
        type = Type.Weapon;
    }
    public Item(ItemModel model){
        super(new Image(Game.urlChooser("test")));
        name = model.getName();
        dmgMin = model.getDmgMin();
        dmgMax = model.getDmgMax();
        type = model.getType();
        element = model.getElement();
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
