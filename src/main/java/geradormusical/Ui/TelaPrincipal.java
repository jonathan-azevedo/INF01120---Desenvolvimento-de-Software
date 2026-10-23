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

import geradormusical.modelo.ConfiguracaoMusical;

public class TelaPrincipal {
    
    private JFrame janela;
    private JTextArea AreaTexto;
    private SpinnerNumberModel modeloBpm;
    private SpinnerNumberModel modeloSom;
    private SpinnerNumberModel modeloOitava;

    public TelaPrincipal() {

        janela = new JFrame("Gerador Musical");
        janela.setBounds(0, 0, 900, 600);
        janela.setLocationRelativeTo(null);
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
        AreaTexto.setLineWrap(true);
        AreaTexto.setWrapStyleWord(true);
        JScrollPane rolagemTexto = new JScrollPane(AreaTexto);


        JPanel painelTexto = new JPanel(new BorderLayout(0, 8));
        painelTexto.setOpaque(false);
        painelTexto.add(new JLabel("Digite seu texto"), BorderLayout.NORTH);
        painelTexto.add(rolagemTexto, BorderLayout.CENTER);
        painelPrincipal.add(painelTexto, BorderLayout.CENTER);


        modeloBpm = new SpinnerNumberModel(120, ConfiguracaoMusical.BPM_MINIMO, ConfiguracaoMusical.BPM_MAXIMO, 10);
        JSpinner spinner = new JSpinner(modeloBpm);
        spinner.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        spinner.setPreferredSize(new Dimension(180, 32));
        JPanel painelbpm = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        painelbpm.setBackground(Color.WHITE);
        painelbpm.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        painelbpm.add(new JLabel("BPM", JLabel.CENTER));
        painelbpm.add(spinner);

        modeloSom = new SpinnerNumberModel(100, ConfiguracaoMusical.VOLUME_MINIMO, ConfiguracaoMusical.VOLUME_MAXIMO, 10);
        JSpinner spinnerVolume = new JSpinner(modeloSom);
        spinnerVolume.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        spinnerVolume.setPreferredSize(new Dimension(180, 32));
        JPanel painelsom = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        painelsom.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        painelsom.add(new JLabel("VOLUME", JLabel.CENTER));
        painelsom.add(spinnerVolume);
        painelsom.setBackground(Color.WHITE);

        modeloOitava = new SpinnerNumberModel(6, ConfiguracaoMusical.OITAVA_MINIMA, ConfiguracaoMusical.OITAVA_MAXIMA, 1);
        JSpinner spinnerOitava = new JSpinner(modeloOitava);
        spinnerOitava.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        spinnerOitava.setPreferredSize(new Dimension(180, 32));
        JPanel painelOitava = new JPanel(new java.awt.GridLayout(2, 1, 0, 3));
        painelOitava.setBorder(BorderFactory.createEmptyBorder(8, 20, 0, 20));
        painelOitava.add(new JLabel("OITAVA", JLabel.CENTER));
        painelOitava.add(spinnerOitava);
        painelOitava.setBackground(Color.WHITE);

     

        JPanel painelConfiguracoes = new JPanel(new BorderLayout());
        painelConfiguracoes.setPreferredSize(new Dimension(220, 0));
        painelConfiguracoes.setBackground(Color.WHITE);
        painelConfiguracoes.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        painelConfiguracoes.add( new JLabel("Parâmetros iniciais", JLabel.CENTER), BorderLayout.NORTH);
        painelPrincipal.add(painelConfiguracoes, BorderLayout.EAST);


        JPanel camposConfiguracao = new JPanel(new java.awt.GridLayout(0, 1, 0, 12));;
        camposConfiguracao.setBackground(Color.WHITE);
        camposConfiguracao.add(painelbpm);
        camposConfiguracao.add(painelsom);
        camposConfiguracao.add(painelOitava);

        JPanel areaCampos = new JPanel(new BorderLayout());
        areaCampos.setBackground(Color.WHITE);
        areaCampos.add(camposConfiguracao, BorderLayout.NORTH);
        painelConfiguracoes.add(areaCampos, BorderLayout.CENTER);




        }
    
        public void MostrarTela(){
     janela.setVisible(true);
    }
        
    // Ferramenta temporária para teste da interface sem executar o main principal
    public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(() -> {
    TelaPrincipal tela = new TelaPrincipal();
    tela.MostrarTela();
    });
    }
}
