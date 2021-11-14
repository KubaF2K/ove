package com.example.main;

import javafx.scene.image.Image;

public class Entity {
    private Image sprite;
    private boolean empty;
    public boolean isEmpty() {
        return empty;
    }
    public Image getSprite(){
        return sprite;
    }
    public Entity(Image sprite){
        this.sprite = sprite;
        empty = false;
    }
    public Entity(){//Puste pole
        sprite = new Image("file:res/img/empty.png");
        empty = true;
    }
}
