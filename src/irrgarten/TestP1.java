/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package irrgarten;
import irrgarten.UI.TextUI;
import irrgarten.controller.Controller;
/**
 *
 * @author usuario
 */
public class TestP1 {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        final int N_PLAYERS=1;
        
        TextUI vista = new TextUI();
        Game juego = new Game(N_PLAYERS);
        Controller controlador= new Controller(juego, vista);
        
        controlador.play();
    }
    
}
