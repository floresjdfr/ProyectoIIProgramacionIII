package chatusuario.vista;

import chat.control.ControlChat;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Marvin Aguilar
 */
public class VentanaRegistro extends JFrame {
    
    public VentanaRegistro(String titulo, ControlChat gestorPrincipal) throws HeadlessException {
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
                BorderFactory.createEtchedBorder(), "Registro", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        
        campoTextoUsuario = new JTextField();
        campoTextoUsuario.setPreferredSize(new Dimension(120, 20));
        
        campoTextoNombre = new JTextField();
        campoTextoNombre.setPreferredSize(new Dimension(120, 20));
        
        campoTextoPassword = new JPasswordField();
        campoTextoPassword.setPreferredSize(new Dimension(120, 20));
        
        btnRegistrarse = new JButton("Registrarse");
        btnRegistrarse.setPreferredSize(new Dimension(120, 30));
        
        btnRegresar = new JButton("Regresar");
        btnRegresar.setPreferredSize(new Dimension(120, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(new JLabel("Usuario: "), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(campoTextoUsuario, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(new JLabel("Nombre Completo: "), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(campoTextoNombre, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(new JLabel("Password: "), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(campoTextoPassword, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(btnRegistrarse, gbc);

        c.add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        panelInferior.add(btnRegresar);
        
        c.add(panelInferior, BorderLayout.PAGE_END);
        
        btnRegistrarse.addActionListener((ActionEvent e) -> {
            registrarUsuario();
        });
        
        btnRegresar.addActionListener((ActionEvent e) -> {
            volverInicio();
        });
    }

    public void init() {
        setVisible(true);
    }
    
    public void registrarUsuario() {
        String usuario = campoTextoUsuario.getText();
        String nombreCompleto = campoTextoNombre.getText();
        String password = String.valueOf(campoTextoPassword.getPassword());
        
        gestorPrincipal.agregar(usuario, nombreCompleto, password);
        
        JOptionPane.showMessageDialog(this,"Usuario registrado!");
        
        dispose();
        new VentanaChat("Chat de Texto", gestorPrincipal).init();
    }
    
    public void volverInicio() {
        dispose();
        new VentanaAplicacion("Chat", gestorPrincipal).init();
    }
    
    private JTextField campoTextoUsuario;
    private JTextField campoTextoNombre;
    private JPasswordField campoTextoPassword;
    private JButton btnRegistrarse;
    private JButton btnRegresar;
    
    private final ControlChat gestorPrincipal;
}
