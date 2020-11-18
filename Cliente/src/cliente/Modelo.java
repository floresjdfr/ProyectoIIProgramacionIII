package cliente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import protocolo.Chat;
import protocolo.Mensaje;
import protocolo.Usuario;

/**
 *
 * @author Jose David Flores
 * @author Marvin Aguilar
 */
public class Modelo extends Observable {

    public Modelo(Usuario usuarioActual, Chat chatActual) {
        this.usuarioActual = usuarioActual;
        this.chatActual = chatActual;
    }

    public Modelo() {
    }

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public Chat getChatActual() {
        return chatActual;
    }

    public void setChatActual(Chat chatActual) {
        this.chatActual = chatActual;

        setChanged();
        notifyObservers();
    }

    public String getNombreUsuario() {
        return usuarioActual.getNombreUsuario();
    }

    public void agregarContacto(String usuario) {
        usuarioActual.agregarContacto(usuario);

        setChanged();
        notifyObservers();
    }

    public void enviarMensaje(Mensaje mensaje) {
        chatActual.agregarMensaje(mensaje);
        
        setChanged();
        notifyObservers();
    }
    
    public String obtenerContactoActual(){
        return chatActual.getContacto();
    }

    public List<String> obtenerContactos() {
        return usuarioActual.getContatos();
    }

    public String obtenerMensajesChatActual() {
        if (chatActual != null) {
            StringBuilder mensajes = new StringBuilder();
            for (Mensaje mensaje : chatActual.getMensajes()) {
                mensajes.append(mensaje);
            }
            return mensajes.toString();
        }
        return null;
    }

    private Usuario usuarioActual;
    private Chat chatActual;

    void recibirMensaje(Mensaje mensaje) {
        chatActual.agregarMensaje(mensaje);
        
        setChanged();
        notifyObservers();
    }

}
