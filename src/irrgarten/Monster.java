/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author usuario
 */
public class Monster {
    private static final int INITIAL_HEALTH = 5;
    private static final int OUT_OF_BOUNDS = -1;
    
    private String name;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    
    public Monster(String name, float intelligence, float strength){
        this.name=name;
        this.intelligence=intelligence;
        this.strength=strength;
        row = OUT_OF_BOUNDS;
        col = OUT_OF_BOUNDS;
        health = INITIAL_HEALTH;
    }
    public boolean dead(){
        if(health==0){
            return true;
        }
            return false;
    }
    public float attack(){
        return Dice.intensity(strength);
    }
    public boolean defend(float receivedAttack){
        boolean isDead = dead();
        if(!isDead){
            float defensiveEnergy = Dice.intensity(intelligence);
            if(defensiveEnergy < receivedAttack){
                //Monster vivo y recive hit
                gotWounded();
                isDead=dead();
            }
        }
        return isDead;
    }
    public void setPos(int row, int col){
        this.row=row;
        this.col=col;
    }   
    
    @Override
    public String toString(){
        return name+"(M):["+"HP("+health+") STR("+strength+") "
                + "INT("+intelligence+") POS("+row+","+col+")]";
    }
    private void gotWounded(){
        --health;
    }
    
}