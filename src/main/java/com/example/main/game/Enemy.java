package com.example.main.game;

import com.example.main.models.Element;
import com.example.main.models.EnemyModel;
import javafx.scene.image.Image;

import java.util.Objects;
import java.util.Random;
public class Enemy extends Mob{

    static int counter=0;
    private final int enemyId = counter++;
    private final String name;
    private Item carrying;
    private Element element;
    private final int dmgMin, dmgMax;

    public Enemy(String name, Image sprite, int hp, int dmgMin, int dmgMax, int x, int y){
        super(sprite, hp, x, y);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
    }
    public Enemy(String name, Image sprite, int hp, int dmgMin, int dmgMax, int x, int y, Element element){
        super(sprite, hp, x, y);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        this.element = element;
    }
    public Enemy(String name, Image sprite, int hp, int dmgMin, int dmgMax, int x, int y, Item carrying){
        super(sprite, hp, x, y);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        this.carrying = carrying;
    }
    public Enemy(String name, Image sprite, int hp, int dmgMin, int dmgMax, int x, int y, Element element, Item carrying){
        super(sprite, hp, x, y);
        this.name = name;
        this.dmgMin = dmgMin;
        this.dmgMax = dmgMax;
        this.element = element;
        this.carrying = carrying;
    }
    public Enemy(EnemyModel model, int x, int y){
        super(new Image(model.getSpriteURL()), model.getHealth(), x, y);
        name = model.getName();
        dmgMin = model.getDmgMin();
        dmgMax = model.getDmgMax();
        if(model.getItemModel() != null)
            carrying = new Item(model.getItemModel());
        element = model.getElement();
    }

    public int getId(){
        return enemyId;
    }

    public Item getCarrying() {
        return carrying;
    }

    public int getDmg(){
        Random random = new Random();
        return random.nextInt(dmgMax-dmgMin)+dmgMin;
    }

    public void takeDmg(int dmg, Element attackingElement) {
        if(element != null && attackingElement != null){
            if(element.getWeakToId() == attackingElement.getElementId()){
                takeDmg(dmg*2);
                return;
            }
            if(element.getStrongToId() == attackingElement.getElementId()){
                takeDmg(dmg/2);
                return;
            }
        }
        takeDmg(dmg);
    }

    @Override
    public void pickup(Item item) {
        if(carrying==null) carrying=item;
        else drop(item);
    }

    @Override
    public void interact(int x, int y) {
        if(x== Game.player.getX() && y== Game.player.getY()){ //Zderzenie z awatarem
            Game.player.takeDmg(getDmg());
            if(Game.player.getHp()==0){
                Game.player = null;
                Game.entityTable[x][y] = new Entity();
                //Main.setGameOver();
            }
        }
        else if(Game.entityTable[x][y].getClass().getSimpleName().equals("Item")) { //Zderzenie z przedmiotem
            Item temp = (Item) Game.entityTable[x][y];
            teleport(x, y);
            if (carrying != null) { //Przestawienie leżącego przedmiotu
                drop(temp);
            } else {
                carrying = temp;//Podniesienie przedmiotu
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
