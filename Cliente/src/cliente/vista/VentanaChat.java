package cliente.vista;

import cliente.Controlador;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
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
import protocolo.Chat;

/**
 *
 * @author Marvin Aguilar
 */
public class VentanaChat extends JFrame implements Observer {

    public VentanaChat(String titulo, Controlador gestorPrincipal) throws HeadlessException {
        super(titulo);
        this.gestorPrincipal = gestorPrincipal;
        this.gestorPrincipal.registrarObservador(this);
        configurar();
        actualizarContactos();
        actualizarEstadoContactos();

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

        String user = "Usuario: " + gestorPrincipal.getNombreUsuario();
        labelLoggedUser = new JLabel(user);
        panelSuperior.add(labelLoggedUser);
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

        campoChat = new JTextArea();
        campoChat.setLineWrap(true);
        campoChat.setEditable(false);
        scrollPanelChat = new JScrollPane(campoChat);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;
        panelCentral.add(scrollPanelChat, gbc);

        c.add(panelCentral, BorderLayout.CENTER);

        JPanel panelContactos = new JPanel();
        panelContactos.setLayout(new FlowLayout(FlowLayout.LEFT));

        String[] nombreColumnas = {"Usuario", "Estado"};

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

        campoMensaje = new JTextField();
        campoMensaje.setPreferredSize(new Dimension(300, 20));
        campoMensaje.setMaximumSize(new Dimension(300, 20));
        btnEnviarMensaje = new JButton("Enviar Mensaje");

        panelInferior.add(campoMensaje);
        panelInferior.add(Box.createRigidArea(new Dimension(10, 0)));
        panelInferior.add(btnEnviarMensaje);

        c.add(panelInferior, BorderLayout.PAGE_END);

        btnEnviarMensaje.addActionListener((ActionEvent e) -> {
            enviarMensaje();
        });

        btnLogOut.addActionListener((ActionEvent e) -> {
            logOut();
        });

        btnAgregarContacto.addActionListener((ActionEvent e) -> {
            VentanaNuevoContacto ventana = new VentanaNuevoContacto("Nuevo Contacto", gestorPrincipal);
            ventana.init();

        });

        tablaListaContactos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    String usuario = obtenerUsuarioTabla();

                    seleccionarChatUsuario(usuario);
                }
            }
        });
        listaEstado.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String estado = (String) listaEstado.getSelectedItem();
                    solicitarEstadoContactos(estado);
                }
            }
        });
    }

    public void solicitarEstadoContactos(String estado) {
        switch (estado) {
            case "Todos":
                actualizarContactos();
                actualizarEstadoContactos();
                break;
            case "Online":
                obtieneEstadoOnline();
                break;
            case "Offline":
                obtieneEstadoOffline();
                break;
        }
    }

    public void obtieneEstadoOnline() {
        modeloTabla.setRowCount(0);
        List<Chat> chats = gestorPrincipal.obtenerChats();
        for (Chat chat : chats) {
            if (chat.isEstado()) {
                Object[] objectoAgregado = {chat.getContacto(), "Online"};
                modeloTabla.addRow(objectoAgregado);
            }
        }
    }

    public void obtieneEstadoOffline() {
        modeloTabla.setRowCount(0);
        List<Chat> chats = gestorPrincipal.obtenerChats();
        for (Chat chat : chats) {
            if (!chat.isEstado()) {
                Object[] objectoAgregado = {chat.getContacto(), "Offline"};
                modeloTabla.addRow(objectoAgregado);
            }
        }
    }

    public void init() {
        setVisible(true);
    }

    public void enviarMensaje() {
        String mensaje = campoMensaje.getText();
        campoMensaje.setText(null);

        if (!mensaje.equals("")) {
            gestorPrincipal.enviarMensaje(mensaje);
        }
    }

    public void logOut() {
        gestorPrincipal.logout();
        dispose();
        new VentanaAplicacion("Chat", gestorPrincipal).init();
    }

    public void actualizarEstadoContactos() {
        List<Chat> chats = gestorPrincipal.obtenerChats();
        for (Chat chat : chats) {
            String nombreUsuario = chat.getContacto();
            boolean estado = chat.isEstado();
            System.out.println("Contacto: " + nombreUsuario + estado);
            for (int i = 0; i < modeloTabla.getRowCount(); i++) {
                if (modeloTabla.getValueAt(i, 0).toString().equals(nombreUsuario)) {
                    if (estado) {
                        modeloTabla.setValueAt("Online", i, 1);
                    } else {
                        modeloTabla.setValueAt("Offline", i, 1);
                    }
                }
            }
        }
    }

    private void actualizarContactos() {
        modeloTabla.setRowCount(0);
        List<String> contactos = gestorPrincipal.obtenerContactos();
        for (String contacto : contactos) {
            Object[] objectoAgregado = {contacto, "Offline"};
            modeloTabla.addRow(objectoAgregado);
        }
    }

    private String obtenerUsuarioTabla() {
        int filaSeleccionada = tablaListaContactos.getSelectedRow();
        return (String) modeloTabla.getValueAt(filaSeleccionada, 0);

    }

    private void seleccionarChatUsuario(String usuario) {
        gestorPrincipal.seleccionarChatUsuario(usuario);
    }

    private String obtenerMensajesChatActual() {
        return gestorPrincipal.obtenerMensajesChatActual();
    }

    private void actualizarMensajes() {
        String mensajes = obtenerMensajesChatActual();
        campoChat.setText(mensajes);
    }

    @Override
    public void update(Observable o, Object arg) {
        actualizarContactos();
        actualizarMensajes();
        actualizarEstadoContactos();
    }

    private DefaultTableModel modeloTabla;
    private JScrollPane scrollPanelTabla;
    private JTable tablaListaContactos;

    private JScrollPane scrollPanelChat;
    private JTextArea campoChat;

    private JTextField campoMensaje;

    private JComboBox<String> listaEstado;
    private JButton btnLogOut;
    private JButton btnEnviarMensaje;
    private JButton btnAgregarContacto;

    private JLabel labelLoggedUser;

    private final Controlador gestorPrincipal;

}
