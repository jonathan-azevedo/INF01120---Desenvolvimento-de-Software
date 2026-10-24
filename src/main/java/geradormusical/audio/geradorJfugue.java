package geradormusical.audio;
import geradormusical.modelo.Musica;
import geradormusical.modelo.ConfiguracaoMusical;
import javax.sound.midi.Sequence;
import org.jfugue.pattern.Pattern;

import java.util.ArrayList;
import java.util.List;
import geradormusical.modelo.EventoMusical;
import geradormusical.modelo.TipoEvento;
import org.jfugue.player.Player;


public class geradorJfugue {
     
    double duracao;
    

    public Sequence Transformar(Musica musica){
        
        Pattern padrao = new Pattern();
        List<Long> posicoes = new ArrayList<>(musica.getAndamentos().keySet());
        for(int i = 0; i < posicoes.size();i++){
            long posicao = posicoes.get(i);
            int bpm = musica.getAndamentos().get(posicao);
            double posicaoJfugue = posicao / 64.0;
            padrao.add("@" + posicaoJfugue);
            padrao.add("T" + bpm);

        }
        for(int i = 0; i < ConfiguracaoMusical.QUANTIDADE_VOZES; i++){
            padrao.add("V" + i);
            padrao.add("@0");
            List<EventoMusical> eventos = musica.obterEventos(i);
            for(int j = 0; j < eventos.size();j++){
                 EventoMusical evento = eventos.get(j);
                 duracao = evento.duracao() / 64.0;
                 if(evento.tipo() == TipoEvento.PAUSA){
                    padrao.add("R/" + duracao);
                 }
                 else if(evento.tipo() == TipoEvento.NOTA){
                    int notatemp = evento.notaMidi();
                    int volumetemp = evento.volume();
                    int instrumentotemp = evento.instrumento();
                    padrao.add("I" + instrumentotemp);
                    padrao.add( notatemp + "/" + duracao + "a" + volumetemp);
                }
            }
        }

        Player tocador = new Player();
        Sequence sequencia_musical = tocador.getSequence(padrao);
        return sequencia_musical;
    }
}
