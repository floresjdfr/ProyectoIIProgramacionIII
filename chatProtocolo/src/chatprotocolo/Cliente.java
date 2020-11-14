package chatprotocolo;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 *
 * @author Georges Alfaro S.
 * @version 1.2
 */
public class Cliente {

    public Cliente(String url, int puerto) {
        this.url = url;
        this.puerto = puerto;
    }

    public Cliente(String url) {
        this(url, Servidor.PUERTO_POR_DEFECTO);
    }

    public Cliente() {
        this(URL_POR_DEFECTO);
    }

    public void iniciar() {
        try {
            System.out.println("Abriendo conexión con el servidor..");
            Socket skt = new Socket(url, puerto);
            System.out.println("Conexión completada..");

            // Invoca al método de atención al
            // servidor. Este atenderá al servidor
            // de manera asíncrona.
            //
            atenderServidor(skt);

        } catch (UnknownHostException ex) {
            System.err.printf("No se reconoce el servidor: %s%n", url);
        } catch (IOException ex) {
            System.err.printf("Ocurrió un error en la conexión: '%s'%n", ex.getMessage());
        }
        System.out.println("Inicialización completa..");
    }

    public void atenderServidor(Socket skt) {
        System.out.println("Atendiendo al servidor..");

        try {
            enviarMensajes(skt);
            recibirMensajes(skt);

        } catch (IOException ioe) {
            System.err.printf("Excepción: '%s'%n", ioe.getMessage());
        } finally {
        }

        System.out.println("Finaliza atenderServidor()..");
        System.out.printf("El socket está %s.%n", (skt.isClosed()) ? "CERRADO" : "ABIERTO");
    }

    public void recibirMensajes(Socket skt) throws IOException {
        new Thread(new GestorServidor(skt)).start();
    }

    public void enviarMensajes(Socket skt) throws IOException {
        // Tarea por completar:
        // --- Crear la clase que envía mensajes al servidor.
        new Thread(new ProveedorServidor(skt)).start();
    }

    public static final String URL_POR_DEFECTO = "localhost";
    private String url;
    private int puerto;
}
