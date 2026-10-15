package geradormusical;

import org.jfugue.player.Player;

public class AplicacaoGeradorMusical {
    public static void main(String[] args) {
        //Teste do Jfugue
        Player player = new Player();
        player.play("T120 C5q D5q E5q");
    }
}