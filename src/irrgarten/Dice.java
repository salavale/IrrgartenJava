/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package irrgarten;

/**
 *
 * @author usuario
 */
public class Dice {
    //Atributos de clase
    private static final int MAX_USES = 5; //(número máximo de usos de armas y escudos)
    private static final float MAX_INTELLIGENCE = 10.0f; //(valor máximo para la inteligencia de jugadores y monstruos)
    private static final float MAX_STRENGTH = 10.0f; //(valor máximo para la fuerza de jugadores y monstruos)
    private static final float RESURRECT_PROB = 0.3f; //(probabilidad de que un jugador sea resucitado en cada turno)
    private static final int WEAPONS_REWARD = 2; //(numero máximo de armas recibidas al ganar un combate)
    private static final int SHIELDS_REWARD = 3; //(numero máximo de escudos recibidos al ganar un combate)
    private static final int HEALTH_REWARD = 5; //(numero máximo de unidades de salud recibidas al ganar un combate)
    private static final int MAX_ATTACK = 3; //(máxima potencia de las armas)
    private static final int MAX_SHIELD = 2; //(máxima potencia de los escudos)

    private static java.util.Random generator = new java.util.Random();
    
    static int randomPos(int max){
        return generator.nextInt(max);
    }
    
    static int whoStarts(int nplayers){
        return generator.nextInt(nplayers);
    }
    
    static float randomIntelligence(){
        return MAX_INTELLIGENCE*generator.nextFloat();
    }
    
    static float randomStrength(){
        return MAX_STRENGTH*generator.nextFloat();
    }
    
    static boolean resurrectectPlayer(){
        if(generator.nextFloat()<RESURRECT_PROB){
            return true;
        }else return false;
    }
    
    static int weaponsReward(){
        return generator.nextInt(WEAPONS_REWARD);
    }
    
    static int shieldsReward(){
        return generator.nextInt(SHIELDS_REWARD);
    }
    
    static int healthReward(){
        return generator.nextInt(HEALTH_REWARD);
    }
    
    static float weaponPower(){
        return MAX_ATTACK*generator.nextFloat();
    }
    
    static float shieldPower(){
        return MAX_SHIELD*generator.nextFloat();
    }
    
    static int usesLeft(){
        return generator.nextInt(MAX_USES);
    }
    
    static float intensity(float competence){
        return competence*generator.nextFloat();
    }
    
    static boolean discardElement(int usesLeft){
        if(usesLeft>=MAX_USES*generator.nextFloat()){
            return false;
        }else return true;
    }
}   
    

