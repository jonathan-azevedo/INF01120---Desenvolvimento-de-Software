package geradormusical.interpretacao;

import geradormusical.interpretacao.comandos.ComandoAlterarBpm;
import geradormusical.interpretacao.comandos.ComandoAlterarOitava;
import geradormusical.interpretacao.comandos.ComandoMusical;
import geradormusical.interpretacao.comandos.ComandoPausa;
import geradormusical.interpretacao.comandos.ComandoRepetirOuPausar;
import geradormusical.interpretacao.comandos.ComandoTrocarInstrumento;
import geradormusical.modelo.TempoMusical;

// Escolhe um comando a partir de um símbolo isolado do texto.
public class DicionarioComandos {
    public static DicionarioComandos padrao() {
        return new DicionarioComandos();
    }

    public ComandoMusical obterComando(char caractere) {
        if (caractere >= 'a' && caractere <= 'h') {
            return new ComandoPausa(TempoMusical.UNIDADES_POR_BATIDA);
        }

        if (caractere >= '0' && caractere <= '9') {
            int valor = caractere - '0';
            if (valor % 2 == 0) {
                return new ComandoTrocarInstrumento(valor, true);
            }
            return new ComandoTrocarInstrumento(15, false);
        }

        switch (caractere) {
            case '?':
            case '.':
                return new ComandoAlterarOitava(1);
            case 'V':
                return new ComandoAlterarOitava(-1);
            case '>':
                return new ComandoAlterarBpm(10);
            case '<':
                return new ComandoAlterarBpm(-10);
            case '!':
                return new ComandoTrocarInstrumento(24, false);
            case ';':
                return new ComandoTrocarInstrumento(15, false);
            case ',':
                return new ComandoTrocarInstrumento(114, false);
            default:
                return new ComandoRepetirOuPausar();
        }
    }
}
