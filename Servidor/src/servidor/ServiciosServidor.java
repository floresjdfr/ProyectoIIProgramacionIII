package servidor;

import DAO.UsuarioDAO;
import java.util.List;
import protocolo.IServicio;
import protocolo.Mensaje;
import protocolo.Usuario;

/**
 *
 * @author Jose David
 */
public class ServiciosServidor implements IServicio {

    public ServiciosServidor() {
        usuarioDAO = usuarioDAO.obtenerInstancia();
    }

    public static IServicio obtenerInstancia() {
        if (instancia == null) {
            instancia = new ServiciosServidor();
        }
        return instancia;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    @Override
    public Usuario login(Usuario usuario) throws Exception {
        Usuario user = usuarioDAO.recuperar(usuario.getNombreUsuario());
        if (user != null) {
            if (user.getClave().equals(usuario.getClave())) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void logout(Usuario usuario) throws Exception {
        servidor.borrarUsuarioServidor(usuario);
    }

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        servidor.enviarMensaje(mensaje);
    }

    @Override
    public void verificaContactosEnLinea(String nombreUsuario, List<String> listaContactos) {
        servidor.contactosEnLinea(nombreUsuario, listaContactos);
    }

    @Override
    public void NotificarLogout(String nombreUsuario) {
        servidor.notificarLogout(nombreUsuario);
    }

    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        usuarioDAO.agregar(usuario);
        return usuarioDAO.recuperar(usuario.getNombreUsuario());
    }

    public String agregarContacto(String usuario) {
        return usuarioDAO.recuperarNombreUsuario(usuario);
    }

    private static IServicio instancia;

    Servidor servidor;
    UsuarioDAO usuarioDAO;
}
