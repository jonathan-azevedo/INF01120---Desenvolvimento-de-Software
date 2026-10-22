package geradormusical.Ui;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class TelaPrincipal {
    
    private JFrame janela;
    private JTextArea AreaTexto;

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


        JPanel painelConfiguracoes = new JPanel(new BorderLayout());
        painelConfiguracoes.setPreferredSize(new Dimension(220, 0));
        painelConfiguracoes.setBackground(Color.WHITE);
        painelConfiguracoes.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        painelConfiguracoes.add( new JLabel("Parâmetros iniciais", JLabel.CENTER), BorderLayout.NORTH);
        painelPrincipal.add(painelConfiguracoes, BorderLayout.EAST);
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
