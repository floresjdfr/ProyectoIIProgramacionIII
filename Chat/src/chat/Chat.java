package chat;

import chat.control.ControlChat;
import chat.vista.VentanaAplicacion;
import chat.vista.VentanaChat;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Jose David
 */
public class Chat {
    
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            JFrame.setDefaultLookAndFeelDecorated(true);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | UnsupportedLookAndFeelException ex) {
            System.err.printf("Excepción: '%s'%n", ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            mostrarInterfaz();
        });
    }
    
    public static void mostrarInterfaz() {
        new VentanaAplicacion("Chat", new ControlChat()).init();
        //new VentanaChat("Chat de Texto (DEMO)", new ControlChat()).init();
    }
    
}
