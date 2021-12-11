package com.example.main.models;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Element implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_element")
    private int elementId;

    @Column(name = "name")
    private String name;

    @Column(name = "sprite")
    private String spriteURL;

    @Column(name = "weak_to")
    private int weakToId = -1;

    @Column(name = "strong_to")
    private int strongToId = -1;

    public int getElementId(){
        return elementId;
    }

    public void setElementId(int id){
        elementId = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpriteURL() {
        return spriteURL;
    }

    public void setSpriteURL(String spriteURL) {
        this.spriteURL = spriteURL;
    }

    public int getWeakToId() {
        return weakToId;
    }

    public void setWeakToId(int weakToId) {
        this.weakToId = weakToId;
    }

    public int getStrongToId() {
        return strongToId;
    }

    public void setStrongToId(int strongToId) {
        this.strongToId = strongToId;
    }
}
