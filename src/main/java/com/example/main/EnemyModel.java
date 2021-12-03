package com.example.main;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "Enemy")
public class EnemyModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int enemyId;

    @Column(name = "name")
    private String name;

    @Column(name = "sprite")
    private String spriteLink;

    @ManyToOne
    @JoinColumn(name = "id_element")
    private Element element;

    @Column(name = "health")
    private int health;

    @Column(name = "dmg_min")
    private int dmgMin;

    @Column(name = "dmg_max")
    private int dmgMax;

    public int getEnemyId() {
        return enemyId;
    }

    public void setEnemyId(int enemyId) {
        this.enemyId = enemyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpriteLink() {
        return spriteLink;
    }

    public void setSpriteLink(String spriteLink) {
        this.spriteLink = spriteLink;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDmgMin() {
        return dmgMin;
    }

    public void setDmgMin(int dmgMin) {
        this.dmgMin = dmgMin;
    }

    public int getDmgMax() {
        return dmgMax;
    }

    public void setDmgMax(int dmgMax) {
        this.dmgMax = dmgMax;
    }


}
