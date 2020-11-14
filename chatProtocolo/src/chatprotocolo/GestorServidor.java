package chatprotocolo;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 *
 * @author Georges Alfaro S.
 * @version 1.2
 */
class GestorServidor implements Runnable {

    public GestorServidor(Socket skt) throws IOException {
        this.skt = skt;
    }

    @Override
    public void run() {
        ObjectInputStream entrada;
        try {
            entrada = new ObjectInputStream(skt.getInputStream());

            while (continuar) {
                try {
                    Object s = entrada.readObject();
                    mostrarMensaje(s.toString());
                } catch (IOException exc) {
                    System.err.printf("Error de recepción: '%s'%n", exc.getMessage());
                    continuar = false;
                } catch (ClassNotFoundException exc) {
                    System.err.printf("Error de serialización: '%s'%n", exc.getMessage());
                    continuar = false;
                }
            }
            try {
                entrada.close();
                System.out.println("Cerrando conexión con el servidor..");
                // skt.close();
            } catch (IOException ioe) {
                throw ioe;
            }
        } catch (IOException ioe) {
            System.err.printf("Ocurrió un error al abrir el flujo de entrada: '%s'%n", ioe.getMessage());
            continuar = false;
        }
    }

    public void mostrarMensaje(String mensaje) {
        System.out.printf("Mensaje recibido: '%s' (%s)%n",
                mensaje, LocalDateTime.now(ZoneId.systemDefault()));
    }

    private boolean continuar = true;
    private final Socket skt;
}
