package com.example.main;

import javafx.scene.image.Image;

public class Player extends Mob{
    //TODO Item[] inventory;
    private int equippedItemId = 0;
    //TODO public Item getEquippedItem(){ return Item[equippedItemId]; }


    public void setEquippedItemId(int equippedItemId) {
        this.equippedItemId = equippedItemId;
    }

    public Player(Image sprite, int startHp){
        super(sprite, startHp, 4, 4);//Startowa pozycja (4,4) czyli środek siatki
    }
}
