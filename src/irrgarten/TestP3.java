/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten;

/**
 *
 * @author usuario
 */
public class TestP3 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args){ 
        //Prueba weapon
        Weapon lanza=new Weapon(3.1f,2);
        System.out.println("Lanza:"+lanza.toString());
        System.out.println("Ataca");
        lanza.attack();
        System.out.println("Lanza:"+lanza.toString());
        if(lanza.discard()){
            System.out.println("Me han descartado");
        }else{
            System.out.println("No me han descartado");
        }
        
        //Prueba shield
        Shield escudo=new Shield(3.1f,4);
        System.out.println("\nEscudo:"+escudo.toString());
        System.out.println("Defiende");
        escudo.protect();
        System.out.println("Escudo:"+escudo.toString());
        if(escudo.discard()){
            System.out.println("Me han descartado");
        }else{
            System.out.println("No me han descartado");
        }
        
        //Prueba GameState
        String labyrinth = "laberinto 1";
        String players = "Pepe Jose Juan María Laura";
        String monsters = "zombie esqueleto jefeFinal";
        int currentPlayer = 0;
        boolean winner = false;
        String log = "vacio";
        GameState tablero=new GameState(labyrinth, players, monsters,
            currentPlayer, winner, log);
        
        System.out.println("\nCaracterísticas del juego:\n"+
                "laberinto: "+tablero.getLabyrinth()+"\n"+
                "jugadores: "+tablero.getPlayers()+"\n"+
                "enemigos: "+tablero.getMonsters()+"\n"+
                "jugador actual: "+Integer.toString(tablero.getCurrentPlayer())+"\n"+
                "juego terminado: "+tablero.getWinner()+"\n"+
                "Eventos especiales: "+tablero.getLog()+"\n");
        
        //Prueba enums
        Orientation mirada = Orientation.HORIZONTAL;
        GameCharacter zombie = GameCharacter.MONSTER;
        Directions mover = Directions.LEFT;
        
        //Prueba Dice
        int contadorCero=0;
        int contadorMax=0;
        int contadorPromedio=0;
        
        int contadorRevivido=0;
        
        for(int i=0; i<100; i++){
            if(Dice.discardElement(0)){
                contadorCero++;
            }
            if(Dice.discardElement(5)){
                contadorMax++;
            }
            if(Dice.discardElement(3)){
                contadorPromedio++;
            }
            
            if(Dice.resurrectectPlayer()){
                contadorRevivido++;
            }
        }
        float resultadoCero= contadorCero/(float)100;
        float resultadoMax= contadorMax/(float)100;
        float resultadoPromedio= contadorPromedio/(float)100;
        
        System.out.println("Prob de descarte de 0: "+Float.toString(resultadoCero));
        System.out.println("Prob de descarte del Max: "+Float.toString(resultadoMax));
        System.out.println("Prob de descarte de 3: "+Float.toString(resultadoPromedio));
        
        float resultadoRevivir= contadorRevivido/(float)100;
        
        System.out.println("Prob de revivir: "+Float.toString(resultadoRevivir));
        
        int posMax=32;
        int numJugadores=4;
        float competencia=4.3f;
        for(int i=0; i<100; i++){
            int posicion=Dice.randomPos(posMax);
            if(0>posicion||posicion>posMax){
                System.out.println("Error en la posición aleatoria");
            }
            int empieza=Dice.whoStarts(numJugadores);
            if(0>empieza||empieza>5){
                System.out.println("Error en quién empieza");
            }
            float inteligencia=Dice.randomIntelligence();
            if(0>inteligencia||inteligencia>=10.0f){
                System.out.println("Error en inteligencia");
            }
            float fuerza=Dice.randomStrength();
            if(0>fuerza||fuerza>10.0f){
                System.out.println("Error en la recompensa de salud");
            }
            int recompensaArma=Dice.weaponsReward();
            if(0>recompensaArma||recompensaArma>2){
                System.out.println("Error en la recompensa de armas");
            }
            int recompensaEscudo=Dice.shieldsReward();
            if(0>recompensaEscudo||recompensaEscudo>3){
                System.out.println("Error en la recompensa de escudos");
            }
            int recompensaSalud=Dice.healthReward();
            if(0>recompensaSalud||recompensaSalud>5){
                System.out.println("Error en la recompensa de salud");
            }
            float poderArma=Dice.weaponPower();
            if(0>poderArma||poderArma>3){
                System.out.println("Error en el poder de ataque");
            }
            float poderEscudo=Dice.shieldPower();
            if(0>poderEscudo||poderEscudo>2){
                System.out.println("Error en el poder de defensa");
            }
            int usos=Dice.usesLeft();
            if(0>usos||usos>5){
                System.out.println("Error en asignacion de usos");
            }
            float intensidad=Dice.intensity(competencia);
            if(0>intensidad||intensidad>competencia){
                System.out.println("Error en la intensidad");
            }
        }

    }
    
}
