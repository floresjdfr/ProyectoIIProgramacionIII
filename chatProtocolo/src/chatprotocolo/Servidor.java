package chatprotocolo;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author Georges ALfaro S.
 * @version 1.0
 */
public class Servidor implements Runnable {

    public Servidor(int puerto, PrintStream salida, PrintStream error) {
        this.puerto = puerto;
        this.hiloControl = null;
        this.proveedores = new ArrayList<>();

        this.salida = salida;
        this.error = error;
    }

    public Servidor(int puerto) {
        this(puerto, System.out, System.err);
    }

    public Servidor() {
        this(PUERTO_POR_DEFECTO);
    }

    public void iniciar() {
        hiloControl = new Thread(this);
        if (hiloControl != null) {
            enServicio = true;
            hiloControl.start();
        }
    }

    public void detener() {
        for (ProveedorCliente actual : proveedores) {
            actual.detener();
        }
        enServicio = false;
    }

    @Override
    public void run() {
        salida.println("Iniciando servidor..");
        ServerSocket srvr;
        try {
            srvr = new ServerSocket(puerto);
            while (enServicio && (hiloControl == Thread.currentThread())) {
                try {
                    srvr.setSoTimeout(ESPERA_MAXIMA);
                    try {
                        Socket skt = srvr.accept();
                        atenderCliente(skt);
                    } catch (IOException exc) {
                        // Excedió el tiempo de espera
                        // para un cliente.
                    }
                } catch (IOException ioe) {
                    error.printf("Ocurrió un error al abrir el servidor: '%s'%n", ioe.getMessage());
                } finally {
                }
            }
            try {
                // El servidor se cierra hasta que el
                // usuario decide terminar la aplicación.
                srvr.close();
            } catch (IOException exc) {
            }
            salida.println("Cerrando servidor..");
        } catch (IOException exc) {
            error.printf("No se pudo abrir el servidor en el puerto %d (%s)%n",
                    puerto, exc.getMessage());
        }
    }

    public void atenderCliente(Socket skt) {

        salida.println("Atendiendo cliente..");

        // PRIMERO:
        // Se abre la conexión en dirección servidor <- cliente
        // SEGUNDO:
        // Se abre la conexión en dirección cliente <- servidor
        try {

            salida.println("Iniciando recepción de mensajes al cliente..");
            recibirMensajes(skt);
            salida.println("Iniciando envío de mensajes al cliente..");
            enviarMensajes(skt);

        } catch (IOException ioe) {
            error.printf("Excepción: '%s'%n", ioe.getMessage());
        } finally {
        }
    }

    public void enviarMensajes(Socket skt) throws IOException {
        ProveedorCliente prv = new ProveedorCliente(skt);
        proveedores.add(prv);
        new Thread(prv).start();
    }

    public void recibirMensajes(Socket skt) throws IOException {
        // Tarea por completar:
        // --- Crear la clase que recibe mensajes en el servidor.
        new Thread(new GestorCliente(skt)).start();
    }

    @Override
    public String toString() {
        String r;
        try {
            r = String.format("@%s:%s", InetAddress.getLocalHost(), puerto);
        } catch (UnknownHostException ex) {
            r = String.format("error: (%s)", ex.getMessage());
        }
        return r;
    }

    public static final int PUERTO_POR_DEFECTO = 1234;
    public static final int ESPERA_MAXIMA = 1000;
    private int puerto;
    private boolean enServicio = false;
    private Thread hiloControl;
    private ArrayList<ProveedorCliente> proveedores;

    private PrintStream salida;
    private PrintStream error;
}
