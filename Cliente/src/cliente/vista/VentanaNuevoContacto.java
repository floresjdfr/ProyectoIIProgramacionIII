/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente.vista;

import cliente.Controlador;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
 * @author Jose David
 */
public class VentanaNuevoContacto extends JFrame{

    public VentanaNuevoContacto(String titulo, Controlador gestorPrincipal){
        super(titulo);
        this.gestorPrincipal = gestorPrincipal;
        configurar();
    }
    
    private void configurar() {
        setIconImage(new ImageIcon(getClass().getResource("/images/chat.png")).getImage());
        ajustarComponentes(getContentPane());

        setResizable(false);
        setSize(380, 200);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void ajustarComponentes(Container c) {

        c.setLayout(new BorderLayout());

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        
        panelCentral.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Agregar Contacto", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        
        campoTextoUsuario = new JTextField();
        campoTextoUsuario.setPreferredSize(new Dimension(120, 20));
        
        btnAgregar = new JButton("Agregar Contacto");
        btnAgregar.setPreferredSize(new Dimension(120, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(new JLabel("Nombre usuario: "), gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(campoTextoUsuario, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panelCentral.add(btnAgregar, gbc);

        c.add(panelCentral, BorderLayout.CENTER);
        
        btnAgregar.addActionListener((ActionEvent e) -> {
            agregarContacto();
        });
    }

    public void init() {
        setVisible(true);
    }
    
    public void agregarContacto() {
        String usuario = campoTextoUsuario.getText();
        gestorPrincipal.agregarContacto(usuario);
        
        JOptionPane.showMessageDialog(this,"Contacto agregado!");
        dispose();
    }
    
    private JTextField campoTextoUsuario;
    private JButton btnAgregar;
    
    private final Controlador gestorPrincipal;
}
