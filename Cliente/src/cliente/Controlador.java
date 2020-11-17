package cliente;

import java.time.LocalDate;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import protocolo.Usuario;

/**
 *
 * @author Jose David
 */
public class Controlador {
    public Controlador() {
        this.servicioCliente = (ServicioCliente) ServicioCliente.obtenerInstancia();
    }

    public Controlador(Modelo modelo) {
        this.modelo = modelo;
        this.servicioCliente = (ServicioCliente) ServicioCliente.obtenerInstancia();
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }
    
    public void registrarObservador(Observer obs){
        modelo.addObserver(obs);
    }
    
    public boolean verificarDatosUsuario(String usuario, String clave) throws Exception{
        Usuario user = new Usuario(usuario, clave);
        user = servicioCliente.login(user);
        if (user != null){
            modelo.setUsuarioActual(user);
            return true;
        }
        else
            return false;
        
    }
    
    public void registrarNuevoUsuario(String usuario, String nombreCompleto, String password){
        Usuario user = new Usuario(usuario, nombreCompleto, password, LocalDate.now());
        servicioCliente.registrarUsuario(user);
        try {
            user = servicioCliente.login(user);
        } catch (Exception ex) {}
        modelo.setUsuarioActual(user);
    }
    
    
    private Modelo modelo;
    private final ServicioCliente servicioCliente;

}
