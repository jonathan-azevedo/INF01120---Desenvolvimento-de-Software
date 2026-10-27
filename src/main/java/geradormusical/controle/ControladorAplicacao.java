package geradormusical.controle;

import geradormusical.arquivo.GerenciadorArquivo;
import geradormusical.interpretacao.InterpretadorTexto;
import geradormusical.audio.GeradorJfugue;
import geradormusical.modelo.ConfiguracaoMusical;
import geradormusical.modelo.Musica;
import geradormusical.audio.ReprodutorAudio;
import geradormusical.audio.EstadoReproducao;

import java.io.IOException;
import java.nio.file.Path;

import javax.sound.midi.Sequence;

public class ControladorAplicacao {
    private final InterpretadorTexto interpretador;
    private final GeradorJfugue gerador;
    private GeracaoMusical ultimageracao;
    private final ReprodutorAudio reprodutor;
    private final GerenciadorArquivo arquivos;
    


    public ControladorAplicacao(InterpretadorTexto interpretador, GeradorJfugue gerador,GerenciadorArquivo arquivos,ReprodutorAudio reprodutor) {

        if (interpretador == null || gerador == null || arquivos == null || reprodutor == null) {
        throw new IllegalArgumentException("Interpretador, gerador, arquivos e reprodutor são obrigatórios");
        }

        this.interpretador = interpretador;
        this.gerador = gerador;
        this.arquivos = arquivos;
        this.reprodutor = reprodutor;
    }

    public GeracaoMusical gerarMusica(String texto, ConfiguracaoMusical config){

        Musica germusica = interpretador.interpretar(texto, config);
        Sequence gerseq = gerador.transformar(germusica);

        GeracaoMusical Nova_geracao = new GeracaoMusical(germusica, gerseq);

        reprodutor.carregar(Nova_geracao.getSequencia());
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
    
    public String abrirTexto(Path caminho) throws IOException {
        return arquivos.lerTexto(caminho);
    }

    public void salvarTexto(String texto, Path caminho) throws IOException {
        arquivos.salvarTexto(texto, caminho);
    }

    public void exportarMidi(Path caminho) throws IOException {
        if (ultimageracao == null) {
         throw new IllegalStateException("Necessário criar uma música antes");
        }

        Sequence novaseq = ultimageracao.getSequencia();
        arquivos.salvarMidi(novaseq, caminho);
    }

    public  void reproduzir(){
        if(ultimageracao == null){
         throw new IllegalStateException("Necessário criar uma música antes");
        }
        reprodutor.carregar(ultimageracao.getSequencia());
        reprodutor.iniciar();
    }

    public void pausar() {
        reprodutor.pausar();
    }

    public void retomar() {
        reprodutor.retomar();
    }

    public void parar() {
        reprodutor.parar();
    }

    public void encerrar() {
        reprodutor.fechar();
    }
    
    public EstadoReproducao obterEstadoReproducao() {
        return reprodutor.obterEstado();
    }

}