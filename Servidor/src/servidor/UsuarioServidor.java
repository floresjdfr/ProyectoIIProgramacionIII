package servidor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolo.Mensaje;
import protocolo.Peticiones;
import protocolo.Usuario;

/**
 *
 * @author Jose David
 */
public class UsuarioServidor implements Runnable {

    public UsuarioServidor(Socket socket, ObjectInputStream entrada, ObjectOutputStream salida, Usuario usuario) {
        this.socket = socket;
        this.entrada = entrada;
        this.salida = salida;
        this.usuario = usuario;
    }

    @Override
    public void run() {
        String servicioSolitidado;
        ServiciosServidor serviciosServidor = (ServiciosServidor) ServiciosServidor.obtenerInstancia();
        while (continuar) {
            try {
                servicioSolitidado = (String) entrada.readObject();

                switch (servicioSolitidado) {
                    case Peticiones.LOGOUT: {
                        try {

                            serviciosServidor.logout(usuario);
                            serviciosServidor.NotificarLogout(usuario.getNombreUsuario());
                            continuar = false;
                        } catch (Exception ex) {
                        }
                    }
                    break;

                    case Peticiones.ENVIO_MENSAJE:
                        Mensaje mensaje = null;
                        try {
                            mensaje = (Mensaje) entrada.readObject();
                            int secuencia = this.cargarSecuencia();
                            mensaje.setSecuencia(secuencia);
                            secuencia++;
                            this.guardarSecuencia(secuencia);
                            serviciosServidor.enviarMensaje(mensaje);
                        } catch (ClassNotFoundException ex) {
                        }
                        break;

                    case Peticiones.CONTACTOS_EN_LINEA:
                        try {
                            List<String> listaContactos = (List<String>) entrada.readObject();
                            serviciosServidor.verificaContactosEnLinea(usuario.getNombreUsuario(), listaContactos);
                        } catch (ClassNotFoundException ex) {
                        }
                        break;
                    
                    case Peticiones.AGREGAR_CONTACTO:
                        String usuario = (String) entrada.readObject();
                        usuario = serviciosServidor.agregarContacto(usuario);
                        this.agregarContacto(usuario);
                }
                salida.flush();
                
            } catch (IOException | ClassNotFoundException ex) {
                continuar = false;
            }

        }
    }

        
    public int cargarSecuencia() {
        try {
            File myObj = new File("secuencia.txt");
            Scanner myReader = new Scanner(myObj);
            int numero = 0;
            while (myReader.hasNextLine()) {
                numero = myReader.nextInt();
            }
            myReader.close();
            return numero;
        } catch (FileNotFoundException e) {
            return 0;
        }
    }
    
    public void guardarSecuencia(int numeroNuevo) {
        try {
            FileWriter myWriter = new FileWriter("secuencia.txt");
            myWriter.write(String.valueOf(numeroNuevo));
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void iniciar() {
        hilo = new Thread(this);
        if (hilo != null) {
            continuar = true;
            hilo.start();
        }
    }
    
    public void agregarContacto (String usuario){
        try{
            salida.writeObject(Peticiones.AGREGAR_CONTACTO);
            salida.writeObject(usuario);
            salida.flush();
        }catch (IOException ex) {
        }
    }
    
    public void enviarMensaje(Mensaje mensaje){
        try{
            salida.writeObject(Peticiones.ENVIO_MENSAJE);
            salida.writeObject(mensaje);
            salida.flush();
        }catch (IOException ex) {
        }
    }

    public void notificarLogin(String nombreUsuario) {
        try {
            salida.writeObject(Peticiones.NOTIFICAR_LOGIN);
            salida.writeObject(nombreUsuario);
            salida.flush();

        } catch (IOException ex) {
            Logger.getLogger(UsuarioServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void notificarLogout(String nombreUsuario) {
        try {
            salida.writeObject(Peticiones.NOTIFICAR_LOGOUT);
            salida.writeObject(nombreUsuario);
            salida.flush();

        } catch (IOException ex) {
            Logger.getLogger(UsuarioServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void obtenerContacotsEnLinea(List<String> contactosEnLinea) {
        try {
            salida.writeObject(Peticiones.CONTACTOS_EN_LINEA);
            salida.writeObject(contactosEnLinea);
            salida.flush();
        } catch (IOException ex) {
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void detener() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(UsuarioServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private Usuario usuario;
    private Thread hilo;
    private boolean continuar;
}
