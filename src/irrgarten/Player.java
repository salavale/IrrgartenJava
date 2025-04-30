/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;
import java.util.ArrayList;
/**
 *
 * @author usuario
 */
public class Player {
    private static final int MAX_WEAPONS=2;
    private static final int MAX_SHIELDS=3;
    private static final int INITIAL_HEALTH=10;
    private static final int HITS2LOSE=3;
    private static final int OUT_OF_BOUNDS = -1;
        
    private String name;
    private ArrayList<Weapon> weapons;
    private ArrayList<Shield> shields;
    private char number;
    private float intelligence;
    private float strength;
    private float health;
    private int row;
    private int col;
    private int consecutiveHits;
    
    public Player(char number, float intelligence, float strength){    
        this.number=number;
        name="Player"+number;
        this.intelligence=intelligence;
        this.strength=strength;
        health=INITIAL_HEALTH;
        consecutiveHits=0;
        weapons = new ArrayList<Weapon>();
        shields = new ArrayList<Shield>();
        row = col = OUT_OF_BOUNDS;
    }

    public void resurrect(){
        weapons.clear();
        shields.clear();
        health=INITIAL_HEALTH;
        consecutiveHits=0;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public char getNumber(){
        return number;
    }

    public void setPos(int row, int col){
        this.row=row;
        this.col=col;
    }

    public boolean dead(){
        if(health==0){
            return true;
        }
        return false;
    }

    public Directions move(Directions direction, ArrayList<Directions> validMoves){
        int size = validMoves.size();
        boolean contained = validMoves.contains(direction);
        if((size>0)&&(!contained)){
            return validMoves.get(0);
        }else{
            return direction;
        }
    }

    public float attack(){
       return strength+sumWeapons();
    }

    public boolean defend(float receivedAttack){
        return manageHit(receivedAttack);
    }

    public void receiveReward(){
        int wReward=Dice.weaponsReward();
        int sReward=Dice.shieldsReward();
        for(int i=0; i<wReward; ++i){
            receiveWeapons(newWeapon());
        }
        for(int i=0; i<sReward; ++i){
            receiveShields(newShield());
        }
        health+=Dice.healthReward();
    }

    @Override
    public String toString(){
        String str=name+"(P):["+"HP("+health+") STR("+strength+") "
                + "INT("+intelligence+") POS("+row+","+col+")]";
        str+="\n\tWeapons[";
        for (int i = 0; i < weapons.size(); i++) {
            str+=weapons.get(i).toString();
            if (i != weapons.size() - 1) str+=",";
        }
        str+="]";
        str+="\n\tShields[";
        for (int i = 0; i < shields.size(); i++) {
            str+=shields.get(i).toString();
            if (i != shields.size() - 1) str+=",";
        }
        str+="]";
        return str;
    }

    private void receiveWeapons(Weapon w){
        for(int i=0; i<weapons.size();++i){
            if(weapons.get(i).discard()){
                weapons.remove(i);
                --i;
            }
        }
        if(weapons.size()<MAX_WEAPONS){
            weapons.add(w);
        }
    }

    private void receiveShields(Shield s){
        for(int i=0; i<shields.size();++i){
            if(shields.get(i).discard()){
                shields.remove(i);
                --i;
            }
        }
        if(shields.size()<MAX_SHIELDS){
            shields.add(s);
        }
    }

    private Weapon newWeapon(){
        return new Weapon(Dice.weaponPower(),Dice.usesLeft());
    }

    private Shield newShield(){
        return new Shield(Dice.shieldPower(), Dice.usesLeft());
    }

    private float sumWeapons(){
        float sum=0;
        for(int i=0; i<weapons.size();++i){
            sum+=weapons.get(i).attack();
        }
        return sum;
    }

    private float sumShields(){
        float sum=0;
        for(int i=0; i<shields.size();++i){
            sum+=shields.get(i).protect();
        }
        return sum;
    }

    private float defensiveEnergy(){
        return intelligence+sumShields();
    }

    private boolean manageHit(float receivedAttack){
        boolean lose;
        float defense = defensiveEnergy();
        if(defense<receivedAttack){
            gotWounded();
            incConsecutiveHits();
        }else{
            resetHits();
        }
        
        if((consecutiveHits==HITS2LOSE)||dead()){
            resetHits();
            lose=true;
        }else{
            lose=false;
        }
        return lose;
    }

    private void resetHits(){
        consecutiveHits=0;
    }

    private void gotWounded(){
        --health;
    }

    private void incConsecutiveHits(){
        ++consecutiveHits;
    }
}
