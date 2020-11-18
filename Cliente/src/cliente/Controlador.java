package cliente;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Observer;
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
    
    public boolean verificarDatosUsuario(String usuario, String clave){
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
        user = servicioCliente.registrarUsuario(user);
        try {
            user = servicioCliente.login(user);
        } catch (Exception ex) {}
        modelo.setUsuarioActual(user);
    }
    
    public void logout(){
        servicioCliente.logout(modelo.getUsuarioActual());
        modelo.setChatActual(null);
        modelo.setUsuarioActual(null);
    }
    
    public String getNombreUsuario(){
        return modelo.getNombreUsuario();
    }
    
    public String agregarContacto(String usuario){
        
        usuario = servicioCliente.agregarContacto(usuario);
        if (usuario != null){
            modelo.agregarContacto(usuario);
        }
        return usuario;
    }
    
    private Modelo modelo;
    private final ServicioCliente servicioCliente;

}
