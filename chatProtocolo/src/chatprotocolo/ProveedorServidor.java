package chatprotocolo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ProveedorServidor implements Runnable {

    public ProveedorServidor(Socket skt) throws IOException {
        this.skt = skt;
    }

    @Override
    public void run() {
        iniciar();
        OutputStream flujoSalida;
        ObjectOutputStream salida;
        try {
            flujoSalida = skt.getOutputStream();
            salida = new ObjectOutputStream(flujoSalida);

            int k = 0;
            while (continuar()) {
                try {
                    String mensaje = String.format("Mensaje %d", ++k);
                    System.out.printf("Enviando al servidor: %s%n", mensaje);
                    salida.writeObject(mensaje);

                    try {
                        Thread.sleep(MAX_ESPERA);
                    } catch (InterruptedException iee) {
                    }

                } catch (IOException exc) {
                    System.err.printf("Error de transmisión: '%s'%n", exc.getMessage());
                    detener();
                }
            }
            try {
                salida.close();
                System.out.println("Cerrando conexión con el servidor..");
                // skt.close();
            } catch (IOException ioe) {
                throw ioe;
            }
        } catch (IOException ioe) {
            System.err.printf("Ocurrió un error al abrir el flujo de salida: '%s'%n", ioe.getMessage());
            detener();
        }
    }

    public synchronized boolean continuar() {
        return continuar;
    }

    public synchronized void iniciar() {
        continuar = true;
    }

    public synchronized void detener() {
        continuar = false;
    }

    private static final int MAX_ESPERA = 2_000;
    private boolean continuar = true;
    private Socket skt;
}
