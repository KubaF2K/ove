package com.example.main;

import javafx.scene.image.Image;

public class Mob extends Entity{
    private int hp,x,y;
    private int maxHp;
    public int getHp() {
        return hp;
    }
    public void takeDmg(int dmg){
        if(hp-dmg<0) hp=0;
        else hp-=dmg;
    }
    public void heal(int heal){
        if(hp+heal>maxHp) hp=maxHp;
        else hp+=heal;
    }

    //Pozycja
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void teleport(int x, int y){ //Podmiana starej pozycji na puste miejsce, podmiana nowej pozycji na awatar
        Main.entityTable[this.x][this.y] = new Entity();
        Main.entityTable[x][y] = this;
        this.x = x;
        this.y = y;
    }
    public void moveUp(){
        if(y!=0){
            if(Main.entityTable[x][y-1].isEmpty()){
                Main.entityTable[x][y] = new Entity();
                Main.entityTable[x][--y] = this;
            }
            else{
                //TODO o bogowie walka
            }
        }
    }
    public void moveDown(){
        if(y!=8){
            if(Main.entityTable[x][y+1].isEmpty()){
                Main.entityTable[x][y] = new Entity();
                Main.entityTable[x][++y] = this;
            }
            else{
                //TODO o bogowie walka
            }
        }
    }
    public void moveLeft(){
        if(x!=0){
            if(Main.entityTable[x-1][y].isEmpty()){
                Main.entityTable[x][y] = new Entity();
                Main.entityTable[--x][y] = this;
            }
            else{
                //TODO o bogowie walka
            }
        }
    }
    public void moveRight(){
        if(x!=8){
            if(Main.entityTable[x+1][y].isEmpty()){
                Main.entityTable[x][y] = new Entity();
                Main.entityTable[++x][y] = this;
            }
            else{
                //TODO o bogowie walka
            }
        }
    }
    public void moveUpLeft(){
        if(x!=0 && y!=0){
            if(Main.entityTable[x-1][y-1].isEmpty()){
                Main.entityTable[x][y] = new Entity();
                Main.entityTable[--x][--y] = this;
            }
            else{
                //TODO o bogowie walka
            }
        }
    }
    public void moveUpRight(){
        if(x!=8 && y!=0){
            if(Main.entityTable[x+1][y-1].isEmpty()){
                Main.entityTable[x][y] = new Entity();
                Main.entityTable[++x][--y] = this;
            }
            else{
                //TODO o bogowie walka
            }
        }
    }
    public void moveDownLeft(){
        if(x!=0 && y!=8){
            if(Main.entityTable[x-1][y+1].isEmpty()){
                Main.entityTable[x][y] = new Entity();
                Main.entityTable[--x][++y] = this;
            }
            else{
                //TODO o bogowie walka
            }
        }
    }
    public void moveDownRight(){
        if(x!=8 && y!=8){
            if(Main.entityTable[x+1][y+1].isEmpty()){
                Main.entityTable[x][y] = new Entity();
                Main.entityTable[++x][++y] = this;
            }
            else{
                //TODO o bogowie walka
            }
        }
    }

    public Mob(Image sprite, int startHp, int x, int y){
        super(sprite);
        hp = maxHp = startHp;
        Main.entityTable[x][y] = this; //Podstawienie do głównej tablicy
        this.x = x;
        this.y = y;
    }
}
