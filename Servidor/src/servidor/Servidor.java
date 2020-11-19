package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolo.Mensaje;
import protocolo.Peticiones;
import protocolo.Usuario;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public class Servidor {

    public Servidor() {
        try {
            serverSocket = new ServerSocket(Peticiones.PUERTO_POR_DEFECTO);
            usuarios = new ArrayList<>();
        } catch (IOException ex) {

        }
    } 

    public void iniciarServidor() throws IOException {
        ServiciosServidor serviciosServidor = (ServiciosServidor) ServiciosServidor.obtenerInstancia();
        serviciosServidor.setServidor(this);
        boolean continuar = true;

        while (continuar) {
            Socket socket = serverSocket.accept();

            ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());

            try {
                String peticion = (String) entrada.readObject();
                Usuario usuario = (Usuario) entrada.readObject();
                if (peticion.equals(Peticiones.LOGIN)) {
                    try {

                        usuario = serviciosServidor.login(usuario);
                    } catch (Exception ex) {
                        usuario = null;
                    }
                } else if (peticion.equals(Peticiones.REGISTRAR_USUARIO)) {
                    usuario = serviciosServidor.registrarUsuario(usuario);
                }

                if (usuario != null) {
                    salida.writeObject(Peticiones.NO_ERROR);
                    salida.writeObject(usuario);
                    salida.flush();

                    notificarLogin(usuario.getNombreUsuario());

                    UsuarioServidor nuevoUsuarioServidor = new UsuarioServidor(socket, entrada, salida, usuario);
                    usuarios.add(nuevoUsuarioServidor);

                    nuevoUsuarioServidor.iniciar();
                } else {
                    salida.writeObject(Peticiones.ERROR_LOGIN);
                    salida.writeObject(usuario);
                    salida.flush();

                }

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Servidor.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void notificarLogin(String nombreUsuario) {
        for (UsuarioServidor usuario : usuarios) {
            usuario.notificarLogin(nombreUsuario);
        }
    }

    public void notificarLogout(String nombreUsuario) {
        for (UsuarioServidor usuario : usuarios) {
            usuario.notificarLogout(nombreUsuario);
        }
    }

    public void borrarUsuarioServidor(Usuario usuario) {
        for (UsuarioServidor usuarioServidor : usuarios) {
            if (usuarioServidor.getUsuario().equals(usuario)) {

                usuarios.remove(usuarioServidor);

                usuarioServidor.detener();

            }
        }
    }

    public void enviarMensaje(Mensaje message) {
        String destino = message.getReciever();
        UsuarioServidor user;

        for (UsuarioServidor usuario : usuarios) {
            if (usuario.getUsuario().getNombreUsuario().equals(destino)) {
                usuario.enviarMensaje(message);
            }
        }
    }

    public void contactosEnLinea(String solicitante, List<String> listContactos) {
        List<String> contactosEnLinea = new ArrayList<>();
        UsuarioServidor usuarioServidorSolicitante = null;
        for (UsuarioServidor usuario : usuarios) {
            if (listContactos.contains(usuario.getUsuario().getNombreUsuario())) {
                contactosEnLinea.add(usuario.getUsuario().getNombreUsuario());
            }
            if (usuario.getUsuario().getNombreUsuario().equals(solicitante)) {
                usuarioServidorSolicitante = usuario;
            }
        }
        usuarioServidorSolicitante.obtenerContacotsEnLinea(contactosEnLinea);
    }

    private ServerSocket serverSocket;
    private ArrayList<UsuarioServidor> usuarios;
    

}
