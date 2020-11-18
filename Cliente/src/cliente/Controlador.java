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
        this.servicioCliente.setControlador(this);
    }

    public Controlador(Modelo modelo) {
        this.modelo = modelo;
        this.servicioCliente = (ServicioCliente) ServicioCliente.obtenerInstancia();
        this.servicioCliente.setControlador(this);
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
    
    public void agregarContacto(String usuario){
        
        servicioCliente.agregarContacto(usuario);
        for (String contacto: modelo.getUsuarioActual().getContatos()){
            System.out.print(contacto);
        }
        
    }
    
    private Modelo modelo;
    private final ServicioCliente servicioCliente;

}
