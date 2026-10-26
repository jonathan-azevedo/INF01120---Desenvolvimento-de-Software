package geradormusical.controle;

import geradormusical.interpretacao.InterpretadorTexto;
import geradormusical.audio.GeradorJfugue;
import geradormusical.modelo.ConfiguracaoMusical;
import geradormusical.modelo.Musica;
import javax.sound.midi.Sequence;

public class ControladorAplicacao {
    private final InterpretadorTexto interpretador;
    private final GeradorJfugue gerador;
    private GeracaoMusical ultimageracao;

    public ControladorAplicacao(InterpretadorTexto interpretador, GeradorJfugue gerador) {

        if (interpretador == null || gerador == null) {
            throw new IllegalArgumentException("Valores inválidos para interpretador e gerador");
        }

        this.interpretador = interpretador;
        this.gerador = gerador;
    }

    public GeracaoMusical gerarMusica(String texto, ConfiguracaoMusical config){

        Musica germusica = interpretador.interpretar(texto, config);
        Sequence gerseq = gerador.transformar(germusica);

        GeracaoMusical Nova_geracao =
            new GeracaoMusical(germusica, gerseq);

        ultimageracao = Nova_geracao;
        return Nova_geracao;
    }
    
    public GeracaoMusical getUltimaGeracao(){
        return ultimageracao;
    }
    
    public boolean temGeracao(){
        if(ultimageracao != null){
            return true;
        }
        return false;
    }
}