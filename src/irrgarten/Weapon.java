package irrgarten;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author usuario
 */
public class Weapon {
    
    private float power;
    private int uses;

    public Weapon(float p, int u){
        power=p;
        uses=u;
    }
    
    public float attack(){
        if(uses>0){
            uses--;
            return power;
        }else return 0;
    }
    
    public String toString (){
        return "W["+power+", "+uses+"]";
    }
    
    public boolean discard(){
        return Dice.discardElement(uses);
    }
}
