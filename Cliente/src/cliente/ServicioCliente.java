/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Thread.sleep;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolo.IServicio;
import protocolo.Mensaje;
import protocolo.Peticiones;
import protocolo.Usuario;

/**
 *
 * @author Jose David
 */
public class ServicioCliente implements IServicio, Runnable {

    public ServicioCliente() {

    }

    public static IServicio obtenerInstancia() {
        if (instancia == null) {
            instancia = new ServicioCliente();
        }
        return instancia;
    }

    public void iniciar() {
        hiloControl = new Thread(this);
        if (hiloControl != null) {
            continuar = true;
            hiloControl.start();
        }
    }

    public void stopThread() {
        continuar = false;
    }

    @Override
    public void run() {
        String respuestaServidor;
        while (continuar) {

            try {
                respuestaServidor = (String) entrada.readObject();
                //System.out.print(respuestaServidor);

                switch (respuestaServidor) {
                    case Peticiones.ENVIO_MENSAJE:
                        try {
                            Mensaje mensaje = (Mensaje) entrada.readObject();
                            recibirMensaje(mensaje);

                        } catch (ClassNotFoundException ex) {
                        }
                        break;
                    case Peticiones.CONTACTOS_EN_LINEA:
                        List<String> contactosEnLinea = (List<String>) entrada.readObject();
                        break;

                    case Peticiones.NOTIFICAR_LOGOUT:
                        String nombreUsuarioLoggedOut = (String) entrada.readObject();
                        break;
                    case Peticiones.NOTIFICAR_LOGIN:
                        String usuarioLoggedIn = (String) entrada.readObject();
                        break;
                    
                    case Peticiones.AGREGAR_CONTACTO:
                        String usuario = (String) entrada.readObject();
                        if (usuario != null)
                            contactoRecibido = usuario;
                        break;
                }
                salida.flush();
                //PROBAR SI ES NECESARIO
            } catch (IOException | ClassNotFoundException ex) {
                continuar = false;
            }
        }
    }

    public void conectarSocket() throws IOException {
        socket = new Socket("localhost", 1234);
        salida = new ObjectOutputStream(socket.getOutputStream());
        entrada = new ObjectInputStream(socket.getInputStream());
    }

    public void desconectarSocket() throws IOException {
        socket.shutdownOutput();
        //PROBAR SI ES NECESARIO
        socket.close();
    }

    @Override
    public Usuario login(Usuario usuario) {
        try {
            conectarSocket();
        } catch (IOException ex) {
            Logger.getLogger(ServicioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            salida.writeObject(Peticiones.LOGIN);
            salida.writeObject(usuario);
            salida.flush();
            String response = (String) entrada.readObject();
            Usuario user = (Usuario) entrada.readObject();
            if (response.equals(Peticiones.NO_ERROR) && user != null) {
                this.iniciar();
                return user;
            } else {
                desconectarSocket();
                throw new Exception("No remote user");
            }
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public void logout(Usuario usuario) {
        try {
            salida.writeObject(Peticiones.LOGOUT);
            salida.writeObject(usuario);
            salida.flush();
            this.stopThread();
            this.desconectarSocket();
        } catch (IOException ex) {
        }
    }

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        try {
            salida.writeObject(Peticiones.ENVIO_MENSAJE);
            salida.writeObject(mensaje);
            salida.flush();
        } catch (IOException ex) {
        }
    }

    @Override
    public void verificaContactosEnLinea(String nombreUsuario, List<String> listaContactos) {
        try {
            salida.writeObject(Peticiones.CONTACTOS_EN_LINEA);
            salida.writeObject(listaContactos);
            salida.flush();
        } catch (IOException ex) {
        }
    }

    @Override
    public void NotificarLogout(String nombreUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        //Falta
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {

        try {

            conectarSocket();
            try {
                salida.writeObject(Peticiones.REGISTRAR_USUARIO);
                salida.writeObject(usuario);
                salida.flush();

                String response = (String) entrada.readObject();
                if (response.equals(Peticiones.NO_ERROR)) {
                    Usuario user = (Usuario) entrada.readObject();
                    this.iniciar();
                    return user;
                } else {
                    desconectarSocket();
                    throw new Exception("No remote user");
                }
            } catch (Exception ex) {
                return null;
            }
        } catch (IOException ex) {
            return null;
        }
    }

    public String agregarContacto(String usuario) {
        try {

            salida.writeObject(Peticiones.AGREGAR_CONTACTO);
            salida.writeObject(usuario);
            salida.flush();
            
            
            sleep(500);//Espera la respuesta de servidor
            
            if (contactoRecibido != null){
                usuario = contactoRecibido;
                contactoRecibido = null;
                return usuario;
            }
            return null;

        } catch (IOException | InterruptedException ex) {
            return null;
        }
    }
    
    private void recibirMensaje(Mensaje mensaje) {
        Thread hilo = new Thread(new Runnable() {
            @Override
            public void run() {
                controlador.recibirMensaje(mensaje);
            }
        });
        hilo.start();
    }
    
    public void setContolador(Controlador controlador){
        this.controlador = controlador;
    }

    private static IServicio instancia;
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private Thread hiloControl;
    private boolean continuar = true;
    private String contactoRecibido = null;
    private Controlador controlador;

   
}
