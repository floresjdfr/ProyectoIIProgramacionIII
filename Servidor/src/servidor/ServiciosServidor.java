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
        Usuario resultado = usuarioDAO.recuperar(usuario.getNombreUsuario());
        if (resultado != null){
            if(resultado.getClave().equals(usuario.getClave()))
                return resultado;
            else
                throw new Exception("Contraseña incorrecta");
        }
        else
            throw new Exception("Usuario incorrecto");
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

    private static IServicio instancia;

    
    Servidor servidor;
    UsuarioDAO usuarioDAO;
}

    
