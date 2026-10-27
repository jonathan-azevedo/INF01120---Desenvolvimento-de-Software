package geradormusical.Ui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import geradormusical.controle.ControladorAplicacao;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.concurrent.ExecutionException;

import geradormusical.modelo.ConfiguracaoMusical;
import geradormusical.modelo.CatalogoInstrumentos;

public class TelaPrincipal {
    private JFrame janela;
    private JTextArea AreaTexto;
    private SpinnerNumberModel modeloBpm;
    private SpinnerNumberModel modeloSom;
    private SpinnerNumberModel modeloOitava;
    private JComboBox<CatalogoInstrumentos.Instrumento> seletorInstrumento;
    private CatalogoInstrumentos catalogo = new CatalogoInstrumentos();
    private JButton BotaoRestaurar;
    private JComboBox<String> seletorVozes;
    private int[] VolumeporVoz = new int[4];
    private int[] OitavaporVoz = new int[4];
    private int[] InstrumentoVoz = new int[4];
    private final ControladorAplicacao controlador;
    private JSpinner spinnerBpm;
    private JSpinner spinnerVolume;
    private JSpinner spinnerOitava;

    public TelaPrincipal(ControladorAplicacao controlador) {
        this.controlador = controlador;

        ConfiguracaoMusical config = ConfiguracaoMusical.configuracaoPadrao();
        for (int i = 0; i < ConfiguracaoMusical.QUANTIDADE_VOZES; i++) {
            VolumeporVoz[i] = config.getVolume(i);
            OitavaporVoz[i] = config.getOitava(i);
            InstrumentoVoz[i] = config.getInstrumento(i);
        }

        janela = new JFrame("Gerador Musical");
        janela.setBounds(0, 0, 900, 600);
        janela.setLocationRelativeTo(null);
        janela.setMinimumSize(new Dimension(900, 600));
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new BorderLayout(16, 16));
        painelPrincipal.setBackground(new Color(235, 238, 242));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        janela.setContentPane(painelPrincipal);
        JLabel titulo = new JLabel("Gerador Musical");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        painelPrincipal.add(titulo, BorderLayout.NORTH);

        AreaTexto = new JTextArea();
        AreaTexto.setBackground(Color.WHITE);
        AreaTexto.setForeground(new Color(35, 40, 50));
        AreaTexto.setFont(new Font("Monospaced", Font.PLAIN, 16));
        AreaTexto.setMargin(new Insets(12, 12, 12, 12));
        AreaTexto.setLineWrap(false);
        JScrollPane rolagemTexto = new JScrollPane(AreaTexto);

        JPanel painelTexto = new JPanel(new BorderLayout(0, 8));
        painelTexto.setOpaque(false);
        painelTexto.add(new JLabel("Digite seu texto"), BorderLayout.NORTH);
        painelTexto.add(rolagemTexto, BorderLayout.CENTER);
        painelPrincipal.add(painelTexto, BorderLayout.CENTER);

        String[] Vozes = {"Voz 0", "Voz 1", "Voz 2", "Voz 3"};
        seletorVozes = new JComboBox<>(Vozes);
        seletorVozes.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        seletorVozes.setBackground(Color.WHITE);
        JPanel painelVoz = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        painelVoz.setBackground(Color.WHITE);
        painelVoz.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        painelVoz.add(new JLabel("VOZ", JLabel.CENTER));
        painelVoz.add(seletorVozes);

        modeloBpm = new SpinnerNumberModel(config.getBpm(), ConfiguracaoMusical.BPM_MINIMO, ConfiguracaoMusical.BPM_MAXIMO, 10);
        spinnerBpm = new JSpinner(modeloBpm);
        spinnerBpm.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ((JSpinner.DefaultEditor) spinnerBpm.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        spinnerBpm.setPreferredSize(new Dimension(180, 32));
        JPanel painelbpm = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        painelbpm.setBackground(Color.WHITE);
        painelbpm.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        painelbpm.add(new JLabel("BPM", JLabel.CENTER));
        painelbpm.add(spinnerBpm);

        modeloSom = new SpinnerNumberModel(100, ConfiguracaoMusical.VOLUME_MINIMO, ConfiguracaoMusical.VOLUME_MAXIMO, 1);
        spinnerVolume = new JSpinner(modeloSom);
        spinnerVolume.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ((JSpinner.DefaultEditor) spinnerVolume.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        spinnerVolume.setPreferredSize(new Dimension(180, 32));
        JPanel painelsom = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        painelsom.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        painelsom.add(new JLabel("VOLUME", JLabel.CENTER));
        painelsom.add(spinnerVolume);
        painelsom.setBackground(Color.WHITE);

        modeloOitava = new SpinnerNumberModel(6, ConfiguracaoMusical.OITAVA_MINIMA, ConfiguracaoMusical.OITAVA_MAXIMA, 1);
        spinnerOitava = new JSpinner(modeloOitava);
        spinnerOitava.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        ((JSpinner.DefaultEditor) spinnerOitava.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        spinnerOitava.setPreferredSize(new Dimension(180, 32));
        JPanel painelOitava = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        painelOitava.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        painelOitava.add(new JLabel("OITAVA", JLabel.CENTER));
        painelOitava.add(spinnerOitava);
        painelOitava.setBackground(Color.WHITE);

        seletorInstrumento = new JComboBox<>(
            catalogo.obterInstrumentos().toArray(new CatalogoInstrumentos.Instrumento[0])
        );
        seletorInstrumento.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        seletorInstrumento.setBackground(Color.WHITE);
        seletorInstrumento.setSelectedItem(catalogo.obterPorNumeroPrograma(InstrumentoVoz[0]));
        JPanel painelInstrumento = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        painelInstrumento.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        painelInstrumento.add(new JLabel("INSTRUMENTO", JLabel.CENTER));
        painelInstrumento.add(seletorInstrumento);
        painelInstrumento.setBackground(Color.WHITE);
        JPanel painelConfiguracoes = new JPanel(new BorderLayout(0, 10));
        painelConfiguracoes.setPreferredSize(new Dimension(220, 0));
        painelConfiguracoes.setBackground(Color.WHITE);
        painelConfiguracoes.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        painelConfiguracoes.add(new JLabel("Parâmetros iniciais", JLabel.CENTER), BorderLayout.NORTH);
        painelPrincipal.add(painelConfiguracoes, BorderLayout.EAST);

        JPanel camposConfiguracao = new JPanel(new java.awt.GridLayout(0, 1, 0, 4));
        camposConfiguracao.setBackground(Color.WHITE);
        camposConfiguracao.add(painelVoz);
        camposConfiguracao.add(painelbpm);
        camposConfiguracao.add(painelsom);
        camposConfiguracao.add(painelOitava);
        camposConfiguracao.add(painelInstrumento);

        JPanel areaCampos = new JPanel(new BorderLayout());
        areaCampos.setBackground(Color.WHITE);
        areaCampos.add(camposConfiguracao, BorderLayout.NORTH);
        painelConfiguracoes.add(areaCampos, BorderLayout.CENTER);

        BotaoRestaurar = new JButton("Restaurar Padrões");
        painelConfiguracoes.add(BotaoRestaurar, BorderLayout.SOUTH);
        BotaoRestaurar.addActionListener(evento -> restaurarPadroes());

        seletorVozes.addActionListener(evento -> mostrarVozSelecionada());

        modeloSom.addChangeListener(evento -> {
            int VozSelecionada = seletorVozes.getSelectedIndex();
            VolumeporVoz[VozSelecionada] = modeloSom.getNumber().intValue();
        });

        modeloOitava.addChangeListener(evento -> {
            int VozSelecionada = seletorVozes.getSelectedIndex();
            OitavaporVoz[VozSelecionada] = modeloOitava.getNumber().intValue();
        });

        seletorInstrumento.addActionListener(evento -> {
            int VozSelecionada = seletorVozes.getSelectedIndex();
            CatalogoInstrumentos.Instrumento instrumentoSelecionado =
                (CatalogoInstrumentos.Instrumento) seletorInstrumento.getSelectedItem();
            if (instrumentoSelecionado != null) {
                InstrumentoVoz[VozSelecionada] = instrumentoSelecionado.numeroPrograma();
            }
        });

        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false);

        JButton botaoGerar = new JButton("Gerar música");
        JButton botaoReproduzir = new JButton("Reproduzir");
        JButton botaoPausar = new JButton("Pausar");
        JButton botaoRetomar = new JButton("Retomar");
        JButton botaoParar = new JButton("Parar");

        painelBotoes.add(botaoGerar);
        painelBotoes.add(botaoReproduzir);
        painelBotoes.add(botaoPausar);
        painelBotoes.add(botaoRetomar);
        painelBotoes.add(botaoParar);

        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        botaoReproduzir.setEnabled(false);
        botaoPausar.setEnabled(false);
        botaoRetomar.setEnabled(false);
        botaoParar.setEnabled(false);

        botaoGerar.addActionListener(evento -> {
            final String texto = AreaTexto.getText();
            final ConfiguracaoMusical configuracao;

            try {configuracao = obterConfiguracaoDaTela();} 
            catch (ParseException | IllegalArgumentException erro) {
                mostrarErro("Confira os valores dos parâmetros.");
                return;
            }

            botaoGerar.setEnabled(false);
            botaoReproduzir.setEnabled(false);
            botaoPausar.setEnabled(false);
            botaoRetomar.setEnabled(false);
            botaoParar.setEnabled(false);

            SwingWorker<Void, Void> tarefa = new SwingWorker<>(){
                @Override
                protected Void doInBackground(){
                    controlador.gerarMusica(texto, configuracao);
                    return null;
                }

                @Override
                protected void done() {
                    try {
                        get();
                        JOptionPane.showMessageDialog(janela, "Música gerada! Clique em Reproduzir.");
                    } 
                    catch (InterruptedException erro) {
                        Thread.currentThread().interrupt();
                        mostrarErro("A geração foi interrompida.");
                    } 
                    catch (ExecutionException erro) {
                        Throwable causa = erro.getCause();
                        mostrarErro(causa.getMessage());
                    } 
                    finally {
                        botaoGerar.setEnabled(true);

                        boolean temMusica = controlador.temGeracao();
                        botaoReproduzir.setEnabled(temMusica);
                        botaoPausar.setEnabled(temMusica);
                        botaoRetomar.setEnabled(temMusica);
                        botaoParar.setEnabled(temMusica);
                    }
                }
            };

            tarefa.execute();
        });

        botaoReproduzir.addActionListener(evento -> executarAcao(controlador::reproduzir));
        botaoPausar.addActionListener(evento -> executarAcao(controlador::pausar));
        botaoRetomar.addActionListener(evento -> executarAcao(controlador::retomar));
        botaoParar.addActionListener(evento -> executarAcao(controlador::parar));

        janela.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent evento) {
                controlador.encerrar();
            }
        });
    }

    private void restaurarPadroes() {
        ConfiguracaoMusical config = ConfiguracaoMusical.configuracaoPadrao();

        for (int i = 0; i < ConfiguracaoMusical.QUANTIDADE_VOZES; i++) {
            VolumeporVoz[i] = config.getVolume(i);
            OitavaporVoz[i] = config.getOitava(i);
            InstrumentoVoz[i] = config.getInstrumento(i);
        }

        modeloBpm.setValue(config.getBpm());
        mostrarVozSelecionada();
    }

    private void mostrarVozSelecionada() {
        int voz = seletorVozes.getSelectedIndex();

        modeloSom.setValue(VolumeporVoz[voz]);
        modeloOitava.setValue(OitavaporVoz[voz]);
        seletorInstrumento.setSelectedItem(catalogo.obterPorNumeroPrograma(InstrumentoVoz[voz]));
    }

    private ConfiguracaoMusical obterConfiguracaoDaTela() throws ParseException{

        spinnerBpm.commitEdit();
        spinnerVolume.commitEdit();
        spinnerOitava.commitEdit();

        return new ConfiguracaoMusical(
            modeloBpm.getNumber().intValue(),
            InstrumentoVoz,
            OitavaporVoz,
            VolumeporVoz
        );
    }

    public void MostrarTela() {
        janela.setVisible(true);
    }

    private void executarAcao(Runnable acao) {
        try {
            acao.run();
        } 
        catch (RuntimeException erro) {
            mostrarErro(erro.getMessage());
        }   
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(
            janela,
            mensagem == null ? "Não foi possível concluir a operação." : mensagem, 
            "Erro", 
            JOptionPane.ERROR_MESSAGE
        );
    }

}
