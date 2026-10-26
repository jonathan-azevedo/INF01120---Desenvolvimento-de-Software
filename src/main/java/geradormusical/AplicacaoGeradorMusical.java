package geradormusical;
import geradormusical.audio.ReprodutorJfugue;
import javax.sound.midi.Sequence;
import org.jfugue.player.Player;

public class AplicacaoGeradorMusical {
    public static void main(String[] args) {
        //Teste do Jfugue
        Player player = new Player();
        Sequence sequencia = player.getSequence("T120 C5q D5q E5q F5q G5q A5q B5q C6q");
        ReprodutorJfugue reprodutor = new ReprodutorJfugue();
        IO.println(reprodutor.obterEstado());
        reprodutor.carregar(sequencia);
        IO.println(reprodutor.obterEstado());
        reprodutor.iniciar();
        IO.println(reprodutor.obterEstado());
    }
}