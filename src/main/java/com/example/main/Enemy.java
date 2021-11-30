package com.example.main;

import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;

public class Enemy extends Mob{
    static int counter=0;
    private int enemyId = counter++;
    private String name;
    private Item carrying;
    //TODO private Element element;
    private int dmgMin, dmgMax;

    public Enemy(String name, Image sprite, int hp, int dmgMin, int dmgMax, int x, int y){//TODO dodać element
        super(sprite, hp, x, y);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
    }
    public Enemy(String name, Image sprite, int hp, int dmgMin, int dmgMax, int x, int y, Item carrying){//TODO dodać element
        super(sprite, hp, x, y);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        this.carrying = carrying;
    }

    public Item getCarrying() {
        return carrying;
    }

    public int getDmg(){
        Random random = new Random();
        return random.nextInt(dmgMax-dmgMin)+dmgMin;
    }

    @Override
    public void pickup(Item item) {
        if(carrying==null) carrying=item;
        else drop(item);
    }

    @Override
    public void interact(int x, int y) {
        if(x==Main.player.getX() && y==Main.player.getY()){ //Zderzenie z awatarem
            Main.player.takeDmg(getDmg());
            if(Main.player.getHp()==0){
                Main.player = null;
                Main.entityTable[x][y] = new Entity();
                //Main.setGameOver();
            }
        }
        else if(Main.entityTable[x][y].getClass().getSimpleName().equals("Item")) { //Zderzenie z przedmiotem
            if (Main.items.contains((Item) Main.entityTable[x][y])) {
                Item temp = (Item) Main.entityTable[x][y];
                teleport(x, y);
                if (carrying != null) { //Przestawienie leżącego przedmiotu
                    drop(temp);
                } else {
                    carrying = temp;//Podniesienie przedmiotu
                    Main.items.remove(temp);
                }
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enemy enemy = (Enemy) o;
        return enemyId == enemy.enemyId;
    }
    @Override
    public int hashCode() {
        return Objects.hash(enemyId);
    }
}
