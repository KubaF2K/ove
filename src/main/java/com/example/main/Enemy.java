package com.example.main;

import javafx.scene.image.Image;

import java.util.Objects;

public class Enemy extends Mob{
    static int counter=0;
    private int enemyId = counter++;
    private String name;
    //TODO private Element element;
    private int dmgMin, dmgMax;

    public Enemy(String name, Image sprite, int hp, int dmgMin, int dmgMax, int x, int y){//TODO dodać element
        super(sprite, hp, x, y);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
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
