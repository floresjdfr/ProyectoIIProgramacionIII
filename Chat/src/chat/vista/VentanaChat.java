package chat.vista;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Marvin Aguilar
 */
public class VentanaChat extends JFrame {
    
    public VentanaChat(String titulo) throws HeadlessException {
        super(titulo);
        //this.gestorPrincipal = gestorPrincipal;
        configurar();

    }

    private void configurar() {
        setIconImage(new ImageIcon(getClass().getResource("/images/chat.png")).getImage());
        ajustarComponentes(getContentPane());

        setResizable(false);
        setSize(800, 570);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void ajustarComponentes(Container c) {

        c.setLayout(new BorderLayout());
        
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.X_AXIS));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        
        btnLogOut = new JButton("Log Out");
        btnLogOut.setFocusable(false);
        btnAgregarContacto = new JButton("+ Agregar Contacto");
        btnAgregarContacto.setFocusable(false);
        listaEstado = new JComboBox<>();
        listaEstado.setPreferredSize(new Dimension(120, 20));
        listaEstado.setMaximumSize(new Dimension(120, 20));
        listaEstado.setFocusable(false);
        
        String[] estadoContactos = {"Todos", "Online", "Offline"};
        listaEstado.setModel(new DefaultComboBoxModel<>(estadoContactos));
        
        panelSuperior.add(new JLabel("Usuario: Bartolomeo"));
        panelSuperior.add(Box.createHorizontalGlue());
        panelSuperior.add(btnLogOut);
        panelSuperior.add(Box.createRigidArea(new Dimension(8, 0)));
        panelSuperior.add(btnAgregarContacto);
        panelSuperior.add(Box.createRigidArea(new Dimension(5, 0)));
        panelSuperior.add(new JLabel("Filtrar Contactos: "));
        panelSuperior.add(Box.createRigidArea(new Dimension(5, 0)));
        panelSuperior.add(listaEstado);
        
        c.add(panelSuperior, BorderLayout.PAGE_START);

        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new GridBagLayout());
        
        panelCentral.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Chat", TitledBorder.LEFT, TitledBorder.DEFAULT_POSITION));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        panelCentral.add(new JTextArea(), gbc);

        c.add(panelCentral, BorderLayout.CENTER);
        
        JPanel panelContactos = new JPanel();
        panelContactos.setLayout(new FlowLayout(FlowLayout.LEFT));
            
        String[] nombreColumnas = {"Usuario", "Última Vez"};

        modeloTabla = new DefaultTableModel(nombreColumnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaListaContactos = new JTable(modeloTabla);
        tablaListaContactos.getTableHeader().setReorderingAllowed(false);
        tablaListaContactos.setRowHeight(20);
        scrollPanelTabla = new JScrollPane(tablaListaContactos);
        
        scrollPanelTabla.setPreferredSize(new Dimension(350, 440));
        
        panelContactos.add(scrollPanelTabla);
        
        c.add(panelContactos, BorderLayout.LINE_END);
        
        JPanel panelInferior = new JPanel();
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.X_AXIS));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(10, 8, 10, 8));
        
        JTextField campoMensaje = new JTextField();
        campoMensaje.setPreferredSize(new Dimension(300, 20));
        campoMensaje.setMaximumSize(new Dimension(300, 20));
        btnEnviarMensaje = new JButton("Enviar Mensaje");
        
        panelInferior.add(campoMensaje);
        panelInferior.add(Box.createRigidArea(new Dimension(10, 0)));
        panelInferior.add(btnEnviarMensaje);
        
        c.add(panelInferior, BorderLayout.PAGE_END);
    }

    public void init() {
        setVisible(true);
    }

    /*
    public ControlAplicacion getGestorPrincipal() {
        return gestorPrincipal;
    }
    */
    
    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPanelTabla;
    private JTable tablaListaContactos;
    
    private JComboBox<String> listaEstado;
    private JButton btnLogOut;
    private JButton btnEnviarMensaje;
    private JButton btnAgregarContacto;
}
