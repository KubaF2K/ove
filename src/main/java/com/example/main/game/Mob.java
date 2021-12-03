package com.example.main.game;

import javafx.scene.image.Image;

public abstract class Mob extends Entity{
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
    public abstract void interact(int x, int y);
    public abstract void pickup(Item item);
    public void drop(Item item){
        if(y!=0 && Game.entityTable[x][y-1].isEmpty()) Game.entityTable[x][y-1] = item;                         //N
        else if(x!=8 && Game.entityTable[x+1][y].isEmpty()) Game.entityTable[x+1][y] = item;                    //E
        else if(y!=8 && Game.entityTable[x][y+1].isEmpty()) Game.entityTable[x][y+1] = item;                    //S
        else if(x!=0 && Game.entityTable[x-1][y].isEmpty()) Game.entityTable[x-1][y] = item;                    //W
        else if((x!=8 && y!=0) && Game.entityTable[x+1][y-1].isEmpty()) Game.entityTable[x+1][y-1] = item;      //NE
        else if((x!=8 && y!=8) && Game.entityTable[x+1][y+1].isEmpty()) Game.entityTable[x+1][y+1] = item;      //SE
        else if((x!=0 && y!=8) && Game.entityTable[x-1][y+1].isEmpty()) Game.entityTable[x-1][y+1] = item;      //SW
        else if((x!=0 && y!=0) && Game.entityTable[x-1][y-1].isEmpty()) Game.entityTable[x-1][y-1] = item;      //NW
    }
    //Pozycja
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void teleport(int x, int y){ //Podmiana starej pozycji na puste miejsce, podmiana nowej pozycji na awatar
        Game.entityTable[this.x][this.y] = new Entity();
        Game.entityTable[x][y] = this;
        this.x = x;
        this.y = y;
    }
    public void moveUp(){
        if(y!=0){
            if(Game.entityTable[x][y-1].isEmpty()){
                Game.entityTable[x][y] = new Entity();
                Game.entityTable[x][--y] = this;
            }
            else{
                interact(x, y-1);
            }
        }
    }
    public void moveDown(){
        if(y!=8){
            if(Game.entityTable[x][y+1].isEmpty()){
                Game.entityTable[x][y] = new Entity();
                Game.entityTable[x][++y] = this;
            }
            else{
                interact(x, y+1);
            }
        }
    }
    public void moveLeft(){
        if(x!=0){
            if(Game.entityTable[x-1][y].isEmpty()){
                Game.entityTable[x][y] = new Entity();
                Game.entityTable[--x][y] = this;
            }
            else{
                interact(x-1, y);
            }
        }
    }
    public void moveRight(){
        if(x!=8){
            if(Game.entityTable[x+1][y].isEmpty()){
                Game.entityTable[x][y] = new Entity();
                Game.entityTable[++x][y] = this;
            }
            else{
                interact(x+1, y);
            }
        }
    }
    public void moveUpLeft(){
        if(x!=0 && y!=0){
            if(Game.entityTable[x-1][y-1].isEmpty()){
                Game.entityTable[x][y] = new Entity();
                Game.entityTable[--x][--y] = this;
            }
            else{
                interact(x-1, y-1);
            }
        }
    }
    public void moveUpRight(){
        if(x!=8 && y!=0){
            if(Game.entityTable[x+1][y-1].isEmpty()){
                Game.entityTable[x][y] = new Entity();
                Game.entityTable[++x][--y] = this;
            }
            else{
                interact(x+1, y-1);
            }
        }
    }
    public void moveDownLeft(){
        if(x!=0 && y!=8){
            if(Game.entityTable[x-1][y+1].isEmpty()){
                Game.entityTable[x][y] = new Entity();
                Game.entityTable[--x][++y] = this;
            }
            else{
                interact(x-1, y+1);
            }
        }
    }
    public void moveDownRight(){
        if(x!=8 && y!=8){
            if(Game.entityTable[x+1][y+1].isEmpty()){
                Game.entityTable[x][y] = new Entity();
                Game.entityTable[++x][++y] = this;
            }
            else{
                interact(x+1, y+1);
            }
        }
    }

    public Mob(Image sprite, int startHp, int x, int y){
        super(sprite);
        hp = maxHp = startHp;
        Game.entityTable[x][y] = this; //Podstawienie do głównej tablicy
        this.x = x;
        this.y = y;
    }
}
