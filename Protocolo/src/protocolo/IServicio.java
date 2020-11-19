package protocolo;

import java.util.List;

/**
 * @author Marvin Aguilar Fuentes
 * @author Jose David Flores Rodriguez
 */
public interface IServicio {
    public Usuario login(Usuario usuario) throws Exception;

    public void logout(Usuario usuario) throws Exception;

    public void enviarMensaje(Mensaje mensaje);

    public void verificaContactosEnLinea(String nombreUsuario, List<String> listaContactos);

    public void NotificarLogout(String nombreUsuario);
    
    public Usuario registrarUsuario(Usuario usuario)throws Exception;
    
}
