package geradormusical.modelo;


public class EstadoDaVoz{
    
    
    private int instrumentoAtual;
    private int VolumeAtual;
    private int OitavaAtual;
    private final int OitavaBase;
    private EventoMusical UltimaNota;
    private boolean instrucaoAnteriorFoiNota;



    public EstadoDaVoz(int VolumeInicial,int instrumentoInicial,int Oitavainicial){
        AlterarVolume(VolumeInicial);
        AlterarInstrumento(instrumentoInicial);
        AlterarOitava(Oitavainicial);
  
        this.OitavaBase = Oitavainicial;
    }

    public EstadoDaVoz(int voz, ConfiguracaoMusical config){
    
        AlterarVolume(config.getVolume(voz));
        AlterarInstrumento(config.getInstrumento(voz));
        AlterarOitava(config.getOitava(voz));
        this.OitavaBase = config.getOitava(voz);
    }


    public int getInstrumentoAtual(){
        return instrumentoAtual;
    }

    public void AlterarInstrumento(int novoInstrumento){
        if (novoInstrumento < ConfiguracaoMusical.INSTRUMENTO_MINIMO || novoInstrumento > ConfiguracaoMusical.INSTRUMENTO_MAXIMO){
        throw new IllegalArgumentException("Instrumento fora do intervalo permitido");
        }
    
        this.instrumentoAtual = novoInstrumento;
    }

    public int getVolumeAtual(){
        return VolumeAtual;
    }

    public void AlterarVolume(int novoVolume){
        if (novoVolume < ConfiguracaoMusical.VOLUME_MINIMO || novoVolume > ConfiguracaoMusical.VOLUME_MAXIMO){
        throw new IllegalArgumentException("Volume fora do intervalo permitido");
        }
    
        this.VolumeAtual = novoVolume;
    }

    public int getOitavaAtual(){
        return OitavaAtual;
    }   

    public void AlterarOitava(int novaOitava){
        if(novaOitava < ConfiguracaoMusical.OITAVA_MINIMA || novaOitava > ConfiguracaoMusical.OITAVA_MAXIMA){
        throw new IllegalArgumentException("Oitava fora do intervalo permitido");
        }
        this.OitavaAtual = novaOitava;
    }

    public void VariarOitava(int variacao){
        int proxima_oitava = OitavaAtual + variacao;
        if (proxima_oitava <  ConfiguracaoMusical.OITAVA_MINIMA || proxima_oitava > ConfiguracaoMusical.OITAVA_MAXIMA){
        OitavaAtual = OitavaBase;
        }
        else{
            OitavaAtual = proxima_oitava;
        }
    }

    public EventoMusical getUltimaNota  (){
        return UltimaNota;
    }

    public void GuardarNota(EventoMusical NotaRecebida){
        if(NotaRecebida != null && NotaRecebida.tipo() == TipoEvento.NOTA){
        this.UltimaNota = NotaRecebida;
        instrucaoAnteriorFoiNota = true;
        }
    }

    public void OutraInstrucao(){
        instrucaoAnteriorFoiNota = false;
    }

    // Algumas letras repetem a última nota, quando vêm logo após uma nota
    // Verifica se a instrução anterior foi uma nota e se há uma nota guardada

    public boolean podeRepetirNota(){
        if(instrucaoAnteriorFoiNota == true && UltimaNota != null){
        return true;
        }
        return false;
    }

}