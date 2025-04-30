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
public class Game {
    private static final int MAX_ROUNDS=10;
    private static final int N_ROWS=15;
    private static final int N_COLS=15;
    private static final int EXIT_ROW=5;
    private static final int EXIT_COL=14;
    
    
    
    private int currentPlayerIndex;
    private String log;
    private ArrayList<Monster> monsters;
    private ArrayList<Player> players;
    private Player currentPlayer;
    private Labyrinth labyrinth;
    
    public Game(int nplayers){
        players= new ArrayList<Player>();
        for(int i=0; i<nplayers; ++i){
            Player aux=new Player((char)i,Dice.randomIntelligence(), Dice.randomStrength());
            players.add(aux);
        }
        currentPlayerIndex = Dice.whoStarts(nplayers);
        currentPlayer = players.get(currentPlayerIndex);
        
        monsters = new ArrayList<Monster>();
        labyrinth=new Labyrinth(N_ROWS, N_COLS, EXIT_ROW, EXIT_COL);
        
        configureLabyrinth();
        labyrinth.spreadPlayers(players);
        log="Game started\n";
    }
    
    public boolean finished(){
        return labyrinth.haveAWinner();
    }
    
    public boolean nextStep(Directions preferredDirection){
        log="";
        if(!currentPlayer.dead()){
            Directions direction=actualDirection(preferredDirection);
            if(direction!=preferredDirection){
                logPlayerNoOrders();
            }
            Monster monster=labyrinth.putPlayer(direction, currentPlayer);
            if(monster==null){
                logNoMonster();
            }else{
                GameCharacter winner=combat(monster);
                manageReward(winner);
            }
        }else{
            manageResurrection();
        }
        boolean endGame=finished();
        if(!endGame){
            nextPlayer();
        }
        return endGame;
    }
    
    public GameState getGameState(){
        String jugadores="";
        String monstruos="";
        
        for(int i=0; i<players.size();++i){
            jugadores+="- "+players.get(i).toString()+"\n";
        }
        for(int i=0; i<monsters.size();++i){
            monstruos+="- "+monsters.get(i).toString()+"\n";
        }
        
        GameState todo= new GameState(labyrinth.toString(), jugadores, monstruos, currentPlayerIndex, finished(), log);
        return todo;
    }
    
    private void configureLabyrinth(){
           labyrinth.addBlock(Orientation.HORIZONTAL, 0, 0, N_ROWS);
           labyrinth.addBlock(Orientation.HORIZONTAL, 14, 0, N_ROWS);
           labyrinth.addBlock(Orientation.VERTICAL, 1, 14, N_COLS);
           labyrinth.addBlock(Orientation.VERTICAL,6 , 14, N_COLS);
           labyrinth.addBlock(Orientation.VERTICAL,1 , 0, N_COLS);
           labyrinth.addBlock(Orientation.VERTICAL,1 , 3, 2);
           labyrinth.addBlock(Orientation.VERTICAL,2,2,3);
           labyrinth.addBlock(Orientation.VERTICAL,6,2,4);
           labyrinth.addBlock(Orientation.VERTICAL,11,2,3);
           labyrinth.addBlock(Orientation.VERTICAL,6 , 3, 2);
           labyrinth.addBlock(Orientation.VERTICAL,9 , 3, 1);
           labyrinth.addBlock(Orientation.VERTICAL,11 , 3, 1);
           Monster esqueleto= new Monster("esqueleto", 4,1);
           monsters.add(esqueleto);
           labyrinth.addMonster(5,5,esqueleto);
           Monster zombie= new Monster("zombie", 2,3);
           monsters.add(zombie);
           labyrinth.addMonster(10,10,zombie);
           monsters.add(zombie);
           labyrinth.addMonster(13,1,zombie);
    }
    private void nextPlayer(){
        players.set(currentPlayerIndex, currentPlayer); //Guardamos la información del jugador actual
        currentPlayerIndex=(currentPlayerIndex+1)%players.size(); //Avanzamos al siguiente jugador
        currentPlayer = players.get(currentPlayerIndex); //Cambiamos al jugador actual
    }
    
    private Directions actualDirection(Directions preferredDirection){
        int currentRow=currentPlayer.getRow();
        int currentCol=currentPlayer.getCol();
        ArrayList<Directions> validMoves=labyrinth.validMoves(currentRow, currentCol);
        return currentPlayer.move(preferredDirection, validMoves);
    }
    
    private GameCharacter combat(Monster monster){
        int rounds=0;
        GameCharacter winner=GameCharacter.PLAYER;
        float playerAttack=currentPlayer.attack();
        boolean lose=monster.defend(playerAttack);
        
        while((!lose)&&(rounds<MAX_ROUNDS)){
            winner=GameCharacter.MONSTER;
            rounds++;
            float monsterAttack=monster.attack();
            lose=currentPlayer.defend(monsterAttack);
            if(!lose){
                playerAttack=currentPlayer.attack();
                winner=GameCharacter.PLAYER;
                lose=monster.defend(playerAttack);
            }
        }
        logRounds(rounds, MAX_ROUNDS);
        return winner;
    }
    
    private void manageReward(GameCharacter winner){
        if(winner==GameCharacter.PLAYER){
            currentPlayer.receiveReward();
            logPlayerWon();
        }else{
            logMonsterWon();
        }
    }
    
    private void manageResurrection(){
        boolean resurrect=Dice.resurrectectPlayer();
        if(resurrect){
            currentPlayer.resurrect();
            logResurrected();
        }else{
            logPlayerSkipTurn();
        }
    }
    
    private void logPlayerWon(){
        log+= "P"+currentPlayer.getNumber()+" won vs M\n";
    }
    
    private void logMonsterWon(){
        log+= "M won vs P"+currentPlayer.getNumber()+"\n";
    }
    
    private void logResurrected(){
        log+="P"+currentPlayer.getNumber()+" has resurrected\n";
    }
    
    private void logPlayerSkipTurn(){
        log+="P"+currentPlayer.getNumber()+" lost its turn for being dead\n";
    }
    
    private void logPlayerNoOrders(){
        log+="P"+currentPlayer.getNumber()+" could not follow the order\n";    
    }
    
    private void logNoMonster(){
        log+="P"+currentPlayer.getNumber()+" moved to a emptycell or could not move\n";
    }
    
    private void logRounds(int rounds, int max){
        log+="there has been "+rounds+" out of "+max+" combat rounds\n";
    }
}
