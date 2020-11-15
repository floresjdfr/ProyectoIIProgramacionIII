package servidor;

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
        
    }

    public static IServicio obtenerInstancia() {
        if (instancia == null) {
            instancia = new ServiciosServidor();  
        }
        return instancia;
    }
    
    @Override
    public Usuario login(Usuario usuario) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void logout(Usuario usuario) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void enviarMensaje(Mensaje mensaje) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void verificaContactosEnLinea(String nombreUsuario, List<String> listaContactos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void NotificarLogout(String nombreUsuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static IServicio instancia;
    Servidor servidor;
}

    
