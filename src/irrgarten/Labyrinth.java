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
public class Labyrinth {
    private static final char BLOCK_CHAR='x';
    private static final char EMPTY_CHAR='-';
    private static final char MONSTER_CHAR='M';
    private static final char COMBAT_CHAR='C';
    private static final char EXIT_CHAR='E';
    private static final int ROW=0;
    private static final int COL=1;
    private static final int OUT_OF_BOUNDS = 0;
    
    private int nRows;
    private int nCols;
    private int exitRow;
    private int exitCol;
    private Player[][] labPlayer;
    private Monster[][] labMonster;
    private char[][] labSquare;
    
    
    public Labyrinth(int nRows, int nCols, int exitRow, int exitCol){
        this.nRows=nRows;
        this.nCols=nCols;
        this.exitRow=exitRow;
        this.exitCol=exitCol;
        labPlayer=new Player[nRows][nCols];
        labMonster=new Monster[nRows][nCols];
        labSquare=new char[nRows][nCols];
        for(int i=0; i<nRows;++i){
            for(int j=0; j<nCols;++j){
                labSquare[i][j]=EMPTY_CHAR;
            }
        }
        labSquare[exitRow][exitCol]=EXIT_CHAR;
    }
    public void spreadPlayers(ArrayList<Player> players){
        for(int i=0; i<players.size();++i){
            int[] pos=randomEmptyPos();
            putPlayer2D(OUT_OF_BOUNDS,OUT_OF_BOUNDS, pos[ROW], pos[COL], players.get(i));
        }
    }
    public boolean haveAWinner(){
        if(labPlayer[exitRow][exitCol]!=null){
        return true;
        }
        return false;
    }
    @Override
    public String toString(){
        String map="";
        for(int i=0; i<nRows;++i){
            for(int j=0; j<nCols;++j){
                map +=labSquare[i][j]+" ";
            }
            map+="\n";
        }
        return map;
    }
    public void addMonster(int row, int col, Monster monster){
        if((row<nRows)&&(col<nCols)){
           labMonster[row][col]=monster;
           labSquare[row][col]=MONSTER_CHAR;
        }
    }
    public Monster putPlayer(Directions direction, Player player){
        int oldRow=player.getRow();
        int oldCol=player.getCol();
        int newPos[]=dir2Pos(oldRow, oldCol, direction);
        Monster monster=putPlayer2D(oldRow, oldCol, newPos[ROW], newPos[COL], player);
        
        return monster;
    }
    public void addBlock(Orientation orientation, int startRow, int startCol, int length){
        int incRow, incCol;
        if(orientation==Orientation.VERTICAL){
            incRow=1;
            incCol=0;
        }else{
            incRow=0;
            incCol=1;
        }

        int row=startRow;
        int col=startCol;

        while((posOK(row,col)&&emptyPos(row,col))&&(length>0)){
            labSquare[row][col]=BLOCK_CHAR;
            length--;
            row+=incRow;
            col+=incCol;
        }
    }
    public ArrayList<Directions> validMoves(int row, int col){
        ArrayList<Directions> output= new ArrayList<Directions>();
        if(canStepOn(row+1,col)){
            output.add(Directions.DOWN);
        }
        if(canStepOn(row-1,col)){
            output.add(Directions.UP);
        }
        if(canStepOn(row,col+1)){
            output.add(Directions.RIGHT);
        }
        if(canStepOn(row,col-1)){
            output.add(Directions.LEFT);
        }
        return output;
    }
    private boolean posOK(int row, int col){
        return (row<nRows)&&(col<nCols);
    }
    private boolean emptyPos(int row, int col){
        if(labSquare[row][col]==EMPTY_CHAR){
            return true;
        }
        return false;
    }
    private boolean monsterPos(int row, int col){
        if(labSquare[row][col]==MONSTER_CHAR){
            return true;
        }
        return false;
    }
    private boolean exitPos(int row, int col){
        if(labSquare[row][col]==EXIT_CHAR){
            return true;
        }
        return false;
    }
    private boolean combatPos(int row, int col){
        if(labSquare[row][col]==COMBAT_CHAR){
            return true;
        }
        return false;
    }
    private boolean canStepOn(int row, int col){
        if(posOK(row, col)){
            if((emptyPos(row,col))||(monsterPos(row,col))||(exitPos(row,col))){
                return true;
            }
        }
        return false;
    }
    private void updateOldPos(int row, int col){
        if(posOK(row,col)){
            if(combatPos(row,col)){
                labSquare[row][col]=MONSTER_CHAR;
            }else{
                labSquare[row][col]=EMPTY_CHAR;
            }
        }
    }
    private int[] dir2Pos(int row, int col, Directions direction){
        int[] posFin={row,col};
        switch(direction){
            case LEFT:
                posFin[ROW]=row;
                posFin[COL]=col-1;
                break;
            case RIGHT:
                posFin[ROW]=row;
                posFin[COL]=col+1;
                break;
            case UP:
                posFin[ROW]=row-1;
                posFin[COL]=col;
                break;
            case DOWN:
                posFin[ROW]=row+1;
                posFin[COL]=col;
                break;
        }
        return posFin;
    }
    private int[] randomEmptyPos(){
        int[] posRandom={ROW,COL};
        do{
            posRandom[ROW]=Dice.randomPos(nRows);
            posRandom[COL]=Dice.randomPos(nCols);
        }while(!emptyPos(posRandom[ROW],posRandom[COL]));
        return posRandom;
    }
    private Monster putPlayer2D(int oldRow, int oldCol, int row, int col, Player player){
        Monster output = null;
        if(canStepOn(row,col)){
            if(posOK(oldRow,oldCol)){
                if(labPlayer[oldRow][oldCol]==player){
                    updateOldPos(oldRow, oldCol);
                    labPlayer[oldRow][oldCol]=null;
                }
            }
            boolean monsterPos=monsterPos(row, col);
            if(monsterPos){
                labSquare[row][col]=COMBAT_CHAR;
                output=labMonster[row][col];
            }else{
                char number=(char)('0'+player.getNumber());
                labSquare[row][col]=number;
            }
            labPlayer[row][col]=player;
            player.setPos(row, col);
        }
        return output;
    }
}
