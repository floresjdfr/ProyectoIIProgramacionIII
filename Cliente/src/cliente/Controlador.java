package cliente;

import XML.Xml;
import java.time.LocalDate;
import java.util.List;
import java.util.Observer;
import protocolo.Chat;
import protocolo.Mensaje;
import protocolo.Usuario;

/**
 *
 * @author Jose David
 */
public class Controlador {
    public Controlador() {
        this.servicioCliente = (ServicioCliente) ServicioCliente.obtenerInstancia();
        this.servicioCliente.setContolador(this);
    }

    public Controlador(Modelo modelo) {
        this.modelo = modelo;
        this.servicioCliente = (ServicioCliente) ServicioCliente.obtenerInstancia();
        this.servicioCliente.setContolador(this);
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
            Xml.obtieneProductosXML(user, modelo);
            servicioCliente.verificaContactosEnLinea(usuario, user.getContatos());
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
        modelo.actualizarChat();
        Xml.guardaUsuarioXML(modelo);
        modelo.logout();
    }
    
    public String getNombreUsuario(){
        return modelo.getNombreUsuario();
    }
    
    public String agregarContacto(String usuario){
        
        usuario = servicioCliente.agregarContacto(usuario);
        if (usuario != null)
            modelo.agregarContacto(usuario);
        return usuario;
    }
    
    public void enviarMensaje(String mensaje) {
        Mensaje msj = new Mensaje(0, modelo.getNombreUsuario(), modelo.obtenerContactoActual(), mensaje, LocalDate.now());
        servicioCliente.enviarMensaje(msj);
        modelo.enviarMensaje(msj);
    }
    
    public List<String> obtenerContactos() {
        return modelo.obtenerContactos();
    }
    
    private Modelo modelo;
    private final ServicioCliente servicioCliente;

    public void seleccionarChatUsuario(String usuario) {
        //Se supone que tiene que buscar el chat en el XML o base de datos
        
        modelo.seleccionarNuevoChat(usuario);
        
    }
    
    public String obtenerMensajesChatActual(){
        return modelo.obtenerMensajesChatActual();
    }
    
    public void recibirMensaje(Mensaje mensaje){
        modelo.recibirMensaje(mensaje);
    }

    public void recibirContactosEnLinea(List<String> contactos){
        modelo.recibirContactosEnLinea(contactos);
    }

    public List<Chat> obtenerChats() {
        return modelo.getChats();
    }
    
    public void notificarLoginUsuario(String usuario){
        modelo.cambiarEstadoUsuario(usuario, true);
    }

    

}
