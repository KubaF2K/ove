package com.example.main.game;

import javafx.scene.image.Image;

public class Player extends Mob{
    private final Item[] inventory = new Item[10];
    private int equippedItemId = 0;

    public int getEquippedItemId() {
        return equippedItemId;
    }
    public Item getEquippedItem(){ return inventory[equippedItemId]; }
    public Item[] getInventory() {
        return inventory;
    }

    public void setEquippedItemId(int equippedItemId) {
        this.equippedItemId = equippedItemId;
    }

    public Player(Image sprite, int startHp){
        super(sprite, startHp, 4, 4);//Startowa pozycja (4,4) czyli środek siatki
    }

    public void useItem(){
        if(getEquippedItem()!=null)
            if(getEquippedItem().getType()==Item.Type.Heal){
                heal(getEquippedItem().getDmgMin());
                inventory[getEquippedItemId()] = null;
            }
    }

    public void dropCurrentItem(){
        drop(inventory[getEquippedItemId()]);
        inventory[getEquippedItemId()] = null;
    }

    public void deleteCurrentItem(){
        inventory[getEquippedItemId()] = null;
    }

    @Override
    public void pickup(Item item){
        for (int i = 0; i <= inventory.length; i++) {
            if(i==inventory.length){    //Brak miejsca w ekwipunku, przestawienie przedmiotu
                drop(item);
            }
            else{
                if(inventory[i]==null) {
                    inventory[i] = item;
                    break;
                }
            }
        }
    }

    @Override
    public void interact(int x, int y) {
        if(Game.entityTable[x][y].getClass().getSimpleName().equals("Enemy")) { //Zderzenie z wrogiem
            if (getEquippedItem() != null) if (getEquippedItem().getType() == Item.Type.Weapon) {
                ((Enemy) Game.entityTable[x][y]).takeDmg(getEquippedItem().getDmg(), getEquippedItem().getElement());
                if (((Enemy) Game.entityTable[x][y]).getHp() == 0) {
                    if (((Enemy) Game.entityTable[x][y]).getCarrying() == null)
                        Game.entityTable[x][y] = new Entity();
                    else {  //Upuść przedmiot
                        Item temp = ((Enemy) Game.entityTable[x][y]).getCarrying();
                        Game.entityTable[x][y] = temp;
                    }
                }
            }
        }
        else if(Game.entityTable[x][y].getClass().getSimpleName().equals("Item")) { //Zderzenie z przedmiotem
            Item temp = (Item) Game.entityTable[x][y];
            teleport(x, y);
            pickup(temp);
        }
    }
}
