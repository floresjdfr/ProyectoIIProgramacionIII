package cliente.vista;

import chat.control.ControlChat;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Marvin Aguilar
 */
public class VentanaAplicacion extends JFrame {

    public VentanaAplicacion(String titulo, ControlChat gestorPrincipal) throws HeadlessException {
        super(titulo);
        this.gestorPrincipal = gestorPrincipal;
        configurar();

    }

    private void configurar() {
        setIconImage(new ImageIcon(getClass().getResource("/images/chat.png")).getImage());
        ajustarComponentes(getContentPane());

        setResizable(false);
        setSize(480, 350);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void ajustarComponentes(Container c) {

        c.setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        
        panelCentral.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Inicio", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        
        btnLogin = new JButton("Log In");
        btnLogin.setPreferredSize(new Dimension(120, 40));
        
        btnRegistro = new JButton("Registrarse");
        btnRegistro.setPreferredSize(new Dimension(120, 40));
        
        btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(120, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(btnLogin, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(btnRegistro, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(btnSalir, gbc);
        

        c.add(panelCentral, BorderLayout.CENTER);
        
        btnLogin.addActionListener((ActionEvent e) -> {
            dispose();
            new VentanaLogIn("Log In", gestorPrincipal).init();
        });
        
        btnRegistro.addActionListener((ActionEvent e) -> {
            dispose();
            new VentanaRegistro("Registro", gestorPrincipal).init();
        });
        
        btnSalir.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
    }

    public void init() {
        setVisible(true);
    }
    
    private JButton btnLogin;
    private JButton btnRegistro;
    private JButton btnSalir;
    
    private final ControlChat gestorPrincipal;
}
