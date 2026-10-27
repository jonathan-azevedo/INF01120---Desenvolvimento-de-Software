package geradormusical;

import javax.swing.SwingUtilities;

import geradormusical.Ui.TelaPrincipal;
import geradormusical.arquivo.GerenciadorArquivo;
import geradormusical.audio.GeradorJfugue;
import geradormusical.audio.ReprodutorJfugue;
import geradormusical.controle.ControladorAplicacao;
import geradormusical.interpretacao.DicionarioComandos;
import geradormusical.interpretacao.InterpretadorTexto;

public class AplicacaoGeradorMusical {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterpretadorTexto interpretador =
                new InterpretadorTexto(new DicionarioComandos());

            GeradorJfugue gerador = new GeradorJfugue();
            GerenciadorArquivo arquivos = new GerenciadorArquivo();
            ReprodutorJfugue reprodutor = new ReprodutorJfugue();

            ControladorAplicacao controlador =
                new ControladorAplicacao(
                    interpretador,
                    gerador,
                    arquivos,
                    reprodutor
                );

            TelaPrincipal tela = new TelaPrincipal(controlador);
            tela.MostrarTela();
        });
    }
}